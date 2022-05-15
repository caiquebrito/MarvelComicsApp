package com.marvelcomics.brito.domain

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import org.junit.Rule

@ExperimentalCoroutinesApi
open class BaseUseCaseTest {

    @get:Rule
    var testCoroutineRule = TestCoroutineRule()

    fun <Clazz> executeOnBlockingTestScope(state: SharedFlow<Clazz>, block: (List<Clazz>) -> Unit) =
        testCoroutineRule.runBlockingTest {
            val emissions = mutableListOf<Clazz>()
            val job = launch {
                state.toList(emissions)
            }

            block.invoke(emissions)

            job.cancel()
        }
}
