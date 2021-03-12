package com.papmobdev.enginevibrationanalyzerkotlin.presentation.activivties.manual

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.papmobdev.domain.cars.models.TypesFuel
import com.papmobdev.domain.cars.models.TypesSource
import com.papmobdev.enginevibrationanalyzerkotlin.R
import com.papmobdev.enginevibrationanalyzerkotlin.databinding.ActivityManualBinding
import com.papmobdev.enginevibrationanalyzerkotlin.presentation.activivties.diagnostic.DiagnosticActivity
import com.papmobdev.enginevibrationanalyzerkotlin.presentation.base.BaseActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
class ManualActivity : BaseActivity() {

    private val viewModel: ManualViewModel by viewModel()
    private lateinit var binding: ActivityManualBinding

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, ManualActivity::class.java)
            context.startActivity(intent)
        }

        private const val DEFAULT_TYPE_SOURCE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            binding<ActivityManualBinding>(R.layout.activity_manual).value.apply {
                lifecycleOwner = this@ManualActivity
                viewModel = this@ManualActivity.viewModel
            }
        initObserve()
        initClickListeners()
    }

    private fun initObserve() {
        viewModel.configuration().observe(this, { result ->
            result.onSuccess {
                binding.imageManual.setImageResource(
                    when (it.fkTypeSource ?: DEFAULT_TYPE_SOURCE) {
                        TypesSource.FRONT_PANEL.idType -> R.drawable.image_panel_vector_device
                        TypesSource.ENGINE.idType -> R.drawable.image_engine_vector_device
                        TypesSource.WHEEL.idType -> R.drawable.image_stwheel_vector_device
                        else -> R.drawable.image_panel_vector_device
                    }
                )
            }
            result.onFailure {
                binding.imageManual.setImageResource(R.drawable.image_engine_vector_device)
            }
        })
    }

    private fun initClickListeners() {
        binding.apply {
            back.setOnClickListener {
                onBackPressed()
            }
            next.setOnClickListener {
                DiagnosticActivity.start(this@ManualActivity)
            }
        }
    }

}