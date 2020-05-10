package com.marvelcomics.brito.viewmodel.character

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.marvelcomics.brito.data.datasource.remote.response.CharacterResponse
import com.marvelcomics.brito.data.datasource.remote.response.model.RemoteMarvelData
import com.marvelcomics.brito.data.handler.ResourceModel
import com.marvelcomics.brito.data.repository.characters.CharacterRepository
import kotlinx.coroutines.Dispatchers

class CharacterViewModel(private val characterRepository: CharacterRepository) : ViewModel() {

    private val characterName = MutableLiveData<String>()

    fun getCharacter(name: String) {
        characterName.value = name
    }

    val character: LiveData<ResourceModel<RemoteMarvelData<CharacterResponse>>> =
        liveData(Dispatchers.IO) {
            characterName.value?.let {
                emit(characterRepository.characters(it))
            }
        }
}