package com.point.settings.model

data class AppSettings(
    val useAnimations: Boolean = true,
    val batteryThreshold: Int = 10,
    val selectedColor: AppColor = AppColor.BLUE,
)

enum class AppColor {
    RED,
    BLUE,
    GREEN,
    BROWN,
    PURPLE,
}
