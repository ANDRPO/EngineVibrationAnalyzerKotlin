package com.papmobdev.enginevibrationanalyzerkotlin.presentation.base

import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

open class BaseActivity : AppCompatActivity() {

    inline fun <reified T : ViewDataBinding> binding(@LayoutRes resId: Int): Lazy<T> =
        lazy { DataBindingUtil.setContentView(this, resId) }

}