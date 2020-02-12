package com.sleep.opengl_analysis.programs;

import com.sleep.opengl_analysis.R;

import static android.opengl.GLES20.*;

/**
 * author：xingkong on 2020-02-12
 * e-mail：xingkong@changjinglu.net
 */
public class TextureShaderProgram extends ShaderProgram {

    private final int uMatrixLocation;
    private final int uTextureUnitLocation;

    private final int aPositionLocation;
    private final int aTextureCoordinateLocation;

    public TextureShaderProgram() {
        super(R.raw.texture_vertex_shader, R.raw.texture_fragment_shader);
        uMatrixLocation = glGetUniformLocation(program, U_MATRIX);
        uTextureUnitLocation = glGetUniformLocation(program, U_TEXTURE_UNIT);
        aPositionLocation = glGetAttribLocation(program, A_POSITION);
        aTextureCoordinateLocation = glGetAttribLocation(program, A_TEXTURE_COORDINATES);
    }

    public void setUniforms(float[] matrix, int textureId) {
        //pass the matrix into shader program
        glUniformMatrix4fv(uMatrixLocation, 1, false, matrix, 0);
        //关于纹理的使用，OpenGL中使用纹理单元，textureId <-> textureUnit <-> OpenGL
        //我们创建的纹理Id只和指定的纹理单元关联，然后激活指定的纹理单元，OpenGL提供有限个数的纹理单元
        //因为一个CPU同时只能绘制有限的几个纹理
        //set the active texture unit to texture unit 0
        glActiveTexture(GL_TEXTURE0);
        //bind the texture to the unit
        glBindTexture(GL_TEXTURE_2D, textureId);
        //tell the texture uniform sampler to use this texture in the shader by
        //telling it to read from texture unit 0
        glUniform1i(uTextureUnitLocation, 0);
    }

    public int getaPositionLocation() {
        return aPositionLocation;
    }

    public int getaTextureCoordinateLocation() {
        return aTextureCoordinateLocation;
    }
}
