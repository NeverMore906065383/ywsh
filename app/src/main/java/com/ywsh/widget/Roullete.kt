package com.ywsh.widget

import android.animation.ValueAnimator
import android.animation.ValueAnimator.AnimatorUpdateListener
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Toast
import com.ywsh.util.LogUtils
import kotlin.math.pow
import kotlin.math.sqrt


/**
 * Copyright, 2020, WhyHow info, All right reserved.
 *
 * @author Administrator
 *  Mail: liyongchao@whyhowinfo.com
 * Created Time: 2020/6/29
 * Descroption:
 */

class Roullete(context: Context, attributeSet: AttributeSet) : View(context), View.OnTouchListener {
    private lateinit var mBgPaint: Paint
    private lateinit var mSpPaint: Paint
    private lateinit var mArcPaint: Paint
    private var colorList = listOf(Color.RED, Color.BLUE, Color.GREEN, Color.RED, Color.BLUE, Color.GREEN, Color.RED, Color.BLUE, Color.GREEN)
    private var isSelectedItem = -1
    private var animator: ValueAnimator? = null
    private var animatedValue: Float = 0f
    private val TAG = "Roullete-"
    private var outAreaItem = listOf("")

    companion object {
        const val outCircleRadios = 300f
        const val inCircleRadios = 200f
        const val strokeWidth = 100f
    }


    private var mRect = RectF(0f, 0f, 0f, 0f)
    private var mSelectRect = RectF(0f, 0f, 0f, 0f)
    private fun init() {
        println("roullete: init fun ")
        setOnTouchListener(this)
        initPaint()


    }

    private fun initPaint() {

        mBgPaint = Paint()
        mBgPaint.color = Color.MAGENTA
        mBgPaint.style = Paint.Style.FILL
        mBgPaint.strokeWidth = 4f


        mSpPaint = Paint()
        mSpPaint.color = Color.DKGRAY
        mSpPaint.style = Paint.Style.STROKE
        mSpPaint.strokeWidth = 4f

        mArcPaint = Paint()
        mArcPaint.color = Color.BLACK
        mArcPaint.style = Paint.Style.STROKE
        mArcPaint.strokeWidth = strokeWidth
    }

    init {
        println("Roullete init ")
        init()
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawColor(Color.YELLOW)

        drawBg(canvas)
        drawSeparated(canvas)

    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return super.onTouchEvent(event)

    }


    private fun drawSeparated(canvas: Canvas) {
        canvas.drawCircle((width / 2).toFloat(), (height / 2).toFloat(), 300F, mSpPaint)
        canvas.drawCircle((width / 2).toFloat(), (height / 2).toFloat(), 200f, mSpPaint)

        canvas.translate((width / 2).toFloat(), (height / 2).toFloat())

//        canvas.drawArc(mRect, 0f, animatedValue - 60, false, mArcPaint)

        //刻度线长度为20，一圈是360度，并且秒针转一圈为60秒，所以一秒就对应360度/60秒=6度，那么五秒也就是5*6 = 30度
        for (i in 0..359 step 60) {
            if (i % 60 == 0 && i % 120 != 0) {//外部区域
                if (isSelectedItem!=0) {
                    canvas.drawArc(mRect, 0f, 120f, false, mArcPaint)
                } else {
                    canvas.translate(strokeWidth, strokeWidth)
                    canvas.drawArc(mRect, 0f, 120f, false, mArcPaint)
                    canvas.translate(-strokeWidth, -strokeWidth)
                }
                mArcPaint.color = this.colorList.get(i / 60)
                canvas.drawLine(200f, 0f, outCircleRadios, 0f, mSpPaint)
            } else if (i % 120 == 0) {//内部区域
//                canvas.drawLine(0f, 0f, 200f, 0f, mSpPaint)
            }
            canvas.rotate(60f)
        }
    }

    fun drawArc() {

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        LogUtils.i(TAG + "onSizeChanged" + width.toFloat())
        mRect = RectF(-outCircleRadios + strokeWidth / 2, -outCircleRadios + strokeWidth / 2
                , outCircleRadios - strokeWidth / 2, outCircleRadios - strokeWidth / 2)
//        mSelectRect = RectF(-outCircleRadios + strokeWidth / 2, -outCircleRadios + strokeWidth / 2
//                , outCircleRadios - strokeWidth / 2, outCircleRadios - strokeWidth / 2)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        LogUtils.i(TAG + "onMeasure")
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        LogUtils.i(TAG + "onLayout")
    }

    private fun drawBg(canvas: Canvas) {
        canvas.drawCircle((width / 2).toFloat(), (height / 2).toFloat(), outCircleRadios, mBgPaint)
    }

    override fun onTouch(v: View, event: MotionEvent): Boolean {


        val x = event.x
        val y = event.y
        LogUtils.e("onTouchEvent:" + x + " y :" + y)
        calcDistance(x, y)
        isSelectedItem = 0
        postDelayed(Runnable { isSelectedItem = -1;postInvalidate() }, 1000)
        initAnimator(5000)
        return true
    }

    //计算相对中心点的距离 计算属于内环外环
    private fun calcDistance(x: Float, y: Float) {
        var inout: String
        //外部圆形
        var len = sqrt(((width / 2 - x).toDouble()).pow(2.0) + ((height / 2 - y).toDouble()).pow(2.0))
        if (len >= 200) {
            calcAreaItemIndex(x, y)

            inout = "out"
        } else {//内部圆形
            inout = "in"
        }
        Toast.makeText(this@Roullete.context, "$inout len:$len ", Toast.LENGTH_SHORT).show()
        animator?.start()
    }

    private fun calcAreaItemIndex(x: Float, y: Float) {


    }

    private fun initAnimator(duration: Long) {
        if (animator != null && animator!!.isRunning) {
            animator?.cancel()
            animator?.start()
        } else {
            animator = ValueAnimator.ofFloat(0f, 360f).setDuration(duration)
            var timeInterpolator = AccelerateDecelerateInterpolator()
            animator?.interpolator = timeInterpolator
            animator?.addUpdateListener(AnimatorUpdateListener { animation ->
                animatedValue = animation.animatedValue as Float
                LogUtils.i(TAG + "animatedValue:" + animatedValue)
                invalidate()
            })
            animator?.start()
        }
    }
}