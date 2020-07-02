package com.ywsh.vm

import android.databinding.BaseObservable
import android.view.View
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
        widgetBinding.circleview.startAnimation()
//        val anim = ObjectAnimator.ofObject(widgetBinding.circleview, "point", PointEvaluator(),
//                Point(000.0f, 000.0f), Point(500.0f, 500.0f)).setDuration(2000)
//        anim.repeatCount = ValueAnimator.INFINITE
//        anim.repeatMode = ValueAnimator.REVERSE
//        anim.start()

//        val anim2 = ObjectAnimator.ofObject(widgetBinding.circleview, "color", ColorEvaluator(),
//                "#0000FF", "#FF0000").setDuration(2000)
//        anim2.repeatCount = ValueAnimator.INFINITE
//        anim2.repeatMode = ValueAnimator.REVERSE
//        anim2.start()

    }

}

