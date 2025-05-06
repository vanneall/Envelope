package com.point.settings.model

data class AppSettings(
    val useAnimations: Boolean = true,
    val batteryThreshold: Int = 10,
    val selectedColor: AppColor = AppColor.BLUE,
    val letterSpacing: LetterSpacingPresetSettings = LetterSpacingPresetSettings.NORMAL,
)

enum class AppColor {
    RED,
    BLUE,
    GREEN,
    BROWN,
    PURPLE,
}

enum class LetterSpacingPresetSettings {
    NORMAL,
    MEDIUM,
    WIDE,
    EXTRA_WIDE,
    ;
}

