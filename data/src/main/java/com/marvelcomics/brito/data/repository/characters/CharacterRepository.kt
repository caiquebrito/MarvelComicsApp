package com.marvelcomics.brito.data.repository.characters

import com.marvelcomics.brito.data.datasource.remote.mapper.CharacterMapper
import com.marvelcomics.brito.data.repository.BaseRepositoryImpl
import com.marvelcomics.brito.data.webservice.MarvelWebService
import com.marvelcomics.brito.domain.entity.CharacterEntity
import com.marvelcomics.brito.domain.repository.ICharacterRepository
import com.marvelcomics.brito.infrastructure.exception.MarvelMapperException

class CharacterRepository(
    private val webService: MarvelWebService,
    private val characterMapper: CharacterMapper
) : ICharacterRepository, BaseRepositoryImpl() {
    override suspend fun getCharacters(name: String): CharacterEntity {
        val listEntity = characterMapper.transform(webService.characters(name))
        return listEntity?.let {
            it[0]
        } ?: let {
            throw MarvelMapperException("Error mapping character", null)
        }
    }
}