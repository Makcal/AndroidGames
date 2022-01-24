package com.example.mygame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import java.util.Random;

public class RandomBallView extends SurfaceView implements SurfaceHolder.Callback {
    int x=500, y=500, speedX, speedY;
    final int MAX_SPEED = 5;

    public RandomBallView(Context context) {
        super(context);
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        new GameThread().start();
        RandomizeSpeed();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {

    }

    @Override
    public boolean performClick() {
        RandomizeSpeed();
        return super.performClick();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            performClick();
        }

        return super.onTouchEvent(event);
    }

    void RandomizeSpeed() {
        speedX = (int)(Math.random() * (MAX_SPEED*2 + 1)) - MAX_SPEED;
        speedY = (int)(Math.random() * (MAX_SPEED*2 + 1)) - MAX_SPEED;
    }

    class GameThread extends Thread {
        Paint paint;

        public GameThread() {
            paint = new Paint();
            paint.setColor(Color.RED);
            paint.setStyle(Paint.Style.FILL);
        }

        @Override
        public void run() {
            Canvas canvas;
            try {
                for (int i = 0; i < 100000; i++) {
                    canvas = getHolder().lockCanvas();

                    canvas.drawColor(Color.BLUE);
                    canvas.drawCircle(x, y, 40, paint);

                    getHolder().unlockCanvasAndPost(canvas);

                    x += speedX;
                    y += speedY;

                    sleep(1/30);
                }
            }
            catch (Exception ignored) { }
//            finally {
//            }
        }
    }
}
