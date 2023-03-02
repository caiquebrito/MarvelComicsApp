package com.marvelcomics.brito.presentation.ui.compose.extension

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlin.coroutines.EmptyCoroutineContext

/**
 * Function to be used to handle one shot events with compose like navigation =)
 * collect on stackoverflow answer
 * https://stackoverflow.com/questions/71624886/how-to-use-sharedflow-in-jetpack-compose
 */
@Composable
fun <T> Flow<T>.collectAsEffect(
    block: (T) -> Unit
) {
    LaunchedEffect(key1 = Unit) {
        onEach(block).flowOn(EmptyCoroutineContext).launchIn(this)
    }
}
