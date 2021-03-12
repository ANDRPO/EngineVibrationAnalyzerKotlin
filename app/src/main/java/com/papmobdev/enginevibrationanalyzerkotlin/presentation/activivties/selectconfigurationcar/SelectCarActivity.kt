package com.papmobdev.enginevibrationanalyzerkotlin.presentation.activivties.selectconfigurationcar

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.papmobdev.domain.cars.CodeParametersCar
import com.papmobdev.domain.cars.models.TypesFuel
import com.papmobdev.domain.cars.models.TypesSource
import com.papmobdev.enginevibrationanalyzerkotlin.R
import com.papmobdev.enginevibrationanalyzerkotlin.databinding.ActivitySelectCarBinding
import com.papmobdev.enginevibrationanalyzerkotlin.presentation.activivties.manual.ManualActivity
import com.papmobdev.enginevibrationanalyzerkotlin.presentation.base.BaseActivity
import kotlinx.android.synthetic.main.activity_select_car.view.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.tinkoff.decoro.MaskImpl
import ru.tinkoff.decoro.parser.UnderscoreDigitSlotsParser
import ru.tinkoff.decoro.watchers.MaskFormatWatcher


@ExperimentalCoroutinesApi
class SelectCarActivity : BaseActivity() {

    private val DEFAULT_TYPE_SOURCE = TypesSource.FRONT_PANEL
    private val DEFAULT_TYPE_FUEL = TypesFuel.PETROL

    private val viewModel: SelectCarViewModel by viewModel()
    private lateinit var binding: ActivitySelectCarBinding

    private val maskEngineVolume = Regex("[0-9][.]")

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
        initOnTextChanged()
    }

    private fun initOnTextChanged() {
        binding.editTextEngineVolume.addTextChangedListener {
            binding.configuration?.engineVolume =
                it?.let { return@let it.toString().toDoubleOrNull() }
        }
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
                    configuration = it.getOrNull()
                }

                if (it.getOrNull()?.fkCarModel == null) {
                    checkNextOptionsListIsNotNull(CodeParametersCar.MARK)
                } else if (it.getOrNull()?.fkCarGeneration == null) {
                    checkNextOptionsListIsNotNull(CodeParametersCar.MODEL)
                }
            })

            liveDataTypesFuelList.observe(this@SelectCarActivity, { result ->
                result.onSuccess { list ->
                    val adapter = ArrayAdapter(
                        this@SelectCarActivity,
                        R.layout.spinner_item,
                        list
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
                                        list[position].idFuel ?: DEFAULT_TYPE_FUEL.idType
                                    )
                                }

                                override fun onNothingSelected(parent: AdapterView<*>?) {}
                            }
                    }

                }
                result.onFailure {
                    showToastMissingFollowingListParameters("Не удалось загрузить список типов топлива")
                }
            }
            )

            liveDataTypeSourceList.observe(this@SelectCarActivity, { result ->
                result.onSuccess { list ->
                    val adapter = ArrayAdapter(
                        this@SelectCarActivity,
                        R.layout.spinner_item,
                        list
                    )

                    binding.spinnerSourceVibration.apply {
                        this.adapter = adapter
                        onItemSelectedListener =
                            object : OnItemSelectedListener {
                                override fun onItemSelected(
                                    parent: AdapterView<*>?,
                                    view: View?,
                                    position: Int,
                                    id: Long
                                ) {
                                    viewModel.updateVibrationSourceConfiguration(
                                        list[position].ordinal ?: 1
                                    )
                                }

                                override fun onNothingSelected(parent: AdapterView<*>?) {}
                            }
                    }
                }
                result.onFailure {
                    showToastMissingFollowingListParameters("Не удалось загрузить список источников вибрации")
                }
            })

            showErrorMessage.observe(this@SelectCarActivity, {
                showToastMissingFollowingListParameters(it)
            })
        }
    }

    private fun initInputFilterFuelField() {
        val slots = UnderscoreDigitSlotsParser().parseSlots("_._")
        val formatWatcher = MaskFormatWatcher(MaskImpl.createNonTerminated(slots))
        formatWatcher.installOn(binding.editTextEngineVolume)
    }

    private fun initClickListeners() {
        binding.selectMarkCar.setOnClickListener {
            openCarParameterList(CodeParametersCar.MARK)
        }
        binding.selectModelCar.setOnClickListener {
            if (modelCheckSelection()) {
                openCarParameterList(CodeParametersCar.MODEL)
            }
        }
        binding.selectGenerationCar.setOnClickListener {
            if (generationCheckSelection()) {
                openCarParameterList(CodeParametersCar.GENERATION)
            }
        }
        binding.buttonNext.setOnClickListener {
            ManualActivity.start(this)
        }
    }


    private fun modelCheckSelection(): Boolean {
        when {
            binding.configuration?.fkCarMark == null
            -> showToastMissingFollowingListParameters("Не выбрана марка")
            !viewModel.nextModelIsNotNull -> showToastMissingFollowingListParameters("Список моделей для данной марки отсутствует")
            else -> return true
        }
        return false
    }

    private fun generationCheckSelection(): Boolean {
        when {
            binding.configuration?.fkCarModel == null -> showToastMissingFollowingListParameters("Не выбрана модель")
            !viewModel.nextGenerationIsNotNull -> showToastMissingFollowingListParameters("Список поколений для данной модели отсутствует")
            else -> return true
        }
        return false
    }

    override fun onPause() {
        super.onPause()
        binding.root.requestFocus()
    }

    private fun openCarParameterList(typeOptionCars: CodeParametersCar) {
        CarParameterListActivity.start(this, typeOptionCars)
    }

    private fun showToastMissingFollowingListParameters(message: String) {
        Toast.makeText(this@SelectCarActivity, message, Toast.LENGTH_SHORT).show()
    }
}