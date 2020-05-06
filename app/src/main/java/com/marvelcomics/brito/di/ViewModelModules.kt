package com.marvelcomics.brito.marvelcomics.di

import com.marvelcomics.brito.viewmodel.character.CharacterViewModel
import com.marvelcomics.brito.viewmodel.comic.ComicViewModel
import com.marvelcomics.brito.viewmodel.series.SeriesViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

object ViewModelModules {
    val viewModels = module {
        viewModel { CharacterViewModel(get()) }
        viewModel { ComicViewModel(get()) }
        viewModel { SeriesViewModel(get()) }
    }
}