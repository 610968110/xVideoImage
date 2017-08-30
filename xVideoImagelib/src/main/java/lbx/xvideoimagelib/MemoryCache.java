package lbx.xvideoimagelib;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * Created by Administrator on 2017/3/15.
 */

public class MemoryCache implements ImgCache {

    private LruCache<String, Bitmap> mMemoryCache;

    public MemoryCache() {
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int mCacheSize = maxMemory / 8;
        mMemoryCache = new LruCache<String, Bitmap>(mCacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight();
            }
        };
    }

    @Override
    public void setCacheBitmap(String url, Bitmap b) {
        mMemoryCache.put(url, b);
    }

    @Override
    public Bitmap getBitmap(String url) {
        return mMemoryCache.get(url);
    }
}
