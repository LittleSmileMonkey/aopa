package com.sleep.opengl_analysis.airhockey;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.Log;

import com.sleep.common.CommonApplication;
import com.sleep.common.TagUtil;
import com.sleep.opengl_analysis.R;
import com.sleep.opengl_analysis.util.ShaderHelper;
import com.sleep.opengl_analysis.util.TextResourceReader;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.GL_FLOAT;
import static android.opengl.GLES20.GL_LINES;
import static android.opengl.GLES20.GL_POINTS;
import static android.opengl.GLES20.GL_TRIANGLE_FAN;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.GLES20.glEnableVertexAttribArray;
import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glUseProgram;
import static android.opengl.GLES20.glVertexAttribPointer;
import static android.opengl.GLES20.glViewport;

/**
 * author：xingkong on 2020-02-10
 * e-mail：xingkong@changjinglu.net
 * <p>
 * OpenGl 编写步骤
 * 1. 对要画的图形进行抽象，转换为OpenGL中的三种基本元素的坐标
 * 2. 通过java ByteBuffer API 申请本地内存，并将坐标复制到本地内存中，并将position指向0
 * 3. 编写glsl代码(本例在raw文件夹中)
 * 4. 加载、编译各种shader并验证
 * 5. 创建并连接program，验证program
 * 6. 通过gl api 拿到glsl中定义的变量的句柄(location)
 * 7. 关联顶点坐标与顶点byteBuffer数据
 * 8. 通过各种句柄及坐标画想要的图形。
 */
public class AirHockeyRender_v1 implements GLSurfaceView.Renderer {

    //java中每个浮点占4个字节
    private static final int BYTES_PERFLOAT = 4;
    //每个position用两个浮点进行标记
    private static final int POSITION_COMPONENT_COUNT = 2;
    //每个color用3个浮点数表示
    private static final int COLOR_COMPONENT_COUNT = 3;

    private static final int STRIDE = (POSITION_COMPONENT_COUNT + COLOR_COMPONENT_COUNT) * BYTES_PERFLOAT;

    private static final String A_POSITION = "a_Position";
    private static final String A_COLOR = "a_Color";
    private int aColorLocation;
    private int aPositionLocation;

    //step 1. 对要画的图形进行抽象，转换为OpenGL中的三种基本元素的坐标
    private float[] tableVerticesWidthTriangles = {
            //triangle fan x,y,r,g,b
            0f, 0f, 1f, 1f, 1f,
            -0.5f, -0.5f, 0.7f, 0.7f, 0.7f,
            0.5f, -0.5f, 0.7f, 0.7f, 0.7f,
            0.5f, 0.5f, 0.7f, 0.7f, 0.7f,
            -0.5f, 0.5f, 0.7f, 0.7f, 0.7f,
            -0.5f, -0.5f, 0.7f, 0.7f, 0.7f,//重复加入这个点，是为了让triangle fan闭合

            //center line
            -0.5f, 0f,0.67f, 0.80f, 0.94f,
            0.5f, 0f, 0.94f, 0.80f, 0.67f,

            //Mallets
            0f, -0.25f, 0f, 0f, 1f,
            0f, 0.25f, 1f, 0f, 0f,

            //center ball
            0f, 0f, 0.67f, 0.80f, 0.94f,
    };

    private int program;

    private final FloatBuffer vertexData = ByteBuffer
            .allocateDirect(tableVerticesWidthTriangles.length * BYTES_PERFLOAT)
            .order(ByteOrder.nativeOrder())
            .asFloatBuffer();

    private String vertexShaderSource;
    private String fragmentShaderSource;

    public AirHockeyRender_v1() {
        //step 2. 通过java ByteBuffer API 申请本地内存，并将坐标复制到本地内存中，并将position指向0
        vertexData.put(tableVerticesWidthTriangles);
        vertexShaderSource = TextResourceReader.readTextFileFromResouce(R.raw.simple_vertex_shader_v1);
        fragmentShaderSource = TextResourceReader.readTextFileFromResouce(R.raw.simple_fragment_shader_v1);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        glClearColor(0.0f, 0f, 0f, 0f);

        //step 4. 加载并编译各种shader并验证
        int vertexShader = ShaderHelper.compileVertexShader(vertexShaderSource);
        int fragmentShader = ShaderHelper.compileFragmentShader(fragmentShaderSource);

        //step 5. 创建并连接program，验证program
        program = ShaderHelper.linkProgram(vertexShader, fragmentShader);
        if (CommonApplication.LOG_ON) {
            ShaderHelper.validateProgram(program);
        }
        glUseProgram(program);

        //step 6. 通过gl api 拿到glsl中定义的变量的句柄(location)
        //Uniform location
        aColorLocation = glGetAttribLocation(program, A_COLOR);
        //Attribute location
        aPositionLocation = glGetAttribLocation(program, A_POSITION);
        //确保内部指针开buffer开头
        vertexData.position(0);

        //step 7. 关联顶点坐标与顶点byteBuffer数据
        glVertexAttribPointer(aPositionLocation, POSITION_COMPONENT_COUNT, GL_FLOAT, false, STRIDE, vertexData);
        glEnableVertexAttribArray(aPositionLocation);

        vertexData.position(POSITION_COMPONENT_COUNT);
        glVertexAttribPointer(aColorLocation, POSITION_COMPONENT_COUNT, GL_FLOAT, false, STRIDE, vertexData);
        glEnableVertexAttribArray(aColorLocation);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        if (CommonApplication.LOG_ON) {
            Log.i(TagUtil.getTag(this), String.format("width = %s, height = %s", width, height));
        }
        glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        //step 8. 通过各种句柄及坐标画想要的图形。

        //更新着色器中的u_Color值
        //画桌子
        glDrawArrays(GL_TRIANGLE_FAN, 0, 6);

        glDrawArrays(GL_LINES, 6, 2);

        //画第一个蓝色木槌
        glDrawArrays(GL_POINTS, 8, 1);
        //画第二个红色木槌
        glDrawArrays(GL_POINTS, 9, 1);

        //中心冰球
        glDrawArrays(GL_POINTS, 10, 1);
    }
}
