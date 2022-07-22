package com.marvelcomics.brito.data.local

import com.marvelcomics.brito.domain.models.CharacterDomain

interface MarvelLocalDataSource {
    suspend fun getLastCharacter(): CharacterDomain
    suspend fun setLastCharacter(character: CharacterDomain)
}
