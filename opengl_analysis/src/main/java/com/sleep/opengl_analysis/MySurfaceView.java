package com.sleep.opengl_analysis;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * author：xingkong on 2020-05-11
 * e-mail：xingkong@changjinglu.net
 */
public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    private SurfaceHolder mSurfaceHolder;

    private Thread mDrawThread = new Thread(this, "mDrawThread");

    private final Object mLock = new Object();

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public MySurfaceView(Context context) {
        this(context, null);
    }

    public MySurfaceView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MySurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public MySurfaceView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

    private void initView() {
        mPaint.setColor(Color.parseColor("#abcdef"));
        mPaint.setTextSize(20);
        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);
        setFocusable(true);
    }

    public void startDraw() {
        synchronized (mLock) {
            needDraw = true;
            mLock.notify();
        }
    }

    public synchronized void stopDraw() {
        needDraw = false;
    }

    public void destroy() {
        needRun = false;
        mDrawThread.interrupt();
        mDrawThread = null;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mSurfaceHolder = holder;
        mDrawThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mSurfaceHolder = null;
        mDrawThread.interrupt();
    }

    private volatile boolean needRun = true;

    private boolean needDraw = true;

    @Override
    public void run() {
        synchronized (mLock) {
            while (true) {
                if (!needRun) return;
                try {
                    if (needDraw) {
                        drawSomething();
                    } else {
                        mLock.wait();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    final int whiteColor = Color.parseColor("#000000");

    private void drawSomething() {
        if (mSurfaceHolder == null) return;
        final Canvas canvas = mSurfaceHolder.lockCanvas();
        try {

            canvas.drawColor(whiteColor, PorterDuff.Mode.CLEAR);
            canvas.drawText("currentTime = " + System.currentTimeMillis() / 1000, 100f, 100f, mPaint);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mSurfaceHolder.unlockCanvasAndPost(canvas);
        }
    }
}
