package com.example.mygame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import java.security.spec.ECField;

public class SnakeView extends SurfaceView implements SurfaceHolder.Callback {
    int x=500, y=1000, speedX=0, speedY=1, radius=1, width, height;
    final int APPLE_RADIUS = 15;

    public SnakeView(Context context) {
        super(context);
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        new GameThread().start();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {

    }

    @Override
    public boolean performClick() {
        if (speedX == 0 && speedY == 1) {
            speedX = 1; speedY = 0;
        }
        else if (speedX == 1 && speedY == 0) {
            speedX = 0; speedY = -1;
        }
        else if (speedX == 0 && speedY == -1) {
            speedX = -1; speedY = 0;
        }
        else if (speedX == -1 && speedY == 0) {
            speedX = 0; speedY = 1;
        }

        return super.performClick();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            performClick();
        }

        return super.onTouchEvent(event);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        width = w;
        height = h;
        super.onSizeChanged(w, h, oldw, oldh);
    }

    void GenerateApple() {
        x = (int)(Math.random() * (width - APPLE_RADIUS*2)) + APPLE_RADIUS;
        y = (int)(Math.random() * (height - APPLE_RADIUS*2)) + APPLE_RADIUS + 5;
        Canvas canvas = getHolder().lockCanvas();
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
        try {
            canvas.drawCircle(x, y, APPLE_RADIUS, paint);
        }
        catch (Exception ignored) { }
    }

    class GameThread extends Thread {
        Paint paint;

        public GameThread() {
            paint = new Paint();
            paint.setColor(Color.YELLOW);
            paint.setStyle(Paint.Style.FILL);
        }

        @Override
        public void run() {
            Canvas canvas;
            try {
                for (int i = 0; i < 100000; i++) {
                    canvas = getHolder().lockCanvas();

                    canvas.drawColor(Color.BLUE);
                    canvas.drawCircle(x, y, radius * 5, paint);

                    getHolder().unlockCanvasAndPost(canvas);

                    x += speedX;
                    y += speedY;

                    if (Math.random() < 1/150d)
                        GenerateApple();

                    sleep(1/30);
                }
            }
            catch (Exception ignored) { }
        }
    }
}
