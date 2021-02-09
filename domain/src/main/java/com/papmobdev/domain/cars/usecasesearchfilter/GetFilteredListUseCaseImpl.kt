package com.papmobdev.domain.cars.usecasesearchfilter

import android.util.Log
import com.papmobdev.domain.cars.models.CarGeneration
import com.papmobdev.domain.cars.models.CarMark
import com.papmobdev.domain.cars.models.CarModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.*

@ExperimentalCoroutinesApi
class GetFilteredListUseCaseImpl : GetFilteredListUseCase {
    override fun execute(query: String, oldList: MutableList<*>): Flow<Result<MutableList<*>>> =
        flow {
            val queryCopy = query.toLowerCase(Locale.getDefault())
            if (queryCopy.isEmpty()) {
                emit(Result.success(oldList))
            } else if(oldList.isNotEmpty()) {
                val newList: MutableList<*>
                when (oldList[0]) {
                    is CarMark -> {
                        newList = mutableListOf<CarMark>()
                        for (item in oldList) {
                            if ((item as CarMark).name?.toLowerCase(Locale.getDefault())
                                    ?.contains(queryCopy) == true
                            ) {
                                newList.add(item)
                            }
                        }
                        emit(Result.success(newList))
                    }
                    is CarModel -> {
                        newList = mutableListOf<CarModel>()
                        for (item in oldList) {
                            if ((item as CarModel).name?.toLowerCase(Locale.getDefault())
                                    ?.contains(queryCopy) == true
                            ) {
                                newList.add(item)
                            }
                        }
                        emit(Result.success(newList))
                    }
                    is CarGeneration -> {
                        newList = mutableListOf<CarGeneration>()
                        for (item in oldList) {
                            if ((item as CarGeneration).name?.toLowerCase(Locale.getDefault())
                                    ?.contains(queryCopy) == true
                            ) {
                                newList.add(item)
                            }
                        }
                        emit(Result.success(newList))
                    }
                }

            }
            else{
                Log.e("NullList", "NullList")
            }
        }
}