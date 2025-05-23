package com.moondroid.cp.exoplayer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.AndroidExternalSurface
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.moondroid.cp.exoplayer.ui.theme.CP_ExoPlayerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CP_ExoPlayerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    PlayerScreen(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun PlayerScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            // Configure the player
            // here I'm making the video loop
            repeatMode = ExoPlayer.REPEAT_MODE_ALL
            playWhenReady = true
            setMediaItem(MediaItem.fromUri("https://cdn.pixabay.com/video/2015/08/20/468-136808389_large.mp4"))
            prepare()
        }
    }
    var isPlaying by remember { mutableStateOf(true) }
    Box(modifier = modifier) {
        VideoSurface(modifier = Modifier.fillMaxSize(), exoPlayer)
        VideoControls(modifier = Modifier.align(Alignment.Center), isPlaying = isPlaying) {
            if (isPlaying) {
                exoPlayer.pause()
            } else {
                exoPlayer.play()
            }
            isPlaying = !isPlaying
        }
    }

}

@Composable
fun VideoSurface(modifier: Modifier = Modifier, exoPlayer: ExoPlayer) {
    AndroidExternalSurface(modifier = modifier,
        onInit = {
            onSurface { surface, _, _ ->
                exoPlayer.setVideoSurface(surface)
                surface.onDestroyed { exoPlayer.setVideoSurface(null) }
            }
        }

    )
}

@Composable
fun VideoControls(
    modifier: Modifier = Modifier,
    isPlaying: Boolean,
    onClick: () -> Unit
) {
    IconButton(modifier = modifier, onClick = onClick) {
        Icon(
            imageVector = if (isPlaying) {
                Icons.Outlined.PlayArrow
            } else {
                Icons.Filled.Place
            }, contentDescription = null, tint = Color.White
        )
    }
}
