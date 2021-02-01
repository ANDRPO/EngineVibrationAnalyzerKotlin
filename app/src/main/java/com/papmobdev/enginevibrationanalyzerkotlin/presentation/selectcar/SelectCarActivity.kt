package com.papmobdev.enginevibrationanalyzerkotlin.presentation.selectcar

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.observe
import com.papmobdev.domain.cars.models.CarGeneration
import com.papmobdev.domain.cars.models.CarMark
import com.papmobdev.domain.cars.models.CarModel
import com.papmobdev.enginevibrationanalyzerkotlin.R
import com.papmobdev.enginevibrationanalyzerkotlin.databinding.ActivitySelectCarBinding
import com.papmobdev.enginevibrationanalyzerkotlin.presentation.base.BaseActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
class SelectCarActivity : BaseActivity() {

    private val viewModel: SelectCarViewModel by viewModel()
    private lateinit var binding: ActivitySelectCarBinding

    private val regex = Regex("[0-9]|[0-9].[0-9]")

    private val resultLauncher: ActivityResultLauncher<Intent> =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val data: Intent? = result.data
                }
            }

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = binding<ActivitySelectCarBinding>(R.layout.activity_select_car).value.apply {
            lifecycleOwner = this@SelectCarActivity
            viewModel = this@SelectCarActivity.viewModel.apply {
                liveDataMark?.observe(this@SelectCarActivity) {
                    objCarMark = it as CarMark
                    executePendingBindings()
                }
                liveDataModel?.observe(this@SelectCarActivity) {
                    objCarModel = it as CarModel
                }
                liveDataGeneration?.observe(this@SelectCarActivity) {
                    objCarGeneration = it as CarGeneration
                }
                selectMarkCar.setOnClickListener {
                    openCarParameterList(TypeOptionCars.MARK, null)
                }
                selectModelCar.setOnClickListener {
                    openCarParameterList(TypeOptionCars.MODEL, liveDataMark?.value?.id)
                }
                selectGenerationCar.setOnClickListener {
                    openCarParameterList(TypeOptionCars.GENERATION, null)
                }
            }


        }
    }

    fun openCarParameterList(typeOptionCars: TypeOptionCars, id: Int?) {
        val intent = Intent(this, CarParameterListActivity::class.java)
        intent.putExtra("typeOptions", typeOptionCars)
        intent.putExtra("id", id)
        resultLauncher.launch(intent)
    }

    fun showToast(message: String) {
        Toast.makeText(this@SelectCarActivity, message, Toast.LENGTH_LONG).show()
    }
}