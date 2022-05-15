package com.marvelcomics.brito.presentation.di

import com.marvelcomics.brito.presentation.character.CharacterViewModel
import com.marvelcomics.brito.presentation.comic.ComicViewModel
import com.marvelcomics.brito.presentation.series.SeriesViewModel
import kotlinx.coroutines.InternalCoroutinesApi
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@InternalCoroutinesApi
object PresentationModules {
    val viewModels = module {
        viewModel { CharacterViewModel(get()) }
        viewModel { ComicViewModel(get()) }
        viewModel { SeriesViewModel(get()) }
    }
}