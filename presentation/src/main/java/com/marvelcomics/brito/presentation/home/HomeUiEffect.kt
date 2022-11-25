package com.marvelcomics.brito.presentation.home

import br.com.cora.common.viewmodel.flow.UIEffect
import com.marvelcomics.brito.domain.models.CharacterDomain

sealed class HomeUiEffect : UIEffect {
    object OpenSearchScreen : HomeUiEffect()
    data class ShowMarvelHeroes(val heroes: List<CharacterDomain>) : HomeUiEffect()
}
