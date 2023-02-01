package com.marvelcomics.brito.presentation

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule

@ExperimentalCoroutinesApi
open class BaseViewModelTest {

    @get:Rule
    var dispatcherRule = DispatcherRule()

    fun <Clazz> executeOnBlockingTestScope(
        state: SharedFlow<Clazz>,
        effect: SharedFlow<Clazz>,
        block: (List<Clazz>, List<Clazz>) -> Unit
    ) =
        dispatcherRule.runBlockingTest {
            val stateEmissions = mutableListOf<Clazz>()
            val effectEmissions = mutableListOf<Clazz>()
            val jobState = launch {
                state.toList(stateEmissions)
            }
            val jobEffect = launch {
                effect.toList(effectEmissions)
            }

            block.invoke(stateEmissions, effectEmissions)

            jobState.cancel()
            jobEffect.cancel()
        }
}
