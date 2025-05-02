package com.point.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.staticCompositionLocalOf
import com.point.settings.AppUiSettings
import com.point.ui.colors.EnvelopeColors
import com.point.ui.colors.lightColorScheme
import com.point.user.LocalUser

@Composable
fun EnvelopeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    localUser: LocalUser? = null,
    localUiSettings: AppUiSettings = AppUiSettings(),
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalTypography provides EnvelopeTypography(),
        LocalColor provides lightColorScheme,
        LocalUser provides localUser,
        LocalUiSettings provides localUiSettings,
        content = content,
    )
}

object Theme {
 
    val colorScheme: EnvelopeColors
        @Composable
        @ReadOnlyComposable
        get() = LocalColor.current

    val typography: EnvelopeTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalTypography.current


    internal var envelopeDefaultTextFieldColors: TextFieldColors? = null

}

internal val LocalColor = compositionLocalOf { lightColorScheme }

internal val LocalTypography = staticCompositionLocalOf { EnvelopeTypography() }

val LocalUser = staticCompositionLocalOf<LocalUser?> { null }

val LocalUiSettings = staticCompositionLocalOf { AppUiSettings() }