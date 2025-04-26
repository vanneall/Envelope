package com.point.ui.colors

import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.point.ui.Theme

val Black = Color(0xFF000000)
val Black50 = Color(0x80000000)
val Gray = Color(0xFFB0B0B0)
val GrayLight = Color(0xFFE0E0E0)
val GrayDark = Color(0xFF606060)

val BlueContainerLight = Color(0xFFd1e4ff)
val BlueContentLight = Color(0xFF2E6CA9)

val OrangeContainer = Color(0xFFFFDFB8)
val OrangeContent = Color(0xFFFFA41F)

val GreenContainer = Color(0xFFF0FFBD)
val GreenContent = Color(0xFFB5C70B)

val GrayContainerLight = Color(0xFFCBCBCB)
val GrayContentLight = Color(0xFF7E7E81)

val CyanContainer = Color(0xFFC9FFFF)
val CyanContent = Color(0xFF35D8E3)

val PurpleContainer = Color(0xFFD3BAFC)
val PurpleContent = Color(0xFF936BD5)

val RedContainer = Color(0xFFFFC1C1)
val RedContent = Color(0xFFF83636)




val White = Color(0xFFFFFFFF)
val Red = Color(0xFFFF3333)
val Green = Color(0xFF4CAF50)
val Blue = Color(0xFF007ACC)

val BackgroundWhite = com.point.ui.colors.new.White
val SurfaceLight = Color(0xFFF5F5F5)

class EnvelopeColors(
    val primary: Color,
    val secondary: Color,
    val accent: Color,
    val success: Color,
    val error: Color,
    val background: Color,
    val surface: Color,
    val disabled: Color,
    val divider: Color,
    val textPrimary: Color,
    val textSecondary: Color,
    val overlay: Color,
) {

    internal val defaultTextFieldColors: TextFieldColors
        @Composable
        get() = Theme.envelopeDefaultTextFieldColors ?: TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedContainerColor = Theme.colorScheme.surface,
            errorContainerColor = Theme.colorScheme.surface,
            disabledContainerColor = Theme.colorScheme.surface,
            unfocusedContainerColor = Theme.colorScheme.surface,
            cursorColor = Theme.colorScheme.accent,
            errorTrailingIconColor = Theme.colorScheme.error,
            errorCursorColor = Theme.colorScheme.error,
        ).also {
            Theme.envelopeDefaultTextFieldColors = it
        }
}