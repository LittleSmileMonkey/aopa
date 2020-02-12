package com.sleep.opengl_analysis.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;
import android.util.Log;

import com.blankj.utilcode.util.Utils;
import com.sleep.common.CommonApplication;

import androidx.annotation.DrawableRes;

import static android.opengl.GLES20.*;

/**
 * author：xingkong on 2020-02-11
 * e-mail：xingkong@changjinglu.net
 */
public class TextureHelper {
    private static final String TAG = TextureHelper.class.getSimpleName();

    public static int loadTexture(@DrawableRes int resId) {
        final int[] textureObjectId = new int[1];
        glGenTextures(1, textureObjectId, 0);
        if (textureObjectId[0] == 0) {
            if (CommonApplication.LOG_ON) {
                Log.w(TAG, "Could not generate OpenGL texture object");
            }
            return 0;
        }
        //OpenGl需要非压缩的原始数据
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        final Bitmap bitmap = BitmapFactory.decodeResource(Utils.getApp().getResources(), resId, options);
        if (bitmap == null) {
            if (CommonApplication.LOG_ON) {
                Log.w(TAG, "Resource id: " + resId + " could not decoded.");
            }
            glDeleteTextures(1, textureObjectId, 0);
            return 0;
        }
        glBindTexture(GL_TEXTURE_2D, textureObjectId[0]);
        //设置缩小情况下的纹理过滤
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
        //设置放大情况下的纹理过滤
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        GLUtils.texImage2D(GL_TEXTURE_2D, 0, bitmap, 0);
        bitmap.recycle();
        glGenerateMipmap(GL_TEXTURE_2D);
        glBindTexture(GL_TEXTURE_2D,0);
        return textureObjectId[0];
    }
}
