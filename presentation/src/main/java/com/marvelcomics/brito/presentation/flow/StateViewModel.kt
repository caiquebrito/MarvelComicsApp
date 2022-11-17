package br.com.cora.common.viewmodel.flow

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.StateFlow

abstract class StateViewModel<State : UIState>(
    initialState: State
) : ViewModel() {

    private val viewModelState = State(initialState)
    val state: StateFlow<State> = viewModelState.uiState

    protected fun setState(state: (State) -> State) {
        viewModelState.updateState(state)
    }
}
