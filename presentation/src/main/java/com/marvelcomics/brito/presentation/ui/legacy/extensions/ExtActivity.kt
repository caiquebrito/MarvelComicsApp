package com.marvelcomics.brito.presentation.ui.legacy.extensions

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import com.marvelcomics.brito.presentation.flow.EffectViewModel
import com.marvelcomics.brito.presentation.flow.StateViewModel
import com.marvelcomics.brito.presentation.flow.UIEffect
import com.marvelcomics.brito.presentation.flow.UIState
import com.marvelcomics.brito.presentation.flow.ViewModel
import com.marvelcomics.brito.presentation.flow.launchAndCollectIn
import com.marvelcomics.brito.presentation.flow.repeatExecuteAndCollectIn

inline fun <reified State : UIState, reified Effect : UIEffect> AppCompatActivity.onStateChange(
    viewModel: ViewModel<State, Effect>,
    crossinline handleStates: (State) -> Unit,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED
) {
    viewModel.state.launchAndCollectIn(
        owner = this,
        minActiveState = minActiveState,
        operation = { state -> handleStates(state) }
    )
}

inline fun <reified State : UIState, reified Effect : UIEffect> AppCompatActivity.onStateChange(
    viewModel: ViewModel<State, Effect>,
    crossinline handleStates: (State) -> Unit,
    crossinline onRepeat: () -> Unit,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED
) {
    viewModel.state.repeatExecuteAndCollectIn(
        owner = this,
        minActiveState = minActiveState,
        operation = { state -> handleStates(state) },
        onRepeat = onRepeat
    )
}

inline fun <reified State : UIState, reified Effect : UIEffect> AppCompatActivity.onEffectTriggered(
    viewModel: ViewModel<State, Effect>,
    crossinline handleEffect: (Effect) -> Unit,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED
) {
    viewModel.effect.launchAndCollectIn(
        owner = this,
        minActiveState = minActiveState,
        operation = { event -> handleEffect(event) }
    )
}

inline fun <reified State : UIState> AppCompatActivity.onStateChange(
    viewModel: StateViewModel<State>,
    crossinline handleStates: (State) -> Unit,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED
) {
    viewModel.state.launchAndCollectIn(
        owner = this,
        minActiveState = minActiveState,
        operation = { state -> handleStates(state) }
    )
}

inline fun <reified State : UIState> AppCompatActivity.onStateChange(
    viewModel: StateViewModel<State>,
    crossinline handleStates: (State) -> Unit,
    crossinline onRepeat: () -> Unit,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED
) {
    viewModel.state.repeatExecuteAndCollectIn(
        owner = this,
        minActiveState = minActiveState,
        operation = { state -> handleStates(state) },
        onRepeat = onRepeat
    )
}

inline fun <reified Effect : UIEffect> AppCompatActivity.onEffectTriggered(
    viewModel: EffectViewModel<Effect>,
    crossinline handleEffect: (Effect) -> Unit,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED
) {
    viewModel.effect.launchAndCollectIn(
        owner = this,
        minActiveState = minActiveState,
        operation = { event -> handleEffect(event) }
    )
}
