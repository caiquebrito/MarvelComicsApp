package com.marvelcomics.brito.viewmodel.series

import androidx.lifecycle.*
import com.marvelcomics.brito.data.repository.series.SeriesRepository
import com.marvelcomics.brito.domain.repository.ISeriesRepository
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class SeriesViewModel(private val seriesRepository: ISeriesRepository) : ViewModel() {

    val characterId = MutableLiveData<String>()

    val series = characterId.switchMap { id ->
        liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {
            emit(SeriesUiState.Loading)
            try {
                emit(SeriesUiState.Success(seriesRepository.getSeries(id.toInt())))
            } catch (exception: Exception) {
                emit(SeriesUiState.Error(exception))
            }
        }
    }
}