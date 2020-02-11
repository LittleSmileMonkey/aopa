package com.sleep.opengl_analysis.util;

/**
 * author：xingkong on 2020-02-11
 * e-mail：xingkong@changjinglu.net
 */
public class MatrixHelper {
    public static void perspectiveM(float[] m, float yFovInDegrees, float aspect, float n, float f) {
        final float angleInDegrees = (float) (yFovInDegrees * Math.PI / 180.0);
        final float a = (float) (1.0 / (Math.tan(angleInDegrees / 2.0)));
        m[0] = a / aspect;
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

        m[12] = 0f;
        m[13] = 0f;
        m[14] = -((2f * f * n) / (f - n));
        m[15] = 0f;
    }
}
