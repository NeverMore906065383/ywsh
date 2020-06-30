package com.ywsh.vm

import android.app.Activity
import android.content.Intent
import android.databinding.BaseObservable
import android.view.View
import com.ywsh.view.activity.AnimotionActivity
import com.ywsh.view.activity.MVVMActivity
import com.ywsh.view.activity.WidgetActivity
import com.ywsh.ywsh.databinding.ActivityMainBinding

/**
 * Copyright, 2020, WhyHow info, All right reserved.
 *
 * @author Administrator
 * Mail: liyongchao@whyhowinfo.com
 * Created Time: 2020/5/18
 * Descroption:
 */
class MainViewModel(private val activity: Activity, private val binding: ActivityMainBinding) : BaseObservable() {

    fun goActivity(view: View) {
        var startIntent = Intent(activity, AnimotionActivity::class.java)
        when (view) {
            binding.tvAIDL -> {
                startIntent = Intent(activity, AnimotionActivity::class.java)
            }
            binding.tvAnimation -> {
                startIntent = Intent(activity, AnimotionActivity::class.java)
            }
            binding.tvHandler -> {
                startIntent = Intent(activity, AnimotionActivity::class.java)
            }
            binding.tvMVVM -> {
                startIntent = Intent(activity, MVVMActivity::class.java)
            }
            binding.tvThreadPool -> {
                startIntent = Intent(activity, AnimotionActivity::class.java)
            }
            binding.tvWidget -> {
                startIntent = Intent(activity, WidgetActivity::class.java)
            }
        }
        activity.startActivity(startIntent)
    }
}