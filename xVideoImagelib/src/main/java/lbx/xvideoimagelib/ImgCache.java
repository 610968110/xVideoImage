package lbx.xvideoimagelib;

import android.graphics.Bitmap;

/**
 * Created by Administrator on 2017/3/15.
 */

public interface ImgCache {
    void setCacheBitmap(String url, Bitmap b);

    Bitmap getBitmap(String url);
}
