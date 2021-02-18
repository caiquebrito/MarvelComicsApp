package com.marvelcomics.brito.viewmodel.character

import androidx.lifecycle.*
import com.marvelcomics.brito.data.repository.characters.CharacterRepository
import com.marvelcomics.brito.domain.repository.ICharacterRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CharacterViewModel(
    private val iCharacterRepository: ICharacterRepository,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private var _characterUiState = MutableStateFlow<CharacterUiState>(CharacterUiState.Empty)
    var characterUiState: StateFlow<CharacterUiState> = _characterUiState

    fun loadCharacter(name: String) = viewModelScope.launch(dispatcher) {
        _characterUiState.value = CharacterUiState.Loading
        try {
            _characterUiState.value =
                CharacterUiState.Success(iCharacterRepository.getCharacters(name))
        } catch (exception: Exception) {
            _characterUiState.value = CharacterUiState.Error(exception)
        }
    }
}