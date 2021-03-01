package com.papmobdev.enginevibrationanalyzerkotlin.presentation.activivties.diagnostic

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
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
            progress.observe(this@DiagnosticActivity, {
                binding.progressDiagnostic.progress = it
            })
            titleNotify.observe(this@DiagnosticActivity, {
                binding.textViewMessage.text = it
            })

            states.observe(this@DiagnosticActivity, {
                when (it) {
                    StateDiagnostic.Default -> applyDefaultState()
                    StateDiagnostic.Error -> applyError()
                    StateDiagnostic.PreStart -> applyPreStart()
                    StateDiagnostic.Start -> applyStart()
                    StateDiagnostic.Success -> applySuccess()
                }
            })

            message.observe(this@DiagnosticActivity, Observer {
                showMessage(it)
            })
        }
    }

    private fun initClickListeners() {
        /*controlTest.setOnClickListener {
            viewModel.apply {
                when (states.value) {
                    StateDiagnostic.Default -> viewModel.launchDiagnostic()
                    StateDiagnostic.Error -> viewModel.launchDiagnostic()
                    StateDiagnostic.PreStart -> viewModel.cancelDiagnostic()
                    StateDiagnostic.Start -> viewModel.cancelDiagnostic()
                    StateDiagnostic.Success -> viewModel.launchDiagnostic()
                    null -> viewModel.launchDiagnostic()
                }
            }
        }*/
    }

    private fun showMessage(message: String) =
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()


}