package com.example.projectapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

public class RedBallView extends View {

    private static final int BALL_RADIUS = 50;
    private static final int BALL_COLOR = Color.RED;
    private static final int ANIMATION_DELAY = 16; // 60 FPS
    private static final int BALL_SPEED = 10; // Speed of the ball

    private Paint paint;
    private float ballX, ballY; // Position of the red ball
    private float ballSpeedX, ballSpeedY; // Speed of the red ball
    private int screenWidth, screenHeight;
    private int direction = 1; // 1 for moving right, -1 for moving left

    private Handler handler;
    private Runnable animationRunnable = new Runnable() {
        @Override
        public void run() {
            moveBall();
            invalidate(); // Redraw the view
            handler.postDelayed(this, ANIMATION_DELAY); // Repeat the animation
        }
    };

    public RedBallView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setColor(BALL_COLOR);
        handler = new Handler();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        screenWidth = w;
        screenHeight = h;
        resetBallPosition();
    }

    private void resetBallPosition() {
        ballX = BALL_RADIUS; // Start from the left corner
        ballY = BALL_RADIUS; // Start from the top corner
        ballSpeedX = BALL_SPEED; // Start moving to the right
        ballSpeedY = 0; // No vertical movement initially
    }

    private void moveBall() {
        ballX += ballSpeedX * direction;
        ballY += ballSpeedY;

        // Check if the ball reaches the right corner or left corner
        if (ballX + BALL_RADIUS >= screenWidth || ballX - BALL_RADIUS <= 0) {
            ballY += BALL_RADIUS * 2; // Move down
            ballX = ballX <= BALL_RADIUS ? screenWidth - BALL_RADIUS : BALL_RADIUS; // Teleport to the next line
        }

        // Check if the ball reaches the bottom edge
        if (ballY + BALL_RADIUS >= screenHeight) {
            ballY = BALL_RADIUS; // Teleport to the top-left corner (starting corner)
            ballX = BALL_RADIUS; // Start from the left corner
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(ballX, ballY, BALL_RADIUS, paint);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        handler.post(animationRunnable); // Start the animation when view is attached
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        handler.removeCallbacks(animationRunnable); // Stop the animation when view is detached
    }
}
