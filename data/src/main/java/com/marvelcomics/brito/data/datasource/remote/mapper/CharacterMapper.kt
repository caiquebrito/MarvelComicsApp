package com.marvelcomics.brito.data.datasource.remote.mapper

import com.marvelcomics.brito.data.datasource.remote.response.CharacterResponse
import com.marvelcomics.brito.data.datasource.remote.response.model.RemoteMarvelContainerResponse
import com.marvelcomics.brito.domain.models.CharacterDomain
import com.marvelcomics.brito.infrastructure.exception.MarvelApiException

class CharacterMapper(private val thumbnailMapper: ThumbnailMapper) {

    @Throws(MarvelApiException::class)
    fun transform(remoteMarvelContainerResponse: RemoteMarvelContainerResponse<CharacterResponse>): List<CharacterDomain>? {
        try {
            val characterDomainList: MutableList<CharacterDomain> = ArrayList()
            remoteMarvelContainerResponse.remoteMarvelDataResponse?.results?.let {
                for (characterResponse in it) {
                    val characterEntity = CharacterDomain(
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
