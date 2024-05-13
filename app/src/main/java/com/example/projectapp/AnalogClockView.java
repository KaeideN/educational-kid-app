package com.example.projectapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class AnalogClockView extends View {

    private Paint paint;
    private int centerX, centerY;
    private int hour = 7;
    private int minute = 0;

    public AnalogClockView(Context context) {
        super(context);
        init();
    }

    public AnalogClockView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerX = w / 2;
        centerY = h / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int radius = (int) (Math.min(centerX, centerY) * 0.9);
        int adjustedCenterY = centerY + 10;
        // Draw clock face
        canvas.drawCircle(centerX, adjustedCenterY, radius, paint);

        // Draw hour hand
        float hourAngle = (hour % 12 + minute / 60.0f) * 360 / 12;
        float hourLength = radius * 0.4f;
        canvas.drawLine(centerX, adjustedCenterY,
                centerX + (float) (hourLength * Math.cos(Math.toRadians(hourAngle - 90))),
                adjustedCenterY + (float) (hourLength * Math.sin(Math.toRadians(hourAngle - 90))), paint);

        // Draw minute hand
        float minuteAngle = minute * 360 / 60;
        float minuteLength = radius * 0.6f;
        canvas.drawLine(centerX, adjustedCenterY,
                centerX + (float) (minuteLength * Math.cos(Math.toRadians(minuteAngle - 90))),
                adjustedCenterY + (float) (minuteLength * Math.sin(Math.toRadians(minuteAngle - 90))), paint);
    }

    public void increaseHour() {
        hour = (hour + 1) % 12;
        invalidate();
    }

    public void decreaseHour() {
        hour = (hour - 1 + 12) % 12;
        invalidate();
    }

    public void increaseMinute() {
        if (minute < 55) {
            minute = (minute + 5) % 60;
        } else {
            minute = 0; // Reset minutes to 0
            hour = (hour + 1) % 12; // Increment hour
        }
        invalidate();
    }


    public void decreaseMinute() {
        minute = (minute - 5 + 60) % 60;
        invalidate();
    }

    // Custom methods to retrieve hour and minute
    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }
}
