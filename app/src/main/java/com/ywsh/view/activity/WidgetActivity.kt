package com.ywsh.view.activity

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.ywsh.util.LogUtils
import com.ywsh.vm.WidgetViewModel
import com.ywsh.ywsh.R
import com.ywsh.ywsh.databinding.ActivityWidgetBinding

/**
 * Copyright, 2020, WhyHow info, All right reserved.
 *
 * @author Administrator
 *  Mail: liyongchao@whyhowinfo.com
 * Created Time: 2020/6/29
 * Descroption:
 */
class WidgetActivity : AppCompatActivity() {
        private lateinit var binding: ActivityWidgetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LogUtils.e("WidgetActivity@@@")
        binding = DataBindingUtil.setContentView(this, R.layout.activity_widget)
        val widgetViewModel = WidgetViewModel(binding)
        binding?.viewModel=widgetViewModel
    }

}