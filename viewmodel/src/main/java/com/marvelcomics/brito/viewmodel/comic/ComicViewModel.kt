package com.marvelcomics.brito.viewmodel.comic

import androidx.lifecycle.*
import com.marvelcomics.brito.data.repository.comics.ComicRepository
import kotlinx.coroutines.Dispatchers

class ComicViewModel(private val comicRepository: ComicRepository) : ViewModel() {

    var characterId = MutableLiveData<String>()

    val comics = characterId.switchMap { id ->
        liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {
            emit(ComicUiState.Loading)
            try {
                emit(ComicUiState.Success(comicRepository.comics(id.toInt())))
            } catch (exception: Exception) {
                emit(ComicUiState.Error(exception))
            }
        }
    }
}