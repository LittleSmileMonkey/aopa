package com.sleep.opengl_analysis.util;

/**
 * author：xingkong on 2020-02-11
 * e-mail：xingkong@changjinglu.net
 */
public class MatrixHelper {
    public static void prespectiveM(float[] m, float yFovInDegress, float asceptRatio, float n, float f) {
        final float angleInDegress = (float) (yFovInDegress * Math.PI / 180.0);
        final float a = (float) (1.0 / (Math.tan(angleInDegress / 2.0)));
        m[0] = a / asceptRatio;
        m[1] = 0f;
        m[2] = 0f;
        m[3] = 0f;

        m[4] = 0f;
        m[5] = a;
        m[6] = 0f;
        m[7] = 0f;

        m[8] = 0f;
        m[9] = 0f;
        m[10] = -((f + n) / (f - n));
        m[11] = -1f;

        m[8] = 0f;
        m[9] = 0f;
        m[10] = -((2f * f * n) / (f - n));
        m[11] = 0f;
    }
}
