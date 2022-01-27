package com.example.mygame;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Apple {
    public float x, y;
    final Paint paintRed = new Paint(), paintBrown = new Paint();
    final float APPLE_RADIUS = 15, STICK_WIDTH = APPLE_RADIUS * 0.1f,
            STICK_HEIGHT = STICK_WIDTH * 3.5f;
    SnakeView snakeView;

    public Apple(SnakeView snakeView) {
        this.snakeView = snakeView;
        x = (float)Math.random()*(snakeView.width - APPLE_RADIUS*2) + APPLE_RADIUS;
        y = (float)Math.random()*(snakeView.height - APPLE_RADIUS*2) + APPLE_RADIUS + 5;

        paintRed.setColor(Color.RED);
        paintRed.setStyle(Paint.Style.FILL);
        paintBrown.setColor(Color.rgb(255, 255, 0));
        paintBrown.setStyle(Paint.Style.FILL);
    }

    public void draw(Canvas canvas) {
        canvas.drawCircle(x, y, APPLE_RADIUS, paintRed);
        canvas.drawRect(
                x - STICK_WIDTH / 2,
                y + APPLE_RADIUS + STICK_HEIGHT,
                x + STICK_WIDTH / 2,
                y + APPLE_RADIUS * 0.9f, paintBrown);
    }

    public boolean checkCollision() {
        return Math.sqrt(Math.pow(x - snakeView.x, 2) + Math.pow(y - snakeView.y, 2)) <
                APPLE_RADIUS + snakeView.radius;
    }
}
