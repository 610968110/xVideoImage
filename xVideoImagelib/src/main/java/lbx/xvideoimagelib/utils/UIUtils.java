package lbx.xvideoimagelib.utils;

import android.os.Handler;
import android.os.Looper;

public class UIUtils {

    private static Handler mHandler;

    // 运行在主线程
    public static void runOnUIThread(Runnable r) {
        if (mHandler == null)
            mHandler = new Handler(Looper.getMainLooper());
        if (mHandler.getLooper().getThread().getName().equals(Looper.getMainLooper().getThread().getName()))
            mHandler.post(r);
    }

    // 运行在子线程
    public static void runOnThirdThread(Runnable r) {
        new Thread(r).start();
    }
}
