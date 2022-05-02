package com.marvelcomics.brito.presentation.character

sealed class CharacterInteraction {
    class SearchCharacter(val name: String) : CharacterInteraction()
}