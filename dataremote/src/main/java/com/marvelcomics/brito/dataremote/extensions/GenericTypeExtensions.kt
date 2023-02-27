package com.marvelcomics.brito.dataremote.extensions

import com.marvelcomics.brito.dataremote.exception.ErrorHandlingNullException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

inline fun <reified T> handleFlowApi(
    mappedCodes: Map<String, Throwable>? = null,
    noinline errorHandling: ((throwable: Throwable) -> T)? = null,
    crossinline callHandling: () -> T
): Flow<T> {
    val flow = flow {
        emit(callHandling.invoke())
    }
    return errorHandling?.let {
        flow.catch {
            errorHandling.invoke(it)
        }
    } ?: flow.catchMappedException(mappedCodes)
}

inline fun <reified T> handleApi(
    noinline errorHandling: ((throwable: Throwable) -> T)? = null,
    callHandling: () -> T
): T {
    return try {
        callHandling.invoke()
    } catch (throwable: Throwable) {
        errorHandling?.invoke(throwable) ?: throwable.parseHttpExceptionOrThrow()
            .mapCoraExceptionOrThrow() as T
    } ?: throw ErrorHandlingNullException()
}

/**
 * Given the Generic Type object, it will check if is null
 * Returning the object itself as nonNullable, or throw [NullPointerException]
 *  */
inline fun <reified T> T?.throwIfNull(): T =
    this?.let {
        return it
    } ?: throw NullPointerException()
