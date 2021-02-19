package com.marvelcomics.brito.viewmodel.comic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marvelcomics.brito.domain.ResultWrapper
import com.marvelcomics.brito.domain.entity.ComicEntity
import com.marvelcomics.brito.domain.repository.IComicRepository
import com.marvelcomics.brito.viewmodel.BaseUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ComicViewModel(private val comicRepository: IComicRepository) : ViewModel() {

    private var _comicUiState =
        MutableStateFlow<BaseUiState<List<ComicEntity>>>(BaseUiState.Empty)
    val comicUiState: StateFlow<BaseUiState<List<ComicEntity>>> = _comicUiState

    fun loadComics(id: Int) =
        viewModelScope.launch(context = viewModelScope.coroutineContext + Dispatchers.IO) {
            _comicUiState.value = BaseUiState.Loading
            when (val response = comicRepository.getComics(id)) {
                is ResultWrapper.Success -> {
                    _comicUiState.value = BaseUiState.Success(response.value)
                }
                is ResultWrapper.Failure -> {
                    _comicUiState.value = BaseUiState.Error(response.error)
                }
                is ResultWrapper.NetworkError -> {
                    _comicUiState.value = BaseUiState.NetworkError
                }
            }
        }
}
