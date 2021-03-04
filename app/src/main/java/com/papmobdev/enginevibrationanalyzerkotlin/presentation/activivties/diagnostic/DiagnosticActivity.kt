package com.papmobdev.enginevibrationanalyzerkotlin.presentation.activivties.diagnostic

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.asLiveData
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
                if (viewModel == null)
                    viewModel = this@DiagnosticActivity.viewModel
            }

        initClickListeners()
        initObserve()
        if (viewModel.stateView.value == null) {
            Log.e("STATEVIEWMODEL", viewModel.stateView.value.toString())
            viewModel._stateView.value = StatesViewDiagnostic.DEFAULT
        }
    }

    private fun initObserve() {
        viewModel.apply {
            stateView.observe(this@DiagnosticActivity, {
                Log.e("STATEINITOBSERVE", it.toString())
                when (it?.let { return@let it } ?: StatesViewDiagnostic.DEFAULT) {
                    StatesViewDiagnostic.DEFAULT -> applyDefault()
                    StatesViewDiagnostic.SUCCESS -> applySuccess()
                    StatesViewDiagnostic.ERROR -> applyError()
                    StatesViewDiagnostic.START -> applyStart()
                    StatesViewDiagnostic.CANCEL -> applyCancel()
                }
            })
        }
    }

    private fun initClickListeners() {
        controlTest.setOnClickListener {
            viewModel.apply {
                when (stateView.value) {
                    StatesViewDiagnostic.DEFAULT -> viewModel.startDiagnostic()
                    StatesViewDiagnostic.ERROR -> viewModel.startDiagnostic()
                    StatesViewDiagnostic.CANCEL -> viewModel.startDiagnostic()
                    StatesViewDiagnostic.START -> viewModel.stopDiagnostic()
                    StatesViewDiagnostic.SUCCESS -> viewModel.startDiagnostic()
                }
            }
        }
    }

    override fun onPause() {
        if (viewModel.stateView.value == StatesViewDiagnostic.START) viewModel.stopDiagnostic()
        super.onPause()
    }

    private fun showMessage(message: String) =
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

}