package com.example.mygame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.AsyncPlayer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import java.util.ArrayDeque;

public class SnakeView extends SurfaceView implements SurfaceHolder.Callback {
    public float x=500, y=1000, radius=15;
    float speedX=0, speedY=1, speedCoefficient=1;
    public int width, height;
    ArrayDeque<Apple> apples = new ArrayDeque<>();

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
    protected void onSizeChanged(int w, int h, int oldW, int oldH) {
        width = w;
        height = h;
        super.onSizeChanged(w, h, oldW, oldH);
    }

    void GenerateApple() {
        Apple apple = new Apple(this);
        Canvas canvas = getHolder().lockCanvas();
        apple.draw(canvas);
        apples.add(apple);
    }

    class GameThread extends Thread {
        Paint paint;

        public GameThread() {
            paint = new Paint();
            paint.setColor(Color.YELLOW);
            paint.setStyle(Paint.Style.FILL);
        }

        void onPostTick() {
            x += speedX * speedCoefficient;
            y += speedY * speedCoefficient;

            for (Apple apple : apples) {
                if (apple.checkCollision()) {
                    radius += 5;
                    speedCoefficient += 1;
                    apples.remove(apple);
                }
            }

            if (Math.random() < 1/150d)
                GenerateApple();
        }

        @Override
        public void run() {
            Canvas canvas;
            try {
                while (true) {
                    canvas = getHolder().lockCanvas();
                    canvas.drawColor(Color.BLUE);
                    canvas.drawCircle(x, y, radius, paint);
                    for (Apple apple : apples)
                        apple.draw(canvas);

                    getHolder().unlockCanvasAndPost(canvas);

                    onPostTick();
                    //noinspection BusyWait
                    sleep(1/30);
                }
            }
            catch (Exception ignored) {
                Log.e("ERROR", ignored.getMessage() + ignored.getStackTrace());
            }
        }
    }
}
