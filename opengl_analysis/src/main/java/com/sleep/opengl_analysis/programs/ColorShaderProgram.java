package com.sleep.opengl_analysis.programs;

import com.sleep.opengl_analysis.R;

import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glUniformMatrix4fv;

/**
 * author：xingkong on 2020-02-12
 * e-mail：xingkong@changjinglu.net
 */
public class ColorShaderProgram extends ShaderProgram {

    private final int uMatrixLocation;

    private final int aPositionLocation;
    private final int aColorPosition;

    public ColorShaderProgram() {
        super(R.raw.simple_vertex_shader_v2, R.raw.simple_fragment_shader_v1);
        uMatrixLocation = glGetUniformLocation(program, U_MATRIX);
        aPositionLocation = glGetAttribLocation(program, A_POSITION);
        aColorPosition = glGetAttribLocation(program, A_COLOR);
    }

    public void setUniforms(float[] matrix) {
        glUniformMatrix4fv(uMatrixLocation, 1, false, matrix, 0);
    }

    public int getPositionLocation() {
        return aPositionLocation;
    }

    public int getColorPosition() {
        return aColorPosition;
    }
}
