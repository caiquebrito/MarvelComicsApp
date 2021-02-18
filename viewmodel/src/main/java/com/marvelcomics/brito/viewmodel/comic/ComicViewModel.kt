package com.marvelcomics.brito.viewmodel.comic

import androidx.lifecycle.*
import com.marvelcomics.brito.data.repository.comics.ComicRepository
import com.marvelcomics.brito.domain.entity.ComicEntity
import com.marvelcomics.brito.domain.repository.IComicRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ComicViewModel(private val comicRepository: IComicRepository) : ViewModel() {

    private var _characterUiState = MutableStateFlow<ComicUiState>(ComicUiState.Empty)
    val characterUiState: StateFlow<ComicUiState> = _characterUiState

    fun loadComics(id: Int) =
        viewModelScope.launch(context = viewModelScope.coroutineContext + Dispatchers.IO) {
            _characterUiState.value = ComicUiState.Loading
            try {
                _characterUiState.value = ComicUiState.Success(comicRepository.getComics(id))
            } catch (exception: Exception) {
                _characterUiState.value = ComicUiState.Error(exception)
            }
        }
}