package com.marvelcomics.brito.domain.usecase

import com.marvelcomics.brito.domain.repository.MarvelRepository

class LoadSeriesUseCase(
    private val marvelRepository: MarvelRepository
) {
    operator fun invoke(id: Int) = marvelRepository.getSeries(id)
}
