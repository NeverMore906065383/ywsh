package com.ywsh.view.activity

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.ywsh.vm.MVVMViewModel
import com.ywsh.ywsh.R
import com.ywsh.ywsh.databinding.ActivityMvvmBinding

class MVVMActivity : AppCompatActivity() {
    lateinit var binding: ActivityMvvmBinding 
    private lateinit var mvvmViewModel: MVVMViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_mvvm)
        mvvmViewModel = MVVMViewModel(application, binding)
        //初始化viewModel
        binding?.viewModel = mvvmViewModel
    }
}