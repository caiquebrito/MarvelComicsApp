package com.marvelcomics.brito.marvel.legacy.extensions

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.marvelcomics.brito.presentation.flow.EffectViewModel
import com.marvelcomics.brito.presentation.flow.StateViewModel
import com.marvelcomics.brito.presentation.flow.UIEffect
import com.marvelcomics.brito.presentation.flow.UIState
import com.marvelcomics.brito.presentation.flow.ViewModel
import com.marvelcomics.brito.presentation.flow.launchAndCollectIn
import com.marvelcomics.brito.presentation.flow.repeatExecuteAndCollectIn

inline fun <reified State : UIState, reified Effect : UIEffect> Fragment.onStateChange(
    viewModel: ViewModel<State, Effect>,
    crossinline handleStates: (State) -> Unit,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
) {
    viewModel.state.launchAndCollectIn(
        owner = viewLifecycleOwner,
        minActiveState = minActiveState,
        operation = { state -> handleStates(state) }
    )
}

inline fun <reified State : UIState, reified Effect : UIEffect> Fragment.onStateChange(
    viewModel: ViewModel<State, Effect>,
    crossinline handleStates: (State) -> Unit,
    crossinline onRepeat: () -> Unit,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
) {
    viewModel.state.repeatExecuteAndCollectIn(
        owner = viewLifecycleOwner,
        minActiveState = minActiveState,
        operation = { state -> handleStates(state) },
        onRepeat = onRepeat
    )
}

inline fun <reified State : UIState, reified Effect : UIEffect> Fragment.onEffectTriggered(
    viewModel: ViewModel<State, Effect>,
    crossinline handleEffect: (Effect) -> Unit,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
) {
    viewModel.effect.launchAndCollectIn(
        owner = viewLifecycleOwner,
        minActiveState = minActiveState,
        operation = { event -> handleEffect(event) }
    )
}

inline fun <reified State : UIState> Fragment.onStateChange(
    viewModel: StateViewModel<State>,
    crossinline handleStates: (State) -> Unit,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
) {
    viewModel.state.launchAndCollectIn(
        owner = viewLifecycleOwner,
        minActiveState = minActiveState,
        operation = { state -> handleStates(state) }
    )
}

inline fun <reified State : UIState> Fragment.onStateChange(
    viewModel: StateViewModel<State>,
    crossinline handleStates: (State) -> Unit,
    crossinline onRepeat: () -> Unit,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
) {
    viewModel.state.repeatExecuteAndCollectIn(
        owner = viewLifecycleOwner,
        minActiveState = minActiveState,
        operation = { state -> handleStates(state) },
        onRepeat = onRepeat
    )
}

inline fun <reified Effect : UIEffect> Fragment.onEffectTriggered(
    viewModel: EffectViewModel<Effect>,
    crossinline handleEffect: (Effect) -> Unit,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
) {
    viewModel.effect.launchAndCollectIn(
        owner = viewLifecycleOwner,
        minActiveState = minActiveState,
        operation = { event -> handleEffect(event) }
    )
}

fun Fragment.openScreen(direction: NavDirections) {
    findNavController().navigate(direction)
}

fun Fragment.openScreen(@IdRes id: Int) {
    findNavController().navigate(id)
}
