package com.marvelcomics.brito.viewmodel.character

import com.marvelcomics.brito.domain.entity.CharacterEntity
import java.lang.Exception

sealed class CharacterUiState {
    class Success(val character: CharacterEntity) : CharacterUiState()
    object Loading : CharacterUiState()
    class Error(val exception: Exception) : CharacterUiState()
    object Empty: CharacterUiState()
}