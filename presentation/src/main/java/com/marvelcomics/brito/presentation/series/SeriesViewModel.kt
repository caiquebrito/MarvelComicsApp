package com.marvelcomics.brito.presentation.series

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marvelcomics.brito.domain.exception.NetworkException
import com.marvelcomics.brito.domain.usecase.SeriesUseCase
import com.marvelcomics.brito.presentation.GlobalUiState
import com.marvelcomics.brito.presentation.SeriesUiState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SeriesViewModel(
    private val seriesUseCase: SeriesUseCase,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private var _seriesUiState =
        MutableStateFlow<Any>(GlobalUiState.Empty)
    var seriesUiState: StateFlow<Any> = _seriesUiState

    fun loadSeries(id: Int) =
        viewModelScope.launch(dispatcher) {
            _seriesUiState.value = GlobalUiState.Loading
            seriesUseCase.getSeries(id).catch {
                if (it is NetworkException) {
                    _seriesUiState.value = GlobalUiState.NetworkError
                } else {
                    _seriesUiState.value = SeriesUiState.Error(it)
                }
            }.collect {
                _seriesUiState.value = SeriesUiState.Success(it)
            }
        }
}
