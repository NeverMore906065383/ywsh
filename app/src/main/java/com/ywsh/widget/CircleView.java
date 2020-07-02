package com.ywsh.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class CircleView extends View {

    private static final float RADIUS = 100.0f;

    private Point point;

    private Paint mPaint;

    public CircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLUE);
    }

    @Override
    protected void onDraw(Canvas canvas){
        if (point == null){
            point = new Point(RADIUS, RADIUS);
            drawCircle(canvas);
        } else {
            drawCircle(canvas);
        }
        invalidate();
    }

    private void drawCircle(Canvas canvas) {
        float x = point.getX();
        float y = point.getY();
        canvas.drawCircle(x, y, RADIUS, mPaint);
    }

    public Point getPoint(){
        return point;
    }

    public void setPoint(Point point){
        this.point = point;
    }
}

