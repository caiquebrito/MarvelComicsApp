package com.marvelcomics.brito.viewmodel.character

import androidx.lifecycle.*
import com.marvelcomics.brito.data.repository.characters.CharacterRepository
import com.marvelcomics.brito.domain.repository.ICharacterRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class CharacterViewModel(
    private val iCharacterRepository: ICharacterRepository,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    var characterName = MutableLiveData<String>()

    val character = characterName.switchMap { name ->
        liveData(dispatcher) {
            emit(CharacterUiState.Loading)
            try {
                emit(
                    CharacterUiState.Success(iCharacterRepository.getCharacters(name))
                )
            } catch (exception: Exception) {
                emit(CharacterUiState.Error(exception))
            }
        }
    }
}