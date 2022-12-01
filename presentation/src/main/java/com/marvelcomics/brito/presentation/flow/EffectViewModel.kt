package com.marvelcomics.brito.presentation.flow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

abstract class EffectViewModel<Effect : UIEffect> : ViewModel() {

    private val viewModelEffect = Effect<Effect>()
    val effect: SharedFlow<Effect> = viewModelEffect.uiEffect

    protected open fun sendEffect(effect: Effect) {
        viewModelScope.launch { viewModelEffect.emitEffect(effect) }
    }
}
