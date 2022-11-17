package br.com.cora.common.viewmodel.flow

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

interface UIEffect

class Effect<Effect : UIEffect> {

    private val _uiEffect = MutableSharedFlow<Effect>()
    val uiEffect: SharedFlow<Effect> = _uiEffect.asSharedFlow()

    suspend fun emitEffect(effect: Effect) {
        _uiEffect.emit(effect)
    }
}
