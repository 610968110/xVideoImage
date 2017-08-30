package lbx.xvideoimagelib;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;

/**
 * Created by Administrator on 2017/7/13.
 */

public class ImageUtils {

    protected static Bitmap makeBmpNoColor(Bitmap b) {
        Bitmap bitmap = Bitmap.createBitmap(b.getWidth(), b.getHeight(), Bitmap.Config.ARGB_8888);
        ColorMatrix colorMatrix = new ColorMatrix();
        float[] array = colorMatrix.getArray();
        array[0] = 0.33f;
        array[1] = 0.59f;
        array[2] = 0.11f;
        array[5] = 0.33f;
        array[6] = 0.59f;
        array[7] = 0.11f;
        array[10] = 0.33f;
        array[11] = 0.59f;
        array[12] = 0.11f;
        colorMatrix.set(array);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
        canvas.drawBitmap(b, 0, 0, paint);
        return bitmap;
    }

}
