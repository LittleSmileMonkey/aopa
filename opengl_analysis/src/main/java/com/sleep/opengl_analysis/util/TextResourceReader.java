package com.sleep.opengl_analysis.util;

import android.content.res.Resources;

import com.blankj.utilcode.util.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import androidx.annotation.RawRes;

/**
 * author：xingkong on 2020-02-10
 * e-mail：xingkong@changjinglu.net
 */
public class TextResourceReader {
    public static String readTextFileFromResouce(@RawRes int resId) {
        StringBuilder body = new StringBuilder();
        final InputStream inputStream = Utils.getApp().getResources().openRawResource(resId);
        final InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        final BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        try {
            String nextLine;
            while ((nextLine = bufferedReader.readLine()) != null) {
                body.append(nextLine);
                body.append("\n");
            }
        } catch (IOException ioException) {
            throw new RuntimeException("Could not open resource: " + resId, ioException);
        } catch (Resources.NotFoundException nfe) {
            throw new RuntimeException("Resource not found " + resId, nfe);
        } finally {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return body.toString();
    }
}
