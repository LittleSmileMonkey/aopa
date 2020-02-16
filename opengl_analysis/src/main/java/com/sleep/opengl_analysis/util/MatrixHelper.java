package com.sleep.opengl_analysis.util;

import android.util.Log;

import java.util.Arrays;

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

    public static void printMatrix(float[] src, int lineLength) {
        printMatrix("MatrixHelper", src, lineLength);
    }

    /**
     * 辅助打印矩阵
     *
     * @param src        矩阵数据源
     * @param lineLength 单行长度
     */
    public static void printMatrix(String tag, float[] src, int lineLength) {
        if (src.length % lineLength != 0) {
            Log.w(tag, "illegal source: " + Arrays.toString(src));
            return;
        }
        StringBuilder builder = new StringBuilder();
        builder.append(" \nmatrix start\n");
        int totalLine = src.length / lineLength;
        for (int i = 0; i < totalLine; i++) {
            for (int j = 0; j < lineLength; j++) {
                int currentIndex = i * lineLength + j;
                builder.append(src[currentIndex]);
                if (j == lineLength - 1) {
                    if (i != totalLine - 1) builder.append("\n");
                } else {
                    builder.append(", ");
                }
            }
        }
        builder.append("\nmatrix end");
        Log.i(tag, builder.toString());
    }
}
