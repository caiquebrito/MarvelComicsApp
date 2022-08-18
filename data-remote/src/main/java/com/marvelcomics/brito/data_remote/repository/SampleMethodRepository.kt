package com.marvelcomics.brito.data_remote.repository

import com.marvelcomics.brito.data.remote.HandlerSample
import com.marvelcomics.brito.data_remote.ErrorBodyException
import com.marvelcomics.brito.data_remote.api.MarvelAPI
import com.marvelcomics.brito.data_remote.datasource.mapper.CharacterMapper
import com.marvelcomics.brito.data_remote.datasource.mapper.ComicMapper
import com.marvelcomics.brito.data_remote.datasource.mapper.SeriesMapper
import com.marvelcomics.brito.data_remote.getBodyOrThrow
import com.marvelcomics.brito.data_remote.handleApi
import com.marvelcomics.brito.data_remote.handledByCommon
import com.marvelcomics.brito.data_remote.treatByCode
import com.marvelcomics.brito.domain.exception.UnknownException
import com.marvelcomics.brito.domain.models.CharacterDomain
import com.marvelcomics.brito.domain.models.ComicDomain
import com.marvelcomics.brito.domain.models.SeriesDomain

class SampleMethodRepository(
    private val api: MarvelAPI,
    private val characterMapper: CharacterMapper,
    private val comicMapper: ComicMapper,
    private val seriesMapper: SeriesMapper
) : HandlerSample {

    override suspend fun firstMethod(name: String): List<CharacterDomain> {
        return handleApi {
            characterMapper.transform(api.characters(name).getBodyOrThrow())
        }
    }

    override suspend fun secondMethod(characterId: Int): List<ComicDomain> {
        return handleApi(
            callHandling = {
                comicMapper.transform(api.comics(characterId))
            },
            errorHandling = { exception ->
                with(exception) {
                    throw when (this) {
                        is ErrorBodyException -> {
                            throw when (mappedCode) {
                                "SMB-1010" -> Exception("invalid code")
                                else -> UnknownException(message)
                            }
                        }
                        else -> this
                    }
                }
            }
        )
    }

    override suspend fun thirdMethod(characterId: Int): List<SeriesDomain> {
        return handleApi(
            callHandling = {
                seriesMapper.transform(api.series(characterId).getBodyOrThrow())
            },
            errorHandling = { exception ->
                val mappedCodes = hashMapOf(
                    Pair("SMB-1010", Exception("invalid password")),
                    Pair("SMB-0001", Exception("locked password")),
                    Pair("SMB-8594", Exception("insuficient balance"))
                )
                exception.treatByCode(mappedCodes)
            })
    }

    override suspend fun fourthMethod(characterId: Int): List<SeriesDomain> {
        return handleApi(
            callHandling = {
                seriesMapper.transform(api.series(characterId).getBodyOrThrow())
            },
            errorHandling = { exception ->
                exception.handledByCommon()
            })
    }
}