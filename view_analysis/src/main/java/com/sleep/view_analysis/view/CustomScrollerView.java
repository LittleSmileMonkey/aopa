package com.sleep.view_analysis.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.orhanobut.logger.Logger;

import androidx.annotation.Nullable;

/**
 * author：xingkong on 2020-01-03
 * e-mail：xingkong@changjinglu.net
 */
public class CustomScrollerView extends View {

    TextPaint textPaint = new TextPaint();

    final String testString = "testString";
    float textHeight;
    float textWidth;

    public CustomScrollerView(Context context) {
        this(context, null);
    }

    public CustomScrollerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomScrollerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        textPaint.setTextSize(20);
        textPaint.setColor(Color.parseColor("#000000"));
        textPaint.setAntiAlias(true);
        textWidth = textPaint.measureText(testString);
        final Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        textHeight = fontMetrics.bottom - fontMetrics.top;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Logger.d("ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Logger.d("ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Logger.d("ACTION_UP");
                break;
            case MotionEvent.ACTION_CANCEL:
                Logger.d("ACTION_CANCEL");
                break;
            default:
                Logger.d(event.getAction());
                break;
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawText(testString, (getWidth() >> 1) - textWidth / 2, (getHeight() >> 1) - textHeight / 2, textPaint);
    }
}
