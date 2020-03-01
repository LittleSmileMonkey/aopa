package com.sleep.opengl_analysis.programs;

import android.opengl.GLES20;

import com.sleep.opengl_analysis.util.ShaderHelper;
import com.sleep.opengl_analysis.util.TextResourceReader;

import androidx.annotation.RawRes;

/**
 * author：xingkong on 2020-02-12
 * e-mail：xingkong@changjinglu.net
 */
public class ShaderProgram {
    // uniform constant
    protected static final String U_MATRIX = "u_Matrix";
    protected static final String U_TEXTURE_UNIT = "u_TextureUnit";
    protected static final String U_COLOR = "u_Color";

    //attribute constant
    protected static final String A_POSITION = "a_Position";
    protected static final String A_COLOR = "a_Color";
    protected static final String A_TEXTURE_COORDINATES = "a_TextureCoordinates";

    //shader program
    protected final int program;

    public ShaderProgram(@RawRes int vertexShaderResId, @RawRes int fragmentShaderResId) {
        program = ShaderHelper.bindProgram(
                TextResourceReader.readTextFileFromResouce(vertexShaderResId)
                , TextResourceReader.readTextFileFromResouce(fragmentShaderResId));
    }

    public void useProgram() {
        GLES20.glUseProgram(program);
    }
}
