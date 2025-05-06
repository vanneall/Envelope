package com.point.settings

import com.point.color.AppColorColor

data class AppUiSettings(
    val useAnimations: Boolean = true,
    val color: AppColorColor = AppColorColor.BLUE,
    val access: Accessibility = Accessibility(),
)

data class Accessibility(
    val letterSpacing: LetterSpacingPreset = LetterSpacingPreset.NORMAL,
)

enum class LetterSpacingPreset {
    NORMAL,
    MEDIUM,
    WIDE,
    EXTRA_WIDE;

    fun toSp(): Float = when (this) {
        NORMAL -> 0f
        MEDIUM -> 1.5f
        WIDE -> 2.5f
        EXTRA_WIDE -> 4f
    }
}