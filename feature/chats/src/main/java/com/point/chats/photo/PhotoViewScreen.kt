package com.point.chats.photo

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.core.net.toUri
import coil.compose.AsyncImage
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun PhotoViewScreen(uriString: String) {
    val systemUiController = rememberSystemUiController()
    val previousDarkIcons = remember { systemUiController.statusBarDarkContentEnabled }

    LaunchedEffect(Unit) {
        systemUiController.setStatusBarColor(
            color = Color.Transparent,
            darkIcons = false
        )
    }

    DisposableEffect(Unit) {
        onDispose {
            systemUiController.setStatusBarColor(
                color = Color.Transparent,
                darkIcons = previousDarkIcons
            )
        }
    }

    val uri = uriString.toUri()
    ZoomableImage(
        imageUri = uri,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    )
}

@Composable
fun ZoomableImage(
    imageUri: Uri,
    modifier: Modifier = Modifier
) {
    val scale = remember { mutableFloatStateOf(1f) }
    val offset = remember { mutableStateOf(Offset.Zero) }

    Box(
        modifier = modifier
            .pointerInput(Unit) {
                detectTransformGestures { _, pan, zoom, _ ->
                    scale.floatValue *= zoom
                    offset.value += pan
                }
            }
    ) {
        val minScale = 1f
        val maxScale = 5f
        val clampedScale = scale.floatValue.coerceIn(minScale, maxScale)

        AsyncImage(
            model = imageUri,
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .graphicsLayer(
                    scaleX = clampedScale,
                    scaleY = clampedScale,
                    translationX = offset.value.x,
                    translationY = offset.value.y
                )
                .align(Alignment.Center)
        )
    }
}
