package com.marvelcomics.brito.domain.usecase

import com.marvelcomics.brito.domain.DispatcherRule
import com.marvelcomics.brito.domain.fake.MarvelFakeRepository
import com.marvelcomics.brito.entity.SeriesEntity
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class LoadSeriesUseCaseTest {

    @get:Rule
    val dispatcherRule = DispatcherRule()

    private var listSeries = listOf(SeriesEntity(1, "Title", "Description", null))
    private val marvelRepository = MarvelFakeRepository()
    private val loadSeriesUseCase = LoadSeriesUseCase(marvelRepository)

    @Test
    fun `when the result is sucess and validate object`() = runTest {
        loadSeriesUseCase.invoke(10).collect {
            assertEquals(listSeries, it)
        }
    }
}
