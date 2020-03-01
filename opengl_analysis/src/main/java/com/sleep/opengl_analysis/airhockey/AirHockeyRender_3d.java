package com.sleep.opengl_analysis.airhockey;

import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;

import com.sleep.common.CommonApplication;
import com.sleep.common.TagUtil;
import com.sleep.opengl_analysis.R;
import com.sleep.opengl_analysis.objects.Mallet3D;
import com.sleep.opengl_analysis.objects.Puck3D;
import com.sleep.opengl_analysis.objects.Table;
import com.sleep.opengl_analysis.programs.ColorShaderProgram_3D;
import com.sleep.opengl_analysis.programs.TextureShaderProgram;
import com.sleep.opengl_analysis.util.MatrixHelper;
import com.sleep.opengl_analysis.util.TextureHelper;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glViewport;

/**
 * author：xingkong on 2020-02-10
 * e-mail：xingkong@changjinglu.net
 * <p>
 * 为table接入纹理
 */
public class AirHockeyRender_3d implements GLSurfaceView.Renderer {

    private final float[] viewMatrix = new float[16];
    private final float[] viewProjectionMatrix = new float[16];
    private final float[] modelViewProjectMatrix = new float[16];
    private final float[] projectionMatrix = new float[16];
    private final float[] modelMatrix = new float[16];

    private Table table;
    private Mallet3D mallet;
    private Puck3D puck;

    private TextureShaderProgram textureProgram;
    private ColorShaderProgram_3D colorProgram;

    private int texture;

    public AirHockeyRender_3d() {
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        glClearColor(0.0f, 0f, 0f, 0f);
        table = new Table();
        mallet = new Mallet3D(0.08f, 0.15f, 32);
        puck = new Puck3D(0.06f, 0.02f, 32);

        textureProgram = new TextureShaderProgram();
        colorProgram = new ColorShaderProgram_3D();

        texture = TextureHelper.loadTexture(R.mipmap.air_hockey_surface);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        if (CommonApplication.LOG_ON) {
            Log.i(TagUtil.getTag(this), String.format("width = %s, height = %s", width, height));
        }
        glViewport(0, 0, width, height);
        MatrixHelper.perspectiveM(projectionMatrix, 45, (float) width / (float) height, 1f, 0f);
        Matrix.setLookAtM(viewMatrix, 0, 0f, 1.2f, 2.2f, 0f, 0f, 0f, 0f, 1f, 0f);
//        Matrix.setIdentityM(modelMatrix, 0);
//        Matrix.translateM(modelMatrix, 0, 0f, 0f, -3f);
//        Matrix.rotateM(modelMatrix, 0, -60f, 1f, 0f, 0f);
//        final float[] temp = new float[16];
//        Matrix.multiplyMM(temp, 0, projectionMatrix, 0, modelMatrix, 0);
//        System.arraycopy(temp, 0, projectionMatrix, 0, temp.length);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        glClear(GL_COLOR_BUFFER_BIT);
        Matrix.multiplyMM(viewProjectionMatrix, 0, projectionMatrix, 0, viewMatrix, 0);
        positionTableInScreen();

        textureProgram.useProgram();
        textureProgram.setUniforms(viewProjectionMatrix, texture);
        table.bindData(textureProgram);
        table.draw();

        //draw the mallet
        positionObjectInScreen(0f, mallet.height / 2f, -0.4f);
        colorProgram.useProgram();
        colorProgram.setUniforms(modelViewProjectMatrix, 1f, 0f, 0f);
        mallet.bindData(colorProgram);
        mallet.draw();

        positionObjectInScreen(0f, mallet.height / 2f, 0.4f);
        colorProgram.setUniforms(modelViewProjectMatrix, 1f, 0f, 0f);
        //note that we don`t have to define the object data twice
        // we just draw the same mallet again but in different position and with a different color
        mallet.draw();

        //draw the puck
        positionObjectInScreen(0f, puck.height / 2f, 0f);
        colorProgram.setUniforms(modelViewProjectMatrix, 0.8f, 0.8f, 1f);
        puck.bindData(colorProgram);
        puck.draw();

    }

    private void positionTableInScreen() {
        //The table is defined in terms of x & y coordinates, so we rotate it 90 degree to lie flat on the xz plane
        Matrix.setIdentityM(modelMatrix, 0);
        Matrix.rotateM(modelMatrix, 0, -90f, 1f, 0f, 0f);
        Matrix.multiplyMM(modelViewProjectMatrix, 0, viewProjectionMatrix, 0, modelMatrix, 0);
    }

    private void positionObjectInScreen(float x, float y, float z) {
        Matrix.setIdentityM(modelMatrix, 0);
        Matrix.translateM(modelMatrix, 0, x, y, z);
        Matrix.multiplyMM(modelViewProjectMatrix, 0, viewProjectionMatrix, 0, modelMatrix, 0);
    }
}
