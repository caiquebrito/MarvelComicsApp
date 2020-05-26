package com.marvelcomics.brito.viewmodel.character

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.marvelcomics.brito.data.entity.CharacterEntity
import com.marvelcomics.brito.data.handler.ResourceModel
import com.marvelcomics.brito.data.repository.characters.CharacterRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CharacterViewModel(private val characterRepository: CharacterRepository) : ViewModel() {

    private val character = MutableLiveData<ResourceModel<CharacterEntity>>()

//    private val characterNameLiveData = MutableLiveData<String>()

    fun getCharacter(name: String) : LiveData<ResourceModel<CharacterEntity>> {
        character.postValue(ResourceModel.loading(null))
        GlobalScope.launch {
            val result = characterRepository.getCharacters(name)
            character.postValue(result)
        }
        return character
    }

//    val characterLiveData = liveData<ResourceModel<RemoteMarvelData<CharacterResponse>>> {
//        emit(ResourceModel.loading(null))
//        characterNameLiveData.value?.let {
//            emit(characterRepository.getCharacters(it))
//        }
//    }
}