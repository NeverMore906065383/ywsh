package com.ywsh.widget

import android.animation.TypeEvaluator


/**
 * Copyright, 2020, WhyHow info, All right reserved.
 *
 * @author Administrator
 *  Mail: liyongchao@whyhowinfo.com
 * Created Time: 2020/7/2
 * Descroption:
 */
class ColorEvaluator : TypeEvaluator<String> {
    override fun evaluate(fraction: Float, startValue: String, endValue: String): String {
        val startRed = startValue.substring(1, 3).toInt(16)
        val startGreen = startValue.substring(3, 5).toInt(16)
        val startBlue = startValue.substring(5, 7).toInt(16)
        val endRed = endValue.substring(1, 3).toInt(16)
        val endGreen = endValue.substring(3, 5).toInt(16)
        val endBlue = endValue.substring(5, 7).toInt(16)
        // 初始化颜色的值
        var mCurrentRed = startRed
        var mCurrentGreen = startGreen
        var mCurrentBlue = startBlue
        // 计算初始颜色和结束颜色之间的差值
        val redDiff = Math.abs(startRed - endRed)
        val greenDiff = Math.abs(startGreen - endGreen)
        val blueDiff = Math.abs(startBlue - endBlue)
        val colorDiff = redDiff + greenDiff + blueDiff
        if (mCurrentRed != endRed) {
            mCurrentRed = getCurrentColor(startRed, endRed, colorDiff, 0,
                    fraction)
        } else if (mCurrentGreen != endGreen) {
            mCurrentGreen = getCurrentColor(startGreen, endGreen, colorDiff,
                    redDiff, fraction)
        } else if (mCurrentBlue != endBlue) {
            mCurrentBlue = getCurrentColor(startBlue, endBlue, colorDiff,
                    redDiff + greenDiff, fraction)
        }
        // 将计算出的当前颜色的值组装返回
        return ("#" + getHexString(mCurrentRed)
                + getHexString(mCurrentGreen) + getHexString(mCurrentBlue))
    }

    /**
     * 根据fraction值来计算当前的颜色。
     */
    private fun getCurrentColor(startColor: Int, endColor: Int, colorDiff: Int,
                                offset: Int, fraction: Float): Int {
        var currentColor: Int
        if (startColor > endColor) {
            currentColor = (startColor - (fraction * colorDiff - offset)).toInt()
            if (currentColor < endColor) {
                currentColor = endColor
            }
        } else {
            currentColor = (startColor + (fraction * colorDiff - offset)).toInt()
            if (currentColor > endColor) {
                currentColor = endColor
            }
        }
        return currentColor
    }

    /**
     * 将10进制颜色值转换成16进制。
     */
    private fun getHexString(value: Int): String {
        var hexString = Integer.toHexString(value)
        if (hexString.length == 1) {
            hexString = "0$hexString"
        }
        return hexString
    }

}