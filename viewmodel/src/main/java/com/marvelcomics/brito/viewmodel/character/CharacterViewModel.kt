package com.marvelcomics.brito.viewmodel.character

import androidx.lifecycle.*
import com.marvelcomics.brito.data.repository.characters.CharacterRepository
import kotlinx.coroutines.Dispatchers

class CharacterViewModel(private val characterRepository: CharacterRepository) : ViewModel() {

    var characterName = MutableLiveData<String>()

    val character = characterName.switchMap { name ->
        liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {
            emit(CharacterUiState.Loading)
            try {
                emit(
                    CharacterUiState.Success(characterRepository.getCharacters(name))
                )
            } catch (exception: Exception) {
                emit(CharacterUiState.Error(exception))
            }
        }
    }
}