package com.papmobdev.enginevibrationanalyzerkotlin.presentation.activivties.diagnostic

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.papmobdev.enginevibrationanalyzerkotlin.R
import com.papmobdev.enginevibrationanalyzerkotlin.databinding.ActivityDiagnosticBinding
import com.papmobdev.enginevibrationanalyzerkotlin.presentation.base.BaseActivity
import kotlinx.android.synthetic.main.activity_diagnostic.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
class DiagnosticActivity : BaseActivity() {

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, DiagnosticActivity::class.java)
            context.startActivity(intent)
        }
    }

    private val viewModel: DiagnosticViewModel by viewModel()
    private lateinit var binding: ActivityDiagnosticBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            binding<ActivityDiagnosticBinding>(R.layout.activity_diagnostic).value.apply {
                lifecycleOwner = this@DiagnosticActivity
                viewModel = this@DiagnosticActivity.viewModel
            }
        initClickListeners()
        initObserve()
    }

    private fun initObserve() {
        viewModel.apply {
            time.observe(this@DiagnosticActivity, {
                binding.timerOutput.text = it
            })
            titleNotify.observe(this@DiagnosticActivity, {
                binding.textViewMessage.text = it
            })

            states.observe(this@DiagnosticActivity, {
                when (it) {
                    StateDiagnostic.Default -> applyDefaultState()
                    StateDiagnostic.Error -> {
                        applyError()
                        showMessage("Произошла ошибка при записи данных")
                    }
                    StateDiagnostic.PreStart -> applyPreStart()
                    StateDiagnostic.Start -> applyStart()
                    StateDiagnostic.Success -> applySuccess()
                }
            })
        }
    }

    private fun initClickListeners() {
        controlTest.setOnClickListener {
            viewModel.apply {
                when (states.value) {
                    StateDiagnostic.Default -> viewModel.startDiagnostic()
                    StateDiagnostic.Error -> viewModel.startDiagnostic()
                    StateDiagnostic.PreStart -> viewModel.cancelDiagnostic()
                    StateDiagnostic.Start -> viewModel.cancelDiagnostic()
                    StateDiagnostic.Success -> viewModel.startDiagnostic()
                    null -> viewModel.startDiagnostic()
                }
            }
        }
    }

    private fun showMessage(message: String) =
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()


}