package com.marvelcomics.brito.presentation.flow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

abstract class ViewModel<State : UIState, Effect : UIEffect>(
    initialState: State
) : ViewModel() {

    private val viewModelState = State(initialState)
    private val viewModelEffect = Effect<Effect>()

    val state: StateFlow<State> = viewModelState.uiState
    val effect: SharedFlow<Effect> = viewModelEffect.uiEffect

    protected fun setState(state: (State) -> State) {
        viewModelState.updateState(state)
    }

    protected open fun sendEffect(effect: Effect) {
        viewModelScope.launch { viewModelEffect.emitEffect(effect) }
    }
}
