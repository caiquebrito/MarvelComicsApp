package com.marvelcomics.brito.data_remote.repository

import com.marvelcomics.brito.data.remote.MarvelRemoteDataSource
import com.marvelcomics.brito.data_remote.api.MarvelAPI
import com.marvelcomics.brito.data_remote.datasource.mapper.CharacterMapper
import com.marvelcomics.brito.data_remote.datasource.mapper.ComicMapper
import com.marvelcomics.brito.data_remote.datasource.mapper.RemoteMapper
import com.marvelcomics.brito.data_remote.datasource.mapper.SeriesMapper
import com.marvelcomics.brito.data_remote.datasource.response.SeriesResponse
import com.marvelcomics.brito.data_remote.datasource.response.model.RemoteMarvelContainerResponse
import com.marvelcomics.brito.data_remote.exception.MarvelApiException
import com.marvelcomics.brito.data_remote.exception.MarvelMapperException
import com.marvelcomics.brito.domain.models.CharacterDomain
import com.marvelcomics.brito.domain.models.ComicDomain
import com.marvelcomics.brito.domain.models.SeriesDomain
import java.lang.Exception
import retrofit2.Response

class MarvelRemoteRepository(
    private val api: MarvelAPI,
    private val characterMapper: CharacterMapper,
    private val comicMapper: ComicMapper,
    private val seriesMapper: SeriesMapper
) : MarvelRemoteDataSource {

    override suspend fun getCharacters(name: String): List<CharacterDomain> {
        api.characters(name).apply {
            return if (this.isSuccessful) {
                body()?.let { body ->
                    characterMapper.transform(body)
                } ?: run {
                    throw MarvelApiException(
                        httpCode = this.code(),
                        marvelCode = this.code().toString(),
                        "Error getting info from Characters"
                    )
                }
            } else {
                throw MarvelApiException(
                    httpCode = this.code(),
                    marvelCode = this.code().toString(),
                    "Error getting info from Characters"
                )
            }
        }
    }

    override suspend fun getComics(characterId: Int): List<ComicDomain> {
        api.comics(characterId).apply {
            return if (this.isSuccessful) {
                body()?.let { body ->
                    comicMapper.transform(body)
                } ?: run {
                    throw MarvelApiException(
                        httpCode = this.code(),
                        marvelCode = this.code().toString(),
                        "Error getting info from Comics"
                    )
                }
            } else {
                throw MarvelApiException(
                    httpCode = this.code(),
                    marvelCode = this.code().toString(),
                    "Error getting info from Comics"
                )
            }
        }
    }

    override suspend fun getSeries(characterId: Int): List<SeriesDomain> {
        api.series(characterId).apply {
            this.whenBodyNotNull { body ->
                seriesMapper.transform(body as RemoteMarvelContainerResponse<SeriesResponse>)
            }
            return if (this.isSuccessful) {
                body()?.let { body ->
                    seriesMapper.transform(body)
                } ?: run {
                    throw MarvelApiException(
                        httpCode = this.code(),
                        marvelCode = this.code().toString(),
                        "Error getting info from Comics"
                    )
                }
            } else {
                throw MarvelApiException(
                    httpCode = this.code(),
                    marvelCode = this.code().toString(),
                    "Error getting info from Comics"
                )
            }
        }
    }
}

fun <T> Response<T>.whenBodyNotNull(block: (body: Any) -> Unit) {
    if (this.isSuccessful) {
        body()?.let { body ->
            block.invoke(body)
        } ?: run {
            throw MarvelApiException(
                httpCode = this.code(),
                marvelCode = this.code().toString(),
                "Error getting info from Comics"
            )
        }
    } else {
        throw MarvelApiException(
            httpCode = this.code(),
            marvelCode = this.code().toString(),
            "Error getting info from Comics"
        )
    }
}