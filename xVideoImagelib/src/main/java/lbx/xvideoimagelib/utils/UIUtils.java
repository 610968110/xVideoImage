package lbx.xvideoimagelib.utils;

import android.os.Handler;
import android.os.Looper;
import android.os.Process;

public class UIUtils {

    private static Handler mHandler = new Handler(Looper.getMainLooper());

    // /////////////////判断是否运行在主线程//////////////////////////
    public static boolean isRunOnUIThread() {
        // 获取当前线程id, 如果当前线程id和主线程id相同, 那么当前就是主线程
        int myTid = android.os.Process.myTid();
        if (myTid == getMainThreadId()) {
            return true;
        }
        return false;
    }

    public static int getMainThreadId() {
        return Process.myTid();
    }

    // 运行在主线程
    public static void runOnUIThread(Runnable r) {
//        if (isRunOnUIThread()) {
//            // 已经是主线程, 直接运行
//            r.run();
//        } else {
            // 如果是子线程, 借助handler让其运行在主线程
            mHandler.post(r);
//        }
    }

    // 运行在子线程
    public static void runOnOtherThread(Runnable r) {
        if (isRunOnUIThread()) {
            // 已经是主线程
            new Thread(r).start();
        } else {
            // 如果是子线程
            r.run();
        }
    }
}
