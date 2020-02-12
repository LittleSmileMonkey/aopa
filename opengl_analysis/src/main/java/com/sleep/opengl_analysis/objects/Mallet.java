package com.sleep.opengl_analysis.objects;

import android.opengl.GLES20;

import com.sleep.opengl_analysis.airhockey_v3.VertexArray;
import com.sleep.opengl_analysis.programs.ColorShaderProgram;
import com.sleep.opengl_analysis.util.Constants;

/**
 * author：xingkong on 2020-02-12
 * e-mail：xingkong@changjinglu.net
 */
public class Mallet {
    private static final int POSITION_COMPONENT_COUNT = 2;
    private static final int COLOR_COMPONENT_COUNT = 3;
    private static final int STRIDE = (POSITION_COMPONENT_COUNT + COLOR_COMPONENT_COUNT) * Constants.BYTES_PERFLOAT;
    private static final float[] VERTEX_DATA = {
            //x,y,r,g,b
            0f, -0.4f, 0f, 0f, 1f,
            0f, 0.4f, 1f, 0f, 0f
    };

    private final VertexArray vertexArray;

    public Mallet() {
        this.vertexArray = new VertexArray(VERTEX_DATA);
    }

    public void bindData(ColorShaderProgram colorProgram) {
        vertexArray.setVertexAttribPointer(0, colorProgram.getPositionLocation(), POSITION_COMPONENT_COUNT, STRIDE);
        vertexArray.setVertexAttribPointer(POSITION_COMPONENT_COUNT, colorProgram.getColorPosition(), COLOR_COMPONENT_COUNT, STRIDE);
    }

    public void draw() {
        GLES20.glDrawArrays(GLES20.GL_POINTS, 0, 2);
    }
}
