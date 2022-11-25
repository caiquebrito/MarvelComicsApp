package com.marvelcomics.brito.presentation

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

fun <T> MutableSharedFlow<T>.emitOnScope(scope: CoroutineScope, value: T) {
    val mutable = this
    scope.launch {
        mutable.emit(value)
    }
}
