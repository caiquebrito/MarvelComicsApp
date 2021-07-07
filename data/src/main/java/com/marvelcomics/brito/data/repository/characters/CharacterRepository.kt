package com.marvelcomics.brito.data.repository.characters

import com.marvelcomics.brito.data.datasource.remote.mapper.CharacterMapper
import com.marvelcomics.brito.data.webservice.MarvelWebService
import com.marvelcomics.brito.domain.entity.CharacterEntity
import com.marvelcomics.brito.domain.repository.ICharacterRepository
import com.marvelcomics.brito.infrastructure.exception.MarvelMapperException

class CharacterRepository(
    private val webService: MarvelWebService,
    private val characterMapper: CharacterMapper
) : ICharacterRepository {
    override suspend fun getCharacters(name: String): List<CharacterEntity> {
        return characterMapper.transform(webService.characters(name))
            ?: run { throw MarvelMapperException("Error mapping character", null) }
    }
}

