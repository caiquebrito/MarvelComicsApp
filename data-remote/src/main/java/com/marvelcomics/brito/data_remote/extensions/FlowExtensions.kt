package com.marvelcomics.brito.data_remote.extensions

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch

/**
 * When a Flow throws an [Exception] this function tries to parse the [Exception] as an [ErrorBodyException].
 * If an [Exception] mapper is given, then the fun will try to parse the received [ErrorBodyException] to one from
 * the mapper.
 */
fun <T> Flow<T>.catchHttpException(): Flow<T> =
    catch { throwable ->
        throw throwable.parseHttpExceptionOrThrow()
    }

/**
 * When a Flow throws an [Exception] this function will execute the same as [catchHttpException]
 * with a plus that it will try to throw a Cora Mapped Error
 * If no mapping is passed it will use the [defaultMapping]
 * If the code mapping is not found, it will throw a [CoraMappingCodeNotFound]
 * If the [Throwable] is not a [ErrorBodyException] it will thrown itself
 */
fun <T> Flow<T>.catchMappedException(mapCode: Map<String, Throwable>? = null): Flow<T> =
    catch { throwable ->
        val parsedHttpException = throwable.parseHttpExceptionOrThrow()
        throw mapCode?.let {
            parsedHttpException.mapCoraExceptionOrThrow(it)
        } ?: parsedHttpException.mapCoraExceptionOrThrow(defaultMapping)
    }
