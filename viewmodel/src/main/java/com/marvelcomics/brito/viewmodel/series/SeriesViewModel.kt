package com.marvelcomics.brito.viewmodel.series

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.marvelcomics.brito.data.datasource.remote.response.SeriesResponse
import com.marvelcomics.brito.data.handler.ResourceModel
import com.marvelcomics.brito.data.repository.series.SeriesRepository
import kotlinx.coroutines.Dispatchers

class SeriesViewModel(private val seriesRepository: SeriesRepository) : ViewModel() {

    private val characterId = MutableLiveData<Int>()

    fun getSeries(characterId: Int) {
        this.characterId.value = characterId
    }

    val series: LiveData<ResourceModel<SeriesResponse>> = liveData(Dispatchers.IO) {
        emit(ResourceModel.loading(null))
        emit(seriesRepository.series(characterId.value!!))
    }
}