package com.marvelcomics.brito.viewmodel.series

import androidx.lifecycle.*
import com.marvelcomics.brito.data.datasource.remote.response.SeriesResponse
import com.marvelcomics.brito.data.handler.ResourceModel
import com.marvelcomics.brito.data.repository.series.SeriesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope

class SeriesViewModel(private val seriesRepository: SeriesRepository) : ViewModel() {

    val characterId = MutableLiveData<String>()

    val series = characterId.switchMap { id ->
        liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {
            emit(ResourceModel.loading())
            emit(seriesRepository.series(id.toInt()))
        }
    }
}