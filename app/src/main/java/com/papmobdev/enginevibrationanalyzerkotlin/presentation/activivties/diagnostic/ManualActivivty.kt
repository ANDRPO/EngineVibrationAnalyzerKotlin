package com.papmobdev.enginevibrationanalyzerkotlin.presentation.activivties.diagnostic

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.CompoundButton
import com.papmobdev.domain.cars.CodeOptionsCar
import com.papmobdev.enginevibrationanalyzerkotlin.R
import com.papmobdev.enginevibrationanalyzerkotlin.databinding.ActivityManualActivivtyBinding
import com.papmobdev.enginevibrationanalyzerkotlin.presentation.activivties.selectconfigurationcar.CarParameterListActivity
import com.papmobdev.enginevibrationanalyzerkotlin.presentation.base.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class ManualActivivty : BaseActivity() {

    private val viewModel: ManualViewModel by viewModel()
    private lateinit var binding: ActivityManualActivivtyBinding

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, ManualActivivty::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            binding<ActivityManualActivivtyBinding>(R.layout.activity_manual_activivty).value.apply {
                lifecycleOwner = this@ManualActivivty
                viewModel = this@ManualActivivty.viewModel
            }
    }
}