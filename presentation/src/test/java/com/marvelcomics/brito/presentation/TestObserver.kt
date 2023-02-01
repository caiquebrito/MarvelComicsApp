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
/**
 * Test observer for Flow which allow to capture all states and verify all states.
 */
class TestObserver<T>(
    scope: CoroutineScope,
    testScheduler: TestCoroutineScheduler,
    flow: Flow<T>
) {
    private val values = mutableListOf<T>()

    private val job: Job = scope.launch(UnconfinedTestDispatcher(testScheduler)) {
        flow.collect { values.add(it) }
    }

    /**
     * Assert no values
     */
    fun assertNoValuesKeepJob(): TestObserver<T> {
        assertEquals(emptyList<T>(), this.values)
        return this
    }

    /**
     * Assert no values
     */
    fun assertNoValues(): TestObserver<T> {
        assertEquals(emptyList<T>(), this.values)
        finish()
        return this
    }

    /**
     * Assert the values. Important [TestObserver.finish] needs to be called at the end of the test.
     */
    fun assertValuesKeepJob(vararg values: T): TestObserver<T> {
        assertEquals(values.toList(), this.values)
        return this
    }

    /**
     * Assert the values. Also finishes the job itself after assetions.
     * Remember to don't make any new calls after finished
     */
    fun assertValues(vararg values: T): TestObserver<T> {
        assertEquals(values.toList(), this.values)
        finish()
        return this
    }

    /**
     * Finish the job
     */
    fun finish() {
        job.cancel()
    }
}

/**
 * Test function for the [TestObserver]
 */
@OptIn(ExperimentalCoroutinesApi::class)
fun <T> Flow<T>.test(
    scope: TestScope
): TestObserver<T> {
    return TestObserver(scope, scope.testScheduler, this)
}
