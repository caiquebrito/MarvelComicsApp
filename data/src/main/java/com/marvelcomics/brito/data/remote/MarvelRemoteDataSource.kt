package com.marvelcomics.brito.data.remote

import com.marvelcomics.brito.entity.CharacterEntity
import com.marvelcomics.brito.entity.ComicEntity
import com.marvelcomics.brito.entity.SeriesEntity

interface MarvelRemoteDataSource {
    suspend fun getCharactersByName(name: String): List<CharacterEntity>
    suspend fun getComicsById(characterId: Int): List<ComicEntity>
    suspend fun getSeriesById(characterId: Int): List<SeriesEntity>
}
