package com.marvelcomics.brito.presentation

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Assert.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class TestFlowCollector<T>(
    scope: CoroutineScope,
    testScheduler: TestCoroutineScheduler,
    flow: Flow<T>
) {
    private val values = mutableListOf<T>()

    private val job: Job = scope.launch(UnconfinedTestDispatcher(testScheduler)) {
        flow.collect { values.add(it) }
    }

    fun assertNoValues(keepJobAlive: Boolean = false): TestFlowCollector<T> {
        assertEquals(emptyList<T>(), this.values)
        if (keepJobAlive)
            job.cancel()
        return this
    }

    fun assertValuesInOrder(vararg values: T, keepJobAlive: Boolean = false): TestFlowCollector<T> {
        assertEquals(values.toList(), this.values)
        if (keepJobAlive)
            job.cancel()
        return this
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
fun <T> Flow<T>.test(
    scope: TestScope
): TestFlowCollector<T> {
    return TestFlowCollector(scope, scope.testScheduler, this)
}
