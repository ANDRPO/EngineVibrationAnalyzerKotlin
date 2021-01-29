package com.papmobdev.enginevibrationanalyzerkotlin.presentation.selectcar

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.papmobdev.enginevibrationanalyzerkotlin.R
import com.papmobdev.enginevibrationanalyzerkotlin.databinding.ActivitySelectCarBinding
import com.papmobdev.enginevibrationanalyzerkotlin.presentation.base.BaseActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
class SelectCarActivity : BaseActivity() {

    private val viewModel: SelectCarViewModel by viewModel()
    private val resultLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data

            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding<ActivitySelectCarBinding>(R.layout.activity_select_car).value.apply {
            lifecycleOwner = this@SelectCarActivity
            viewModel = this@SelectCarActivity.viewModel
        }

        fun openCarParameterList(typeOptionCars: TypeOptionCars, id: Int?) {
            val intent = Intent(this, CarParameterListActivity::class.java)
            intent.putExtra("typeOptions", typeOptionCars)
            intent.putExtra("id", id)
            resultLauncher.launch(intent)
        }
    }


}