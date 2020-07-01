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
    }

}