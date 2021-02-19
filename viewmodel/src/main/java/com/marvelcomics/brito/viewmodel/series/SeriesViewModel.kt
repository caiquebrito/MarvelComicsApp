package com.marvelcomics.brito.viewmodel.series

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marvelcomics.brito.domain.ResultWrapper
import com.marvelcomics.brito.domain.entity.SeriesEntity
import com.marvelcomics.brito.domain.repository.ISeriesRepository
import com.marvelcomics.brito.viewmodel.BaseUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SeriesViewModel(private val seriesRepository: ISeriesRepository) : ViewModel() {

    private var _seriesUiState = MutableStateFlow<BaseUiState<List<SeriesEntity>>>(BaseUiState.Empty)
    var seriesUiState: StateFlow<BaseUiState<List<SeriesEntity>>> = _seriesUiState

    fun loadSeries(id: Int) =
        viewModelScope.launch(context = viewModelScope.coroutineContext + Dispatchers.IO) {
            _seriesUiState.value = BaseUiState.Loading
            when (val response = seriesRepository.getSeries(id)) {
                is ResultWrapper.Success -> {
                    _seriesUiState.value = BaseUiState.Success(response.value)
                }
                is ResultWrapper.Failure -> {
                    _seriesUiState.value = BaseUiState.Error(response.error)
                }
                is ResultWrapper.NetworkError -> {
                    _seriesUiState.value = BaseUiState.NetworkError
                }
            }
        }
}
