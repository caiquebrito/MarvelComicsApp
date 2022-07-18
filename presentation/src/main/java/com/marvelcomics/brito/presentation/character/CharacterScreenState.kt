package com.marvelcomics.brito.presentation.character

sealed class CharacterScreenState {
    object NetworkError : CharacterScreenState()
    object Loading : CharacterScreenState()
    object Empty : CharacterScreenState()
    object Idle : CharacterScreenState()
    class Success(val `data`: Any) : CharacterScreenState()
    class Error(val exception: Throwable, val message: String? = null) : CharacterScreenState()
}
