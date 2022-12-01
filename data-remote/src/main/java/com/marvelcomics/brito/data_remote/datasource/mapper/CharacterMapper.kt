package com.marvelcomics.brito.data_remote.datasource.mapper

import com.marvelcomics.brito.data_remote.datasource.response.CharacterResponse
import com.marvelcomics.brito.data_remote.datasource.response.model.RemoteMarvelContainerResponse
import com.marvelcomics.brito.data_remote.exception.MarvelApiException
import com.marvelcomics.brito.entity.CharacterEntity

class CharacterMapper(private val thumbnailMapper: ThumbnailMapper) {

    @Throws(MarvelApiException::class)
    fun transform(remoteMarvelContainerResponse: RemoteMarvelContainerResponse<CharacterResponse>): List<CharacterEntity> {
        try {
            val characterDomainList: MutableList<CharacterEntity> = ArrayList()
            remoteMarvelContainerResponse.remoteMarvelDataResponse?.results?.let {
                for (characterResponse in it) {
                    val characterEntity = CharacterEntity(
                        characterResponse.id,
                        characterResponse.name,
                        characterResponse.description,
                        thumbnailMapper.transform(characterResponse.thumbnailResponse)
                    )
                    characterDomainList.add(characterEntity)
                }
                return characterDomainList
            } ?: let {
                throw MarvelApiException("Result from server return nulls")
            }
        } catch (e: NullPointerException) {
            throw MarvelApiException("Result from server return nulls", e)
        }
    }
}
