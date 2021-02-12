package com.papmobdev.enginevibrationanalyzerkotlin.presentation.selectcar

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.papmobdev.domain.cars.CodeOptionsCar
import com.papmobdev.enginevibrationanalyzerkotlin.R
import com.papmobdev.enginevibrationanalyzerkotlin.databinding.ActivitySelectCarBinding
import com.papmobdev.enginevibrationanalyzerkotlin.presentation.base.BaseActivity
import kotlinx.android.synthetic.main.activity_select_car.view.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.tinkoff.decoro.MaskImpl
import ru.tinkoff.decoro.parser.UnderscoreDigitSlotsParser
import ru.tinkoff.decoro.watchers.MaskFormatWatcher


@ExperimentalCoroutinesApi
class SelectCarActivity : BaseActivity() {

    companion object {
        private const val KEY_TYPE_CAR_OPTION = "key_type_car_option"
    }

    private val viewModel: SelectCarViewModel by viewModel()
    private lateinit var binding: ActivitySelectCarBinding

    private val maskEngineVolume = Regex("[0-9][.]")

    private val resultLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                viewModel.fetchData()
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
        initClickListeners()
        initInputFilterFuelField()
        initEditTextHasFocus()
        viewModel.fetchData()
    }

    private fun initEditTextHasFocus() {
        binding.apply {
            editTextEngineVolume.setOnFocusChangeListener { v, hasFocus ->
                if (maskEngineVolume.matches((v as EditText).text) && !hasFocus) {
                    v.append("0")
                }
                if (!hasFocus) {
                    viewModel?.updateEngineVolumeConfiguration(v.text.toString())
                }
            }

            editTextInputNote.setOnFocusChangeListener { view: View, b: Boolean ->
                (view as EditText)
                viewModel?.updateNoteConfiguration(view.text.toString())
            }
        }
    }

    private fun initObservers() {
        viewModel.apply {
            liveDataConfiguration.observe(this@SelectCarActivity, {
                binding.apply {
                    configuration = it
                }

                if (it.fkCarModel == null) {
                    checkNextOptionsListIsNotNull(CodeOptionsCar.MARK)
                } else if (it.fkCarGeneration == null) {
                    checkNextOptionsListIsNotNull(CodeOptionsCar.MODEL)
                }
            })

            liveDataTypesFuelList.observe(this@SelectCarActivity, { result ->
                result.onSuccess { list ->
                    val adapter = ArrayAdapter(
                        this@SelectCarActivity,
                        R.layout.spinner_item,
                        list.map { it.nameFuel }
                    )

                    binding.spinnerDieselOrPetrol.apply {
                        this.adapter = adapter
                        onItemSelectedListener =
                            object : OnItemSelectedListener {
                                override fun onItemSelected(
                                    parent: AdapterView<*>?,
                                    view: View?,
                                    position: Int,
                                    id: Long
                                ) {
                                    viewModel.updateTypeFuelConfiguration(
                                        list[position].idFuel ?: 1
                                    )
                                }

                                override fun onNothingSelected(parent: AdapterView<*>?) {}
                            }
                    }

                }
                result.onFailure {
                    showToastMissingFollowingListOptions("Не удалось загрузить список типов топлива")
                }
            }
            )
        }
    }

    private fun initInputFilterFuelField() {
        val slots = UnderscoreDigitSlotsParser().parseSlots("_._")
        val formatWatcher = MaskFormatWatcher(MaskImpl.createNonTerminated(slots))
        formatWatcher.installOn(binding.editTextEngineVolume)
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
            -> showToastMissingFollowingListOptions("Не выбрана марка")
            !viewModel.nextModelIsNotNull -> showToastMissingFollowingListOptions("Список моделей для данной марки отсутствует")
            else -> return true
        }
        return false
    }

    private fun generationCheckSelection(): Boolean {
        when {
            binding.configuration?.fkCarModel == null -> showToastMissingFollowingListOptions("Не выбрана модель")
            !viewModel.nextGenerationIsNotNull -> showToastMissingFollowingListOptions("Список поколений для данной модели отсутствует")
            else -> return true
        }
        return false
    }

    override fun onPause() {
        super.onPause()
        binding.root.requestFocus()
    }

    private fun openCarParameterList(typeOptionCars: CodeOptionsCar) {
        binding.root.clearFocus()
        val intent = Intent(this, CarParameterListActivity::class.java)
        intent.putExtra(KEY_TYPE_CAR_OPTION, typeOptionCars)
        resultLauncher.launch(intent)
    }

    private fun showToastMissingFollowingListOptions(message: String) {
        Toast.makeText(this@SelectCarActivity, message, Toast.LENGTH_SHORT).show()
    }
}