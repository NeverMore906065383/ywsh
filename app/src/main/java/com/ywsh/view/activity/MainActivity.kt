package com.ywsh.view.activity

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.ywsh.vm.MainViewModel
import com.ywsh.ywsh.R
import com.ywsh.ywsh.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Example of a call to a native method
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val mainViewModel = MainViewModel(this, binding)
        binding.viewModel = mainViewModel
        startActivity(Intent(this, WidgetActivity::class.java))
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String

    companion object {

        // Used to load the 'native-lib' library on application startup.
        init {
            System.loadLibrary("native-lib")
        }
    }

}
