package com.marvelcomics.brito.viewmodel.character

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marvelcomics.brito.domain.ResultWrapper
import com.marvelcomics.brito.domain.entity.CharacterEntity
import com.marvelcomics.brito.domain.repository.ICharacterRepository
import com.marvelcomics.brito.viewmodel.BaseUiState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CharacterViewModel(
    private val iCharacterRepository: ICharacterRepository,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private var _characterUiState = MutableStateFlow<BaseUiState<CharacterEntity>>(BaseUiState.Empty)
    var characterUiState: StateFlow<BaseUiState<CharacterEntity>> = _characterUiState

    fun loadCharacter(name: String) = viewModelScope.launch(dispatcher) {
        _characterUiState.value = BaseUiState.Loading
        when (val response = iCharacterRepository.getCharacters(name)) {
            is ResultWrapper.Success -> {
                _characterUiState.value = BaseUiState.Success(response.value)
            }
            is ResultWrapper.Failure -> {
                _characterUiState.value = BaseUiState.Error(response.error)
            }
            is ResultWrapper.NetworkError -> {
                _characterUiState.value = BaseUiState.NetworkError
            }
        }
    }
}
