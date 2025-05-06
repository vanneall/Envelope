package com.point.chats.camera

import android.net.Uri
import android.util.Log
import androidx.camera.core.AspectRatio
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCapture.FLASH_MODE_OFF
import androidx.camera.core.ImageCapture.FLASH_MODE_ON
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.FlashOff
import androidx.compose.material.icons.rounded.FlashOn
import androidx.compose.material.icons.rounded.GridOn
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.zIndex
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.point.navigation.Route
import com.point.ui.Theme
import timber.log.Timber
import java.io.File

@Composable
fun CameraScreen(
    viewModel: CameraViewModel = hiltViewModel(),
    navigate: (Route) -> Unit,
    onBack: (List<Uri>) -> Unit,
    back: () -> Unit,
    onImageCaptured: (Uri) -> Unit,
    modifier: Modifier = Modifier,
) {
    val capturedUris = remember { mutableStateOf(emptyList<Uri>()) }
    val systemUiController = rememberSystemUiController()
    val previousDarkIcons = remember { systemUiController.statusBarDarkContentEnabled }
    var isCapturing by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
    val previewView = remember<PreviewView> { PreviewView(context) }
    val state by viewModel.state

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

    val aspectRatio = when (state.aspectRatio) {
        AspectRatioOption.RATIO_4_3 -> AspectRatio.RATIO_4_3
        AspectRatioOption.RATIO_16_9 -> AspectRatio.RATIO_16_9
    }

    LaunchedEffect(state.aspectRatio, state.isFlashEnabled) {
        val cameraProvider = cameraProviderFuture.get()
        val preview = Preview.Builder()
            .setTargetAspectRatio(aspectRatio)
            .build()
            .also { it.setSurfaceProvider(previewView?.surfaceProvider) }

        val imageCapture = ImageCapture.Builder()
            .setTargetAspectRatio(aspectRatio)
            .setFlashMode(if (state.isFlashEnabled) FLASH_MODE_ON else FLASH_MODE_OFF)
            .build()

        val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

        try {
            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(
                lifecycleOwner,
                cameraSelector,
                preview,
                imageCapture
            )
        } catch (e: Exception) {
            Timber.tag("CameraScreen").e(e, "Camera binding failed")
        }

        previewView.tag = imageCapture
    }


    Box(
        modifier = modifier.background(Color.Black)
    ) {
        AndroidView(
            factory = { previewView },
            modifier = if (state.aspectRatio == AspectRatioOption.RATIO_16_9) {
                Modifier.fillMaxSize()
            } else {
                Modifier
                    .fillMaxWidth()
                    .aspectRatio(4f / 3f)
                    .align(Alignment.Center)
            }
        )

        if (state.isGridEnabled) {
            GridOverlay(
                modifier = if (state.aspectRatio == AspectRatioOption.RATIO_16_9) {
                    Modifier.fillMaxSize()
                } else {
                    Modifier
                        .fillMaxWidth()
                        .aspectRatio(4f / 3f)
                        .align(Alignment.Center)
                }
            )
        }

        val overlayColor by animateColorAsState(
            targetValue = if (isCapturing) Color.Black.copy(alpha = 0.6f) else Color.Transparent,
            animationSpec = tween(durationMillis = 300),
            label = "captureOverlayColor"
        )

        if (isCapturing) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(overlayColor)
                    .zIndex(2f)
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Верхняя панель с кнопками
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                FilledIconButton(
                    onClick = { onBack(capturedUris.value.toList()) },
                    colors = IconButtonDefaults.filledIconButtonColors(containerColor = Color.White)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.ArrowBack,
                        contentDescription = "Toggle Flash",
                        tint = Color.Black
                    )
                }

                FilledIconButton(
                    onClick = viewModel::toggleFlash,
                    colors = IconButtonDefaults.filledIconButtonColors(containerColor = Color.White)
                ) {
                    Icon(
                        imageVector = if (state.isFlashEnabled) Icons.Rounded.FlashOn else Icons.Rounded.FlashOff,
                        contentDescription = "Toggle Flash",
                        tint = Color.Black
                    )
                }

                FilledIconButton(
                    onClick = viewModel::toggleGrid,
                    colors = IconButtonDefaults.filledIconButtonColors(containerColor = Color.White)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.GridOn,
                        contentDescription = "Toggle Grid",
                        tint = if (state.isGridEnabled) Color.Black else Color.Gray
                    )
                }

                FilledIconButton(
                    onClick = viewModel::toggleAspectRatio,
                    colors = IconButtonDefaults.filledIconButtonColors(containerColor = Color.White)
                ) {
                    Text(
                        text = if (state.aspectRatio == AspectRatioOption.RATIO_16_9) "16:9" else "4:3",
                        color = Color.Black,
                        style = Theme.typography.bodyM,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Box(modifier = Modifier.fillMaxWidth()) {
                FilledIconButton(
                    onClick = { navigate(Route.ChatsFeature.Gallery) },
                    colors = IconButtonDefaults.filledIconButtonColors(containerColor = Color.White),
                    modifier = Modifier
                        .size(48.dp)
                        .align(Alignment.BottomStart)
                ) {
                    Icon(
                        imageVector = Icons.Default.PhotoLibrary,
                        contentDescription = "Open Gallery",
                        tint = Color.Black
                    )
                }

                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .align(Alignment.BottomCenter)
                        .clip(CircleShape)
                        .background(Color.White)
                        .padding(2.dp)
                        .border(width = 3.dp, Color.Black, shape = CircleShape)
                        .clickable(
                            indication = rememberRipple(bounded = true),
                            interactionSource = remember { MutableInteractionSource() }
                        ) {
                            isCapturing = true

                            val imageCapture = previewView.tag as? ImageCapture ?: return@clickable
                            val photoFile = File(context.cacheDir, "IMG_${System.currentTimeMillis()}.jpg")
                            val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

                            imageCapture.takePicture(
                                outputOptions,
                                ContextCompat.getMainExecutor(context),
                                object : ImageCapture.OnImageSavedCallback {
                                    override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                                        onImageCaptured(photoFile.toUri())
                                        val uri = photoFile.toUri()
                                        capturedUris.value += uri
                                        isCapturing = false
                                    }

                                    override fun onError(exception: ImageCaptureException) {
                                        Log.e("CameraScreen", "Capture failed", exception)
                                        isCapturing = false
                                    }
                                }
                            )
                        }
                )
            }
        }
    }
}

@Composable
fun GridOverlay(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height

        val lineColor = Color.Black.copy(alpha = 0.4f)
        val stroke = Stroke(width = 1.dp.toPx()).width

        // Вертикальные линии
        drawLine(lineColor, Offset(w / 3, 0f), Offset(w / 3, h), stroke)
        drawLine(lineColor, Offset(2 * w / 3, 0f), Offset(2 * w / 3, h), stroke)

        // Горизонтальные линии
        drawLine(lineColor, Offset(0f, h / 3), Offset(w, h / 3), stroke)
        drawLine(lineColor, Offset(0f, 2 * h / 3), Offset(w, 2 * h / 3), stroke)
    }
}
