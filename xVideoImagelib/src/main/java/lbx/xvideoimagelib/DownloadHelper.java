package lbx.xvideoimagelib;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import lbx.xvideoimagelib.utils.BitmapUtil;
import lbx.xvideoimagelib.utils.UIUtils;


/**
 * Created by Administrator on 2017/3/15.
 */

public class DownloadHelper {

    private VideoImageLoader imageLoader;
    private ImageBuilder builder;
    private Map<String, String> map = new HashMap<>();

    public DownloadHelper(VideoImageLoader imageLoader, ImageBuilder builder) {
        this.imageLoader = imageLoader;
        this.builder = builder;
    }

    public void downloadImg(final String url, final VideoImageLoader.PathType type) {
        synchronized (DownloadHelper.class) {
            if (!TextUtils.isEmpty(map.get(url))) {
//            if (type == VideoImageLoader.PathType.VIDEO) {
//                LogUtils.e("该任务正在下载");
//            } else if (type == VideoImageLoader.PathType.PATH) {
//                LogUtils.e("该任务正在压缩");
//            } else {
//                LogUtils.e("该任务正在???");
//            }
                return;
            }
            map.put(url, url);
//        LogUtils.e("开始下载/加载img");
            imageLoader.executorService.execute(new Runnable() {
                @Override
                public void run() {
                    Bitmap b = null;
                    if (type == VideoImageLoader.PathType.VIDEO) {
                        b = getVideoImg(url);
                    } else if (type == VideoImageLoader.PathType.PATH) {
                        b = getDiscImg(url);
                    }
                    map.remove(url);
                    if (b == null) {
                        if (imageLoader.onImgDownloadFinish != null) {
                            UIUtils.runOnUIThread(new Runnable() {
                                @Override
                                public void run() {
                                    imageLoader.onImgDownloadFinish.imgDownloadErr("img maybe null");
                                }
                            });
                        }
                        return;
                    }
                    imageLoader.setBitmap(url, b);
                    if (imageLoader.onImgDownloadFinish != null) {
                        final Bitmap finalB = b;
                        UIUtils.runOnUIThread(new Runnable() {
                            @Override
                            public void run() {
                                imageLoader.onImgDownloadFinish.imgDownloadFinish(url, finalB);
                            }
                        });
                    }
                }
            });
        }
    }

    private Bitmap getVideoImg(String url) {
        Bitmap b = null;
        try {
            b = BitmapUtil.createVideoThumbnail(url,
                    builder.getImgWidth(), builder.getImgHeight());
        } catch (Exception e) {
            Log.e("xImageUtils", "获取网络视频图片错误：" + e.toString());
            e.printStackTrace();
        }
        return b;
    }

    private Bitmap getDiscImg(String path) {
        int min = Math.min(builder.getImgWidth(), builder.getImgHeight());
        return centerSquareScaleBitmap(BitmapFactory.decodeFile(path), min);
    }

    /**
     * @param bitmap     原图
     * @param edgeLength 希望得到的正方形部分的边长
     * @return 缩放截取正中部分后的位图。
     */
    private static Bitmap centerSquareScaleBitmap(Bitmap bitmap, int edgeLength) {
        if (null == bitmap || edgeLength <= 0) {
            return null;
        }
        Bitmap b = bitmap;
        int widthOrg = bitmap.getWidth();
        int heightOrg = bitmap.getHeight();
        if (widthOrg > edgeLength && heightOrg > edgeLength) {
            //压缩到一个最小长度是edgeLength的bitmap
            int longerEdge = (int) (edgeLength * Math.max(widthOrg, heightOrg) / Math.min(widthOrg, heightOrg));
            int scaledWidth = widthOrg > heightOrg ? longerEdge : edgeLength;
            int scaledHeight = widthOrg > heightOrg ? edgeLength : longerEdge;
            Bitmap scaledBitmap;
            try {
                scaledBitmap = Bitmap.createScaledBitmap(bitmap, scaledWidth, scaledHeight, true);
            } catch (Exception e) {
                return null;
            }
            //从图中截取正中间的正方形部分。
            int xTopLeft = (scaledWidth - edgeLength) / 2;
            int yTopLeft = (scaledHeight - edgeLength) / 2;
            try {
                b = Bitmap.createBitmap(scaledBitmap, xTopLeft, yTopLeft, edgeLength, edgeLength);
                scaledBitmap.recycle();
            } catch (Exception e) {
                return null;
            }
        }
//        LogUtils.e("b w = " + b.getWidth());
//        LogUtils.e("b h = " + b.getWidth());
        return b;
    }
}
