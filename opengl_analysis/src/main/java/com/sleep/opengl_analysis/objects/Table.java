package com.sleep.opengl_analysis.objects;

import android.opengl.GLES20;

import com.sleep.opengl_analysis.airhockey_v3.VertexArray;
import com.sleep.opengl_analysis.programs.TextureShaderProgram;
import com.sleep.opengl_analysis.util.Constants;

/**
 * author：xingkong on 2020-02-11
 * e-mail：xingkong@changjinglu.net
 */
public class Table {
    private static final int POSITION_COMPONENT_COUNT = 2;
    private static final int TEXTURE_COORDINATE_COMPONENT_COUNT = 2;
    private static final int STRIDE =
            (POSITION_COMPONENT_COUNT + TEXTURE_COORDINATE_COMPONENT_COUNT) * Constants.BYTES_PERFLOAT;

    private final VertexArray vertexArray;

    private static final float[] VERTEX_DATA = {
            //x,y,S,T
            0f, 0f, 0.5f, 0.5f,
            -0.5f, -0.8f, 0f, 0.9f,
            0.5f, -0.8f, 1f, 0.9f,
            0.5f, 0.8f, 1f, 0.1f,
            -0.5f, 0.8f, 0f, 0.1f,
            -0.5f, -0.8f, 0f, 0.9f
    };

    public Table() {
        vertexArray = new VertexArray(VERTEX_DATA);
    }

    public void bindData(TextureShaderProgram textureProgram) {
        vertexArray.setVertexAttribPointer(0
                , textureProgram.getaPositionLocation()
                , POSITION_COMPONENT_COUNT
                , STRIDE);
        vertexArray.setVertexAttribPointer(POSITION_COMPONENT_COUNT
                , textureProgram.getaTextureCoordinateLocation()
                , TEXTURE_COORDINATE_COMPONENT_COUNT
                , STRIDE);
    }

    public void draw() {
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, 6);
    }
}
