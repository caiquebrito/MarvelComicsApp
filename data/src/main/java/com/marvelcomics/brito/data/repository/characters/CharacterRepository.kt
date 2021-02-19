package com.marvelcomics.brito.data.repository.characters

import com.marvelcomics.brito.data.datasource.remote.mapper.CharacterMapper
import com.marvelcomics.brito.data.okhttp.safeApiCall
import com.marvelcomics.brito.data.webservice.MarvelWebService
import com.marvelcomics.brito.domain.ResultWrapper
import com.marvelcomics.brito.domain.entity.CharacterEntity
import com.marvelcomics.brito.domain.repository.ICharacterRepository
import com.marvelcomics.brito.infrastructure.exception.MarvelMapperException

class CharacterRepository(
    private val webService: MarvelWebService,
    private val characterMapper: CharacterMapper
) : ICharacterRepository {
    override suspend fun getCharacters(name: String): ResultWrapper<CharacterEntity> {
        return safeApiCall {
            characterMapper.transform(webService.characters(name))?.let {
                it[0]
            } ?: run { throw MarvelMapperException("Error mapping character", null) }
        }
    }
}