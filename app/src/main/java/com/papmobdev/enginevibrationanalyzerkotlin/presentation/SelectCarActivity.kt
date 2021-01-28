package com.papmobdev.enginevibrationanalyzerkotlin.presentation

import android.os.Bundle
import com.papmobdev.enginevibrationanalyzerkotlin.R
import com.papmobdev.enginevibrationanalyzerkotlin.databinding.ActivitySelectCarBinding
import com.papmobdev.enginevibrationanalyzerkotlin.presentation.base.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class SelectCarActivity : BaseActivity() {

    private val vviewModel: SelectCarViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding<ActivitySelectCarBinding>(R.layout.activity_select_car).value.apply {
            lifecycleOwner = this@SelectCarActivity
            viewModel = this@SelectCarActivity.vviewModel
        }
        
    }
}