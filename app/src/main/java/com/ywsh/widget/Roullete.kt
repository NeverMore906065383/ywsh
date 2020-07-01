package com.ywsh.widget

import android.animation.Animator
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
    private lateinit var mOutArcPaint: Paint
    private lateinit var mInsideArcPaint: Paint
    private var colorList = listOf(Color.RED, Color.BLUE, Color.GREEN, Color.RED, Color.BLUE, Color.GREEN, Color.RED, Color.BLUE, Color.GREEN)
    private var isOutsideSelectedItem = -1
    private var isInsideSelectedItem = -1
    private var selectSameArea = false
    private var mDiverAnimator: ValueAnimator? = null
    private var mReturnAnimator: ValueAnimator? = null
    private var animatedValue: Float = 0f
    private val TAG = "Roullete-"
    private var outAreaItem: MutableList<Int> = mutableListOf()
    private var insideAreaItem: MutableList<Int> = mutableListOf()

    companion object {
        const val outCircleRadios = 300f
        const val inCircleRadios = 200f
        const val strokeWidth = 100f
        const val animateDuration = 500L
    }


    private var moutRect = RectF(0f, 0f, 0f, 0f)
    private var minsideRect = RectF(0f, 0f, 0f, 0f)
    private fun init() {
        println("roullete: init fun ")
        setOnTouchListener(this)
        initPaint()


    }

    private fun initPaint() {

        mBgPaint = Paint()
        mBgPaint.color = Color.WHITE
        mBgPaint.style = Paint.Style.FILL
        mBgPaint.strokeWidth = 4f


        mSpPaint = Paint()
        mSpPaint.color = Color.DKGRAY
        mSpPaint.style = Paint.Style.STROKE
        mSpPaint.strokeWidth = 4f

        mOutArcPaint = Paint()
        mOutArcPaint.color = Color.BLUE
        mOutArcPaint.style = Paint.Style.STROKE
        mOutArcPaint.strokeWidth = strokeWidth

        mInsideArcPaint = Paint()
        mInsideArcPaint.color = Color.GREEN
        mInsideArcPaint.style = Paint.Style.FILL
        mInsideArcPaint.strokeWidth = strokeWidth
    }

    init {
        println("Roullete init ")
        init()
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawColor(Color.YELLOW)

        drawBackGroundCircle(canvas)

        drawSeparatedArea(canvas)

    }

    private fun drawSeparatedArea(canvas: Canvas) {
        canvas.drawCircle((width / 2).toFloat(), (height / 2).toFloat(), outCircleRadios, mSpPaint)
        canvas.drawCircle((width / 2).toFloat(), (height / 2).toFloat(), inCircleRadios, mSpPaint)

        canvas.translate((width / 2).toFloat(), (height / 2).toFloat())

        outAreaItem.clear()
        insideAreaItem.clear()

        var offsetWidth = strokeWidth * animatedValue / 360
        for (i in 0..359 step 60) {
            if (i % 120 == 0) {//外部区域
                outAreaItem.add(120 + i)
                if (isOutsideSelectedItem == i / 120) {
                    canvas.translate(offsetWidth, offsetWidth)
                    canvas.drawArc(moutRect, 0f, 120f, false, mOutArcPaint)
                    canvas.translate(-offsetWidth, -offsetWidth)
                } else {
                    canvas.drawArc(moutRect, 0f, 120f, false, mOutArcPaint)
                }
                mOutArcPaint.color = this.colorList[i / 60]
                canvas.drawLine(200f, 0f, outCircleRadios, 0f, mSpPaint)
            }
            canvas.rotate(60f)
        }
        for (i in 0..359 step 60) {
            if (i % 60 == 0 && i % 120 != 0) {//内部区域
                insideAreaItem.add(i + 120)
                if (isInsideSelectedItem == i / 120) {
                    canvas.translate(offsetWidth, offsetWidth)
                    canvas.drawArc(minsideRect, 0f, 120f, true, mInsideArcPaint)
                    canvas.translate(-offsetWidth, -offsetWidth)
                } else {
                    canvas.drawArc(minsideRect, 0f, 120f, true, mInsideArcPaint)
                }
                mInsideArcPaint.color = this.colorList.get(i / 60)
                canvas.drawLine(0f, 0f, inCircleRadios, 0f, mSpPaint)
            }
            canvas.rotate(60f)
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        LogUtils.i(TAG + "onSizeChanged" + width.toFloat())
        moutRect = RectF(-outCircleRadios + strokeWidth / 2, -outCircleRadios + strokeWidth / 2
                , outCircleRadios - strokeWidth / 2, outCircleRadios - strokeWidth / 2)

        minsideRect = RectF(-inCircleRadios, -inCircleRadios
                , inCircleRadios, inCircleRadios)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        LogUtils.i(TAG + "onMeasure")
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        LogUtils.i(TAG + "onLayout")
    }

    private fun drawBackGroundCircle(canvas: Canvas) {
        canvas.drawCircle((width / 2).toFloat(), (height / 2).toFloat(), outCircleRadios, mBgPaint)
    }

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y
        if (event.action == MotionEvent.ACTION_DOWN) {
            calcDistance(x, y)
            startAnimator(animateDuration)
        }
        return true
    }


    //计算相对中心点的距离 计算属于内环外环
    private fun calcDistance(x: Float, y: Float) {
        var inout: String
        var outItemIndex = -1
        var inItemIndex = -1
        //外部圆形
        var len = sqrt(((width / 2 - x).toDouble()).pow(2.0) + ((height / 2 - y).toDouble()).pow(2.0))
        if (len >= 200) {
            outItemIndex = calcOutAreaItemIndex(x, y, outAreaItem, 0f)
            inout = "out"
        } else {//内部圆形
            inItemIndex = calcInsideAreaItemIndex(x, y, insideAreaItem, 0f)
            inout = "in"
        }
        if (isOutsideSelectedItem != -1 || isInsideSelectedItem != -1) {
            selectSameArea = isOutsideSelectedItem == outItemIndex || isInsideSelectedItem == inItemIndex
        }
        isOutsideSelectedItem = outItemIndex
        isInsideSelectedItem = inItemIndex
        Toast.makeText(this@Roullete.context, "$inout len:$len degree：$isOutsideSelectedItem ", Toast.LENGTH_SHORT).show()
    }

    /**
     * 以按钮圆心为坐标圆点，建立坐标系，求出(targetX, targetY)坐标与x轴的夹角
     *
     * @param targetX x坐标
     * @param targetY y坐标
     * @return (targetX, targetY)坐标与x轴的夹角
     */
    private fun calcAngle(targetX: Float, targetY: Float): Float {
        val x = targetX - width / 2//len/2 圆点x坐标
        val y = targetY - height / 2//len/2 圆点y坐标
        val radian: Double

        if (x != 0f) {
            val tan = Math.abs(y / x)
            if (x > 0) {
                if (y >= 0) {
                    radian = Math.atan(tan.toDouble())
                } else {
                    radian = 2 * Math.PI - Math.atan(tan.toDouble())
                }
            } else {
                if (y >= 0) {
                    radian = Math.PI - Math.atan(tan.toDouble())
                } else {
                    radian = Math.PI + Math.atan(tan.toDouble())
                }
            }
        } else {
            if (y > 0) {
                radian = Math.PI / 2
            } else {
                radian = -Math.PI / 2
            }
        }
        return (radian * 180 / Math.PI).toFloat()
    }

    private fun calcOutAreaItemIndex(x: Float, y: Float, areaItem: MutableList<Int>, offset: Float): Int {
        val calcAngle = calcAngle(x, y)

        val size = areaItem.size
        for (i in 0 until size) {
            if (calcAngle < areaItem.get(i)) {
                LogUtils.w("calcOutAreaItemIndex +$calcAngle +： ${areaItem.get(i)}+ i : $i)")
                return i
            }
        }
        return -1

    }

    private fun calcInsideAreaItemIndex(x: Float, y: Float, areaItem: MutableList<Int>, offset: Float): Int {
        var calcAngle = calcAngle(x, y)

        val size = areaItem.size
        for (i in 0 until size) {

            if (calcAngle < 60) {
                calcAngle += 360f
            }
            if (calcAngle < areaItem.get(i)) {
                LogUtils.w("calcOutAreaItemIndex +$calcAngle +： ${areaItem.get(i)}+ i : $i)")
                return i
            }
        }
        return -1

    }

    private fun startAnimator(duration: Long) {
        if (mDiverAnimator != null && mDiverAnimator!!.isRunning) {
            mDiverAnimator?.cancel()
//            mDiverAnimator?.start()
        } else {
            mDiverAnimator = ValueAnimator.ofFloat(0f, 360f).setDuration(duration)
            var timeInterpolator = AccelerateDecelerateInterpolator()
            mDiverAnimator?.interpolator = timeInterpolator
            mDiverAnimator?.addUpdateListener(AnimatorUpdateListener { animation ->
                animatedValue = animation.animatedValue as Float
                invalidate()
            })
            mDiverAnimator?.addListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(animation: Animator?) {
                }

                override fun onAnimationEnd(animation: Animator?) {
                    LogUtils.e("@@onAnimationEnd")
                    backAnimator(animateDuration)
                }

                override fun onAnimationCancel(animation: Animator?) {
                    LogUtils.e("@@onAnimationCancel")
                }

                override fun onAnimationStart(animation: Animator?) {
                }
            })
            mDiverAnimator?.start()
        }
    }

    private fun backAnimator(duration: Long) {
        if (mReturnAnimator != null && mReturnAnimator!!.isRunning) {
            mReturnAnimator?.cancel()
//            mReturnAnimator?.start()
        } else {
            mReturnAnimator = ValueAnimator.ofFloat(360f, 0f).setDuration(duration)
            var timeInterpolator = AccelerateDecelerateInterpolator()
            mReturnAnimator?.interpolator = timeInterpolator
            mReturnAnimator?.addUpdateListener(AnimatorUpdateListener { animation ->
                animatedValue = animation.animatedValue as Float
                invalidate()
            })
            mReturnAnimator?.addListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(animation: Animator?) {

                }

                override fun onAnimationEnd(animation: Animator?) {
                    post { isOutsideSelectedItem = -1;isInsideSelectedItem = -1;postInvalidate() }
                    selectSameArea = false
                }

                override fun onAnimationCancel(animation: Animator?) {
                    selectSameArea = false

                }

                override fun onAnimationStart(animation: Animator?) {
                }
            })
            mReturnAnimator?.start()
        }
    }
}