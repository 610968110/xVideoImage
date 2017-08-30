package lbx.xvideoimagelib;

import android.widget.ImageView;

/**
 * Created by Administrator on 2017/3/30.
 */

public class Options {

    public enum BitmapStyle {
        NORMAL, NO_COLOR,
    }

    private ImageView.ScaleType scaleType = null;
    private BitmapStyle style = BitmapStyle.NORMAL;

    public Options() {
    }

    public ImageView.ScaleType getScaleType() {
        return scaleType;
    }

    public Options setScaleType(ImageView.ScaleType scaleType) {
        this.scaleType = scaleType;
        return this;
    }

    public BitmapStyle getStyle() {
        return style;
    }

    public Options setStyle(BitmapStyle style) {
        this.style = style;
        return this;
    }
}
