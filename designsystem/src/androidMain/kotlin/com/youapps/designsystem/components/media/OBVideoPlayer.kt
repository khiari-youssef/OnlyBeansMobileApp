package com.youapps.designsystem.components.media

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.currentStateAsState
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView




@Composable
fun OBVideoPlayer(
    modifier: Modifier = Modifier,
    videoUri: String
) {
    val mediaItem = MediaItem.fromUri(videoUri)
    val currentLifeCycle = LocalLifecycleOwner.current
     val lifecycleState = currentLifeCycle.lifecycle.currentStateAsState()

    val context = LocalContext.current
    val player = remember(context) {
        ExoPlayer.Builder(context)
            .build()
    }

    AndroidView(
        modifier = modifier,
        factory = { context->
            PlayerView(context).apply {
              this.player = player
            }.also {
                player.setMediaItem(mediaItem);
                player.prepare();
                player.playWhenReady = true
            }
        }
    )
    LaunchedEffect(lifecycleState.value) {
        if (lifecycleState.value == Lifecycle.State.DESTROYED){
            if (player.isReleased.not()) {
                player.release()
            }
        }
    }
}


