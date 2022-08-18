package com.marvelcomics.brito.data.remote

import com.marvelcomics.brito.domain.models.CharacterDomain
import com.marvelcomics.brito.domain.models.ComicDomain
import com.marvelcomics.brito.domain.models.SeriesDomain

interface HandlerSample {
    suspend fun firstMethod(name: String): List<CharacterDomain>
    suspend fun secondMethod(characterId: Int): List<ComicDomain>
    suspend fun thirdMethod(characterId: Int): List<SeriesDomain>
    suspend fun fourthMethod(characterId: Int): List<SeriesDomain>
}
