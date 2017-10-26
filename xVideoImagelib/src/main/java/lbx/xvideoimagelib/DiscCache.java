package lbx.xvideoimagelib;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2017/3/15.
 */

public class DiscCache implements ImgCache {

    private String path;
    private ImageBuilder builder;
    private VideoImageLoader imageLoader;

    public DiscCache(VideoImageLoader imageLoader, ImageBuilder builder) {
        path = builder.getPath();
        if (!path.endsWith("/"))
            path += "/";
        this.builder = builder;
        this.imageLoader = imageLoader;
        File dirFile = new File(path);
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            dirFile.mkdirs();
        }
    }

    private void saveBitmapFile(String url, final Bitmap bitmap) {
        final String name = url.substring(url.lastIndexOf("/") + 1, url.lastIndexOf("."));
        Log.e("xImageUtils", path + name);
        final File file = new File(path, name);//将要保存图片的路径
        imageLoader.executorService.execute(new Runnable() {
            @Override
            public void run() {
                if (file.exists() && !file.isDirectory())
                    file.delete();
                try {
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(file));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void setCacheBitmap(String url, Bitmap b) {
        if (TextUtils.isEmpty(url))
            throw new NullPointerException("url may not be null");
        saveBitmapFile(url, b);
    }

    @Override
    public Bitmap getBitmap(String url) {
        String name = url.contains(".") ?
                url.substring(url.lastIndexOf("/") + 1, url.lastIndexOf(".")) :
                url.substring(url.lastIndexOf("/") + 1, url.length());
        String path = this.path + "/" + name;
        return BitmapFactory.decodeFile(path);
    }
}
