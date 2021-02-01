package com.papmobdev.enginevibrationanalyzerkotlin.presentation.selectcar

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.papmobdev.data.entities.CarGenerationEntity
import com.papmobdev.data.entities.CarMarkEntity
import com.papmobdev.data.entities.CarModelEntity
import com.papmobdev.domain.cars.models.CarGeneration
import com.papmobdev.domain.cars.models.CarMark
import com.papmobdev.domain.cars.models.CarModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class SelectCarViewModel : ViewModel() {

    var liveDataMark: MutableLiveData<CarMark>? = MutableLiveData()
    var liveDataModel: MutableLiveData<CarModel>? = MutableLiveData()
    var liveDataGeneration: MutableLiveData<CarGeneration>? = MutableLiveData()
}