package com.marvelcomics.brito.presentation.details

import androidx.lifecycle.viewModelScope
import com.marvelcomics.brito.domain.usecase.LoadComicsUseCase
import com.marvelcomics.brito.domain.usecase.LoadSeriesUseCase
import com.marvelcomics.brito.domain.usecase.onFailure
import com.marvelcomics.brito.domain.usecase.onSuccess
import com.marvelcomics.brito.entity.ComicEntity
import com.marvelcomics.brito.presentation.flow.ViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class DetailCharacterViewModel(
    private val loadComicsUseCase: LoadComicsUseCase,
    private val loadSeriesUseCase: LoadSeriesUseCase,
    private val dispatcher: CoroutineDispatcher,
) : ViewModel<DetailCharacterUiState, DetailCharacterUiEffect>(DetailCharacterUiState(isIdle = true)) {

    fun getComicsAndSeriesById(id: Int) {
        viewModelScope.launch {
            launch { getComicsById(id) }
            launch { getSeriesById(id) }
        }
    }

    private suspend fun getComicsById(id: Int) {
        setState { state ->
            state.copy(isIdle = false, showComicsLoading = true)
        }
        var listComics: List<ComicEntity>? = null
        loadComicsUseCase.invoke(id)
            .onSuccess {
                listComics = it
            }.onFailure {
                sendEffect(DetailCharacterUiEffect.ShowComicsError)
            }
        setState { state ->
            state.copy(showComicsLoading = false, listComics = listComics)
        }
    }

    private suspend fun getSeriesById(id: Int) {
        loadSeriesUseCase.invoke(id)
            .onStart {
                setState { state ->
                    state.copy(isIdle = false, showSeriesLoading = true)
                }
            }
            .onCompletion {
                setState { state ->
                    state.copy(showSeriesLoading = false)
                }
            }
            .flowOn(dispatcher)
            .catch {
                sendEffect(DetailCharacterUiEffect.ShowSeriesError)
            }
            .collect {
                setState { state ->
                    state.copy(listSeries = it)
                }
            }
    }
}
