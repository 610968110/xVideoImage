package lbx.xvideoimagelib;

import android.os.Environment;

/**
 * Created by Administrator on 2017/3/15.
 */

public class ImageBuilder {

    private String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/ImageLoader";
    private int threadNum = Runtime.getRuntime().availableProcessors() - 1;
    private int imgWidth = 200;
    private int imgHeight = 200;
    private CatchType catchType = CatchType.Memory_File;
    private int imgErrorId = -1;

    public enum CatchType {
        OnlyMemory, OnlyFile, Memory_File
    }


    public ImageBuilder setImgErrorId(int imgErrorId) {
        this.imgErrorId = imgErrorId;
        return this;
    }

    public int getImgErrorId() {
        return imgErrorId;
    }

    public CatchType getCatchType() {
        return catchType;
    }

    public ImageBuilder setCatchType(CatchType catchType) {
        this.catchType = catchType;
        return this;
    }

    public String getPath() {
        return path;
    }

    public ImageBuilder setPath(String path) {
        this.path = path;
        return this;
    }

    public int getThreadNum() {
        return threadNum;
    }

    public ImageBuilder setThreadNum(int threadNum) {
        if (threadNum <= 0)
            return this;
        this.threadNum = threadNum;
        return this;
    }

    public ImageBuilder setImgSize(int w, int h) {
        if (w <= 0 || h <= 0)
            return this;
        imgWidth = w;
        imgHeight = h;
        return this;
    }

    public int getImgWidth() {
        return imgWidth;
    }

    public int getImgHeight() {
        return imgHeight;
    }
}
