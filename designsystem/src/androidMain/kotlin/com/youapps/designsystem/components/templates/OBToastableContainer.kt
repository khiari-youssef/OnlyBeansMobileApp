package com.youapps.designsystem.components.templates

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.youapps.designsystem.components.popups.OBToast
import com.youapps.designsystem.components.popups.OBToastData

class ToastVisibilityState(initiallyShown: Boolean = false) {
    // Controls the visual state
    val transitionState = MutableTransitionState(initiallyShown)
     var currentData : OBToastData?=null


    val isShown: Boolean
        get() = transitionState.currentState || transitionState.targetState

    suspend fun show(data : OBToastData) {
        currentData = data;
        transitionState.targetState = true
        // Wait for the animation to finish
        while (transitionState.currentState != transitionState.targetState) {
            withFrameNanos { }
        }
    }

    suspend fun hide() {
        currentData = null;
        transitionState.targetState = false
        while (transitionState.currentState != transitionState.targetState) {
            withFrameNanos { }
        }
    }

    companion object{
        @Composable
        fun rememberToastVisibilityState(initial: Boolean = false) = remember { ToastVisibilityState(initial) }
    }
}


@Composable
fun OBToastableContainer(
    modifier: Modifier = Modifier,
    state : ToastVisibilityState = ToastVisibilityState.rememberToastVisibilityState(),
    onDismissRequest : ()-> Unit,
    content: @Composable ()-> Unit
) {

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) { 
        content()
        AnimatedVisibility(
        modifier = Modifier
            .padding(
                horizontal = 16.dp
            )
            .padding(
                bottom = 20.dp
            )
            .align(Alignment.BottomCenter),
            visibleState = state.transitionState,
            enter = slideInVertically(tween(durationMillis = 300, easing = EaseOut)) {
                it
            },
            exit = slideOutVertically(tween(durationMillis = 300, easing = EaseIn)) {
                it * 2
            },
            label = "ToastVisibilityAnimation"
        ) {
            state.currentData?.let {
                OBToast(
                    modifier = Modifier
                        .fillMaxWidth(),
                    data = it,
                    onDismissRequest = onDismissRequest
                )
            }

        }
    }
}