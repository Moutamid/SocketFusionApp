package com.moutamid.socketfusiontimer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class CustomSeekBar extends View {

    private int max = 14;
    private int min = 8;
    private int progress = 9;
    private Paint paint;
    private Paint textPaint;

    public CustomSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(getResources().getColor(R.color.appColor));
        paint.setStrokeWidth(10);

        textPaint = new Paint();
        textPaint.setColor(getResources().getColor(R.color.black));
        textPaint.setTextSize(40);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // Draw the track
        int width = getWidth();
        int height = getHeight();
        canvas.drawLine(0, height / 2, width, height / 2, paint);

        // Draw the thumb
        float thumbX = (progress - min) * width / (max - min);
        canvas.drawCircle(thumbX, height / 2, 20, paint);

        // Draw the progress text
        canvas.drawText(String.valueOf(progress), thumbX - 20, height / 2 - 30, textPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                float x = event.getX();
                int width = getWidth();
                progress = (int) (min + (x / width) * (max - min));
                progress = Math.max(min, Math.min(max, progress));
                invalidate();
                return true;
        }
        return super.onTouchEvent(event);
    }
}
