package com.ywsh.widget

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class CircleView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    var point: Point? = null
    var color: String? = null
    private val mPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    override fun onDraw(canvas: Canvas) {
        if (color != null) {
            mPaint.color = Color.parseColor(color)
        }
        if (point == null) {
            point = Point(RADIUS, RADIUS)
            drawCircle(canvas)
        } else {
            drawCircle(canvas)
        }
        invalidate()
    }

    private fun drawCircle(canvas: Canvas) {
        val x = point!!.x
        val y = point!!.y
        canvas.drawCircle(x, y, RADIUS, mPaint)
    }

    var animSet: AnimatorSet? = null

    fun startAnimation() {
        //let run apply also
        animSet?.start() ?: let {

            val anim = ObjectAnimator.ofObject(this, "point", PointEvaluator(),
                    Point(100.0f, 100.0f), Point(500.0f, 500.0f)).apply {
                repeatCount = ValueAnimator.INFINITE
                repeatMode = ValueAnimator.REVERSE
            }
            val anim2 = ObjectAnimator.ofObject(this, "color", ColorEvaluator(),
                    "#0000FF", "#FF0000").apply {
                repeatCount = ValueAnimator.INFINITE
                repeatMode = ValueAnimator.REVERSE
            }
            animSet = AnimatorSet()
            animSet?.apply {
                play(anim)?.with(anim2)
                duration = 2000
                start()
            }
        }
    }

    companion object {
        private const val RADIUS = 100.0f
    }

    init {
        mPaint.color = Color.BLUE
    }
}