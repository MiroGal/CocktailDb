package com.mirogal.cocktail.ui.base

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(this.javaClass.simpleName, "onCreate")
    }

    override fun onStart() {
        super.onStart()
        Log.d(this.javaClass.simpleName, "onStart")
    }

    override fun onPause() {
        Log.d(this.javaClass.simpleName, "onPause")
        super.onPause()
    }

    override fun onStop() {
        Log.d(this.javaClass.simpleName, "onStop")
        super.onStop()
    }

    override fun onDestroy() {
        Log.d(this.javaClass.simpleName, "onDestroy")
        super.onDestroy()
    }

}