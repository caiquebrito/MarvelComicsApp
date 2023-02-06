package com.marvelcomics.brito.presentation

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.test.TestScope
import org.junit.Rule

@ExperimentalCoroutinesApi
open class BaseViewModelTest {

    @get:Rule
    var coroutineTestRule = CoroutineTestRule()

    fun <T> TestScope.collectTestFlows(
        state: StateFlow<T>,
        shared: SharedFlow<T>,
        block: (TestFlowCollector<T>, TestFlowCollector<T>) -> Unit
    ) {
        val stateObserver = state.test(this)
        val sharedObserver = shared.test(this)
        block.invoke(stateObserver, sharedObserver)
        stateObserver.finishJob()
        sharedObserver.finishJob()
    }

    fun <T> TestScope.collectTestFlows(
        state: StateFlow<T>,
        block: (TestFlowCollector<T>) -> Unit
    ) {
        val stateObserver = state.test(this)
        block.invoke(stateObserver)
        stateObserver.finishJob()
    }

    fun <T> TestScope.collectTestFlows(
        shared: SharedFlow<T>,
        block: (TestFlowCollector<T>) -> Unit
    ) {
        val sharedObserver = shared.test(this)
        block.invoke(sharedObserver)
        sharedObserver.finishJob()
    }
}
