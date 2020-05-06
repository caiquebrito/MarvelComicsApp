package com.marvelcomics.brito.viewmodel.comic

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.marvelcomics.brito.data.datasource.remote.response.ComicResponse
import com.marvelcomics.brito.data.handler.ResourceModel
import com.marvelcomics.brito.data.repository.comics.ComicRepository
import kotlinx.coroutines.Dispatchers

class ComicViewModel(private val comicRepository: ComicRepository) : ViewModel() {

    private val characterId = MutableLiveData<Int>()

    fun getComics(characterId: Int) {
        this.characterId.value = characterId
    }

    val comic: LiveData<ResourceModel<ComicResponse>> = liveData(Dispatchers.IO) {
        emit(ResourceModel.loading(null))
        emit(comicRepository.comics(characterId.value!!))
    }
}