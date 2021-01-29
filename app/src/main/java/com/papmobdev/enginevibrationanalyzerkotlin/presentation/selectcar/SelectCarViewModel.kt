package com.papmobdev.enginevibrationanalyzerkotlin.presentation.selectcar

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.papmobdev.enginevibrationanalyzerkotlin.data.entities.CarGenerationEntity
import com.papmobdev.enginevibrationanalyzerkotlin.data.entities.CarMarkEntity
import com.papmobdev.enginevibrationanalyzerkotlin.data.entities.CarModelEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class SelectCarViewModel() : ViewModel() {

    var liveDataMark: MutableLiveData<CarMarkEntity>? = MutableLiveData()
    var liveDataModel: MutableLiveData<CarModelEntity>? = MutableLiveData()
    var liveDataGeneration: MutableLiveData<CarGenerationEntity>? = MutableLiveData()
}