package lbx.xvideoimagelib;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static lbx.xvideoimagelib.ImageLoader.PathType.VIDEO;

/**
 * Created by Administrator on 2017/3/15.
 */

public class ImageLoader {

    private ImgCache memoryCache;
    private ImgCache sdCardCache;
    private DownloadHelper downloadHelper;
    private Context context;
    private ImageBuilder builder;
    private ImageBuilder.CatchType cacheType;
    protected ExecutorService executorService;

    protected enum PathType {
        VIDEO, PATH
    }

    public static ImageLoader getDefault(Context c) {
        return new ImageLoader(c, new ImageBuilder());
    }

    public ImageLoader(Context context, ImageBuilder builder) {
        this.context = context;
        this.builder = builder;
        this.cacheType = builder.getCatchType();
        executorService = Executors.newFixedThreadPool(builder.getThreadNum());
        memoryCache = new MemoryCache();
        sdCardCache = new DiscCache(this, builder);
        downloadHelper = new DownloadHelper(this, builder);
    }

    public void setBitmap(String url, Bitmap bitmap) {
        if (!cacheType.equals(ImageBuilder.CatchType.OnlyFile)) {
            memoryCache.setCacheBitmap(url, bitmap);
//            LogUtils.e("memoryCache.setCacheBitmap");
        }
        if (!cacheType.equals(ImageBuilder.CatchType.OnlyMemory)) {
            sdCardCache.setCacheBitmap(url, bitmap);
//            LogUtils.e("sdCardCache.setCacheBitmap");
        }
    }

    private Bitmap getBitmap(String url, PathType type) {
        if (TextUtils.isEmpty(url))
            return null;
        Bitmap b = memoryCache.getBitmap(url);
        if (b != null) {
//            LogUtils.e("内存获取img");
            return b;
        }
        b = sdCardCache.getBitmap(url);
        if (b != null) {
//            LogUtils.e("sd卡获取img");
            memoryCache.setCacheBitmap(url, b);
            return b;
        }
        downloadHelper.downloadImg(url, type);
        return null;
    }


    public OnImgDownloadFinish onImgDownloadFinish;

    public interface OnImgDownloadFinish {
        void imgDownloadFinish(String url, Bitmap b);

        void imgDownloadErr(String err);
    }

    public void setOnImgDownloadFinish(OnImgDownloadFinish onImgDownloadFinish) {
        this.onImgDownloadFinish = onImgDownloadFinish;
    }

    public <T extends View> void displayBitmap(T t, Bitmap b) {
        displayBitmap(t, b, null, null);
    }

    public <T extends View> void displayBitmap(T t, Bitmap b, Options options) {
        displayBitmap(t, b, null, options);
    }

    public <T extends View> void displayUrl(T t, String url) {
        displayBitmap(t, getBitmap(url, VIDEO), null, null);
    }

    public <T extends View> void displayUrl(T t, String url, Options options) {
        displayBitmap(t, getBitmap(url, VIDEO), null, options);
    }

    public <T extends View> void displayUrl(T t, String url, OnImgSetCallback callback) {
        displayBitmap(t, getBitmap(url, VIDEO), callback, null);
    }

    public <T extends View> void displayUrl(T t, String url, OnImgSetCallback callback, Options options) {
        displayBitmap(t, getBitmap(url, VIDEO), callback, options);
    }

    public <T extends View> void displayDisc(T t, String path) {
        displayBitmap(t, getBitmap(path, PathType.PATH), null, null);
    }

    public <T extends View> void displayDisc(T t, String path, Options options) {
        displayBitmap(t, getBitmap(path, PathType.PATH), null, options);
    }

    public <T extends View> void displayDisc(T t, String path, OnImgSetCallback callback) {
        displayBitmap(t, getBitmap(path, PathType.PATH), callback, null);
    }

    public <T extends View> void displayDisc(T t, String path, OnImgSetCallback callback, Options options) {
        displayBitmap(t, getBitmap(path, PathType.PATH), callback, options);
    }

    private <T extends View> void displayBitmap(T t, Bitmap b, OnImgSetCallback callback, Options options) {
        int errorId = builder.getImgErrorId();
        if (t instanceof ImageView) {
            ImageView imageView = (ImageView) t;
            if (b != null) {
                if (options != null) {
                    ImageView.ScaleType scaleType = options.getScaleType();
                    Options.BitmapStyle bitmapStyle = options.getStyle();
                    if (scaleType != null)
                        imageView.setScaleType(scaleType);
                    if (bitmapStyle == Options.BitmapStyle.NO_COLOR)
                        b = ImageUtils.makeBmpNoColor(b);
                }
                imageView.setImageBitmap(b);
                if (callback != null)
                    callback.success(t, b);
            } else {
                if (callback != null)
                    callback.errorLoad(t);
                if (errorId != -1) {
                    imageView.setImageResource(errorId);
                    if (callback != null)
                        callback.setErrorImgFinish(t, BitmapFactory.decodeResource(context.getResources(), errorId), errorId);
                }
            }
        } else {
            if (b != null) {
                if (options != null) {
                    Options.BitmapStyle bitmapStyle = options.getStyle();
                    if (bitmapStyle == Options.BitmapStyle.NO_COLOR)
                        b = ImageUtils.makeBmpNoColor(b);
                }
                t.setBackground(new BitmapDrawable(b));
                if (callback != null)
                    callback.success(t, b);
            } else {
                if (callback != null)
                    callback.errorLoad(t);
                if (errorId != -1) {
                    t.setBackgroundResource(errorId);
                    if (callback != null)
                        callback.setErrorImgFinish(t, BitmapFactory.decodeResource(context.getResources(), errorId), errorId);
                }
            }
        }
    }


    public interface OnImgSetCallback {
        void success(View view, Bitmap bitmap);

        void errorLoad(View view);

        void setErrorImgFinish(View view, Bitmap bitmap, int errId);
    }
}
