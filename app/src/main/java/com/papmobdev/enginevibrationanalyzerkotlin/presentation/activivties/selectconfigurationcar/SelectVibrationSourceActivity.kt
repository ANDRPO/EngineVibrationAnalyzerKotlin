package com.papmobdev.enginevibrationanalyzerkotlin.presentation.activivties.selectconfigurationcar

import android.os.Bundle
import com.papmobdev.enginevibrationanalyzerkotlin.R
import com.papmobdev.enginevibrationanalyzerkotlin.databinding.ActivitySelectVibrationSourceBinding
import com.papmobdev.enginevibrationanalyzerkotlin.presentation.base.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class SelectVibrationSourceActivity : BaseActivity() {
    private val viewModel: SelectVibrationSourceViewModel by viewModel()
    private lateinit var binding: ActivitySelectVibrationSourceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            binding<ActivitySelectVibrationSourceBinding>(R.layout.activity_select_vibration_source).value.apply {
                lifecycleOwner = this@SelectVibrationSourceActivity
                viewModel = this@SelectVibrationSourceActivity.viewModel
            }
    }
}