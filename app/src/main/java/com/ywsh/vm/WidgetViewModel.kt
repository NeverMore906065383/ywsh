package com.ywsh.vm

import android.animation.ObjectAnimator
import android.databinding.BaseObservable
import android.view.View
import com.ywsh.widget.Point
import com.ywsh.widget.PointEvaluator
import com.ywsh.ywsh.databinding.ActivityWidgetBinding

/**
 * Copyright, 2020, WhyHow info, All right reserved.
 *
 * @author Administrator
 * Mail: liyongchao@whyhowinfo.com
 * Created Time: 2020/5/18
 * Descroption:
 */
class WidgetViewModel(private val widgetBinding: ActivityWidgetBinding) : BaseObservable() {


    fun buttonclick(view: View) {
        widgetBinding.roullete.rotate(true)

        var anim = ObjectAnimator.ofObject(widgetBinding.circleview, "point", PointEvaluator(),
        Point(100.0f, 100.0f),  Point(500.0f, 500.0f))
        anim.setDuration(2000)
        anim.start()
    }

}