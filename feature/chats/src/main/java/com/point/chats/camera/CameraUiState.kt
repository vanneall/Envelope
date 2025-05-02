package com.point.chats.camera

enum class AspectRatioOption { RATIO_4_3, RATIO_16_9 }

data class CameraUiState(
    val isFlashEnabled: Boolean = false,
    val aspectRatio: AspectRatioOption = AspectRatioOption.RATIO_16_9,
    val isGridEnabled: Boolean = false
)

