package com.marvelcomics.brito.data.local

import com.marvelcomics.brito.domain.models.CharacterDomain
import com.marvelcomics.brito.domain.models.ResultWrapper

interface MarvelLocalDataSource {
    suspend fun getLastCharacter(): ResultWrapper<CharacterDomain>
    suspend fun setLastCharacter(character: CharacterDomain)
}
