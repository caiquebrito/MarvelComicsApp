package com.marvelcomics.brito.data_remote.repository

import com.marvelcomics.brito.data.remote.MarvelRemoteDataSource
import com.marvelcomics.brito.data_remote.api.MarvelAPI
import com.marvelcomics.brito.data_remote.datasource.mapper.CharacterMapper
import com.marvelcomics.brito.data_remote.datasource.mapper.ComicMapper
import com.marvelcomics.brito.data_remote.datasource.mapper.SeriesMapper
import com.marvelcomics.brito.data_remote.exception.MarvelApiException
import com.marvelcomics.brito.domain.models.CharacterDomain
import com.marvelcomics.brito.domain.models.ComicDomain
import com.marvelcomics.brito.domain.models.SeriesDomain

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

// fun <T> Response<T>.handleResponse(
//    onSuccess: (body: Any) -> Unit,
//    onError: ((exception: ErrorCallResponse) -> Unit)
// ) {
//    if (this.isSuccessful) {
//        body()?.let { body ->
//            onSuccess.invoke(body)
//        } ?: run {
//            onError.invoke(getMessageFromError(this.errorBody()))
//        }
//    } else {
//        onError.invoke(getMessageFromError(this.errorBody()))
//    }
// }
//
// private fun getMessageFromError(errorBody: ResponseBody?): ErrorCallResponse {
//    val type = object : TypeToken<ErrorCallResponse>() {}.type
//    return Gson().fromJson(errorBody?.charStream(), type)
// }
//
// data class ErrorCallResponse(
//    @SerializedName("code") val code: String,
//    @SerializedName("errorMessage") val errorMessage: String
// )
