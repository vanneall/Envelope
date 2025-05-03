package com.point.settings

import com.point.color.AppColorColor

data class AppUiSettings(
    val useAnimations: Boolean = true,
    val color: AppColorColor = AppColorColor.BLUE,
)