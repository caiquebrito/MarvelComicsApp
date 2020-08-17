package com.marvelcomics.brito.viewmodel.character

import androidx.lifecycle.*
import com.marvelcomics.brito.data.entity.CharacterEntity
import com.marvelcomics.brito.data.handler.ResourceModel
import com.marvelcomics.brito.data.repository.characters.CharacterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CharacterViewModel(private val characterRepository: CharacterRepository) : ViewModel() {

    var characterName = MutableLiveData<String>()

    val character = characterName.switchMap { name ->
        liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {
            emit(ResourceModel.loading())
            val result = characterRepository.getCharacters(name)
            emit(result)
        }
    }
}