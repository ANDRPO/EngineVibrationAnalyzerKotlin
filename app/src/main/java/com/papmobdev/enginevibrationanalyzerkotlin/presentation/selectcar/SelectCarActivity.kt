package com.papmobdev.enginevibrationanalyzerkotlin.presentation.selectcar

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.papmobdev.domain.cars.CodeOptionsCar
import com.papmobdev.enginevibrationanalyzerkotlin.R
import com.papmobdev.enginevibrationanalyzerkotlin.databinding.ActivitySelectCarBinding
import com.papmobdev.enginevibrationanalyzerkotlin.presentation.base.BaseActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
class SelectCarActivity : BaseActivity() {

    companion object {
        private const val KEY_TYPE_CAR_OPTION = "key_type_car_option"
    }

    private val viewModel: SelectCarViewModel by viewModel()
    private lateinit var binding: ActivitySelectCarBinding

    private val listFuel = listOf("Дизель", "Бензин")

    private val resultLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data
                if (data != null) {
                    viewModel.notifySelection(data.getSerializableExtra(KEY_TYPE_CAR_OPTION) as CodeOptionsCar)
                }
            }
        }

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = binding<ActivitySelectCarBinding>(R.layout.activity_select_car).value.apply {
            lifecycleOwner = this@SelectCarActivity
            viewModel = this@SelectCarActivity.viewModel
        }
        initObservers()
        initSpinner()
        initClickListeners()
        viewModel.fetchData()
    }

    private fun initSpinner() {
        val adapter = ArrayAdapter(
            this@SelectCarActivity,
            R.layout.spinner_item,
            listFuel
        )
        binding.spinnerDieselOrPetrol.adapter = adapter
        binding.spinnerDieselOrPetrol.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                viewModel.selectOptionFuel = listFuel[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun initObservers() {
        viewModel.apply {
            liveDataConfiguration.observe(this@SelectCarActivity, {
                binding.configuration = it
                if (it.fkCarMark != null) {
                    checkNextOptionsListIsNotNull(CodeOptionsCar.MARK)
                } else if (it.fkCarModel != null) {
                    checkNextOptionsListIsNotNull(CodeOptionsCar.GENERATION)
                }
            })
        }
    }

    private fun initClickListeners() {
        binding.selectMarkCar.setOnClickListener {
            openCarParameterList(CodeOptionsCar.MARK)
        }
        binding.selectModelCar.setOnClickListener {
            if (modelCheckSelection()) {
                openCarParameterList(CodeOptionsCar.MODEL)
            }
        }
        binding.selectGenerationCar.setOnClickListener {
            if (generationCheckSelection()) {
                openCarParameterList(CodeOptionsCar.GENERATION)
            }
        }
    }

    private fun modelCheckSelection(): Boolean {
        when {
            binding.configuration?.fkCarMark == null
            -> showToastNotLastSelect("Не выбрана марка")
            !viewModel.nextModelIsNotNull -> showToastNotLastSelect("Список моделей для данной марки отсутствует")
            else -> return true
        }
        return false
    }

    private fun generationCheckSelection(): Boolean {
        when {
            binding.configuration?.fkCarModel == null -> showToastNotLastSelect("Не выбрана модель")
            !viewModel.nextGenerationIsNotNull -> showToastNotLastSelect("Список поколений для данной модели отсутствует")
            else -> return true
        }
        return false
    }

    private fun openCarParameterList(typeOptionCars: CodeOptionsCar) {
        val intent = Intent(this, CarParameterListActivity::class.java)
        intent.putExtra(KEY_TYPE_CAR_OPTION, typeOptionCars)
        resultLauncher.launch(intent)
    }

    private fun showToastNotLastSelect(message: String) {
        Toast.makeText(this@SelectCarActivity, message, Toast.LENGTH_SHORT).show()
    }
}