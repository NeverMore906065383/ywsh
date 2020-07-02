package com.ywsh.widget;

import android.animation.TypeEvaluator;

public class PointEvaluator implements TypeEvaluator<Point> {
    @Override
    public Point evaluate(float fraction, Point startValue, Point endValue) {
        float x = startValue.getX() + (endValue.getX() - startValue.getX()) * fraction;
        float y = startValue.getY() + (endValue.getY() - startValue.getY()) * fraction;
        return new Point(x, y);
    }
}

