package com.ywsh.vm

import android.app.Application
import android.databinding.BaseObservable
import android.databinding.Bindable
import android.util.Log
import android.view.View
import com.ywsh.bean.Account
import com.ywsh.callback.MCallBack
import com.ywsh.model.MVVMModel
import com.ywsh.ywsh.BR
import com.ywsh.ywsh.databinding.ActivityMvvmBinding

/**
 * Copyright, 2020, WhyHow info, All right reserved.
 *
 * @author Administrator
 * Mail: liyongchao@whyhowinfo.com
 * Created Time: 2020/5/18
 * Descroption:
 */
class MVVMViewModel(application: Application?, private val binding: ActivityMvvmBinding) : BaseObservable() {
    private val mvvmModel: MVVMModel = MVVMModel()
    private var Input: String? = null

    @get:Bindable
    var result: String? = null
        private set

    fun setResult(result: String) {
        this.result = result
        notifyPropertyChanged(BR.result)
        Log.e("setResult:", "result:$result")
    }

    fun getData(view: View?) {
        Input = binding.etAccount.text.toString()
        mvvmModel.getAccountData(Input, object : MCallBack {
            override fun onSuccess(account: Account) {
                val info = account.name + "|" + account.level
                setResult(info)
            }

            override fun onFailed() {
                setResult("消息获取失败")
            }
        })
    }

}