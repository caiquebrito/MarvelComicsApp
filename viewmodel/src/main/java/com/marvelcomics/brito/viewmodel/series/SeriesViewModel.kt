package com.marvelcomics.brito.viewmodel.series

import androidx.lifecycle.*
import com.marvelcomics.brito.data.repository.series.SeriesRepository
import com.marvelcomics.brito.domain.repository.ISeriesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.Exception

class SeriesViewModel(private val seriesRepository: ISeriesRepository) : ViewModel() {

    private var _seriesUiState = MutableStateFlow<SeriesUiState>(SeriesUiState.Empty)
    var seriesUiState: StateFlow<SeriesUiState> = _seriesUiState

    fun loadSeries(id: Int) =
        viewModelScope.launch(context = viewModelScope.coroutineContext + Dispatchers.IO) {
            _seriesUiState.value = SeriesUiState.Loading
            try {
                _seriesUiState.value = SeriesUiState.Success(seriesRepository.getSeries(id))
            } catch (exception: Exception) {
                _seriesUiState.value = SeriesUiState.Error(exception)
            }
        }
}