package com.marvelcomics.brito.domain

import com.marvelcomics.brito.domain.usecase.CharacterUseCase
import org.koin.dsl.module

object DomainModules {
    val usesCases = module {
        factory { CharacterUseCase(get()) }
    }
}
