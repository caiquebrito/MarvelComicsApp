@file:OptIn(ExperimentalCoroutinesApi::class)

package com.marvelcomics.brito.presentation

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Assert.assertEquals

class TestFlowCollector<T>(
    scope: CoroutineScope,
    testScheduler: TestCoroutineScheduler,
    flow: Flow<T>
) {
    private val values = mutableListOf<T>()

    private val job: Job = scope.launch(UnconfinedTestDispatcher(testScheduler)) {
        flow.collect { values.add(it) }
    }

    fun assertNoValues() {
        assertEquals(emptyList<T>(), this.values)
    }

    fun assertValuesInOrder(vararg values: T) {
        assertEquals(values.toList(), this.values)
    }

    fun finishJob() {
        job.cancel()
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
fun <T> Flow<T>.test(
    scope: TestScope
): TestFlowCollector<T> {
    return TestFlowCollector(scope, scope.testScheduler, this)
}

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
