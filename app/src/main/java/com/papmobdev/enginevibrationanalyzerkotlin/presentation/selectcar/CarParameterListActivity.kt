package com.papmobdev.enginevibrationanalyzerkotlin.presentation.selectcar

import android.os.Bundle
import com.papmobdev.enginevibrationanalyzerkotlin.R
import com.papmobdev.enginevibrationanalyzerkotlin.databinding.ActivityCarParameterListBinding
import com.papmobdev.enginevibrationanalyzerkotlin.presentation.base.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class CarParameterListActivity : BaseActivity() {
    private val viewModel: CarParameterListViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding<ActivityCarParameterListBinding>(R.layout.activity_car_parameter_list).value.apply {
            lifecycleOwner = this@CarParameterListActivity
            viewModel = this@CarParameterListActivity.viewModel
        }



    }
}