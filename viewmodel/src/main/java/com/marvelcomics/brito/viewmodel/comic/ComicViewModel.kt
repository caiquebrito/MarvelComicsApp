package com.marvelcomics.brito.viewmodel.comic

import androidx.lifecycle.*
import com.marvelcomics.brito.data.datasource.remote.response.ComicResponse
import com.marvelcomics.brito.data.handler.ResourceModel
import com.marvelcomics.brito.data.repository.comics.ComicRepository
import kotlinx.coroutines.Dispatchers

class ComicViewModel(private val comicRepository: ComicRepository) : ViewModel() {

    var characterId = MutableLiveData<String>()

    val comics = characterId.switchMap { id ->
        liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {
            emit(ResourceModel.loading())
            emit(comicRepository.comics(id.toInt()))
        }
    }
}