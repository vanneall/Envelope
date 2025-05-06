package com.point.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.sp
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
    val baseTypography = EnvelopeTypography()
    val spacingSp = localUiSettings.access.letterSpacing.toSp().sp
    val adjustedTypography = EnvelopeTypography(
        headlineL = baseTypography.headlineL.copy(letterSpacing = spacingSp),
        headlineM = baseTypography.headlineM.copy(letterSpacing = spacingSp),
        titleL = baseTypography.titleL.copy(letterSpacing = spacingSp),
        titleM = baseTypography.titleM.copy(letterSpacing = spacingSp),
        titleS = baseTypography.titleS.copy(letterSpacing = spacingSp),
        bodyL = baseTypography.bodyL.copy(letterSpacing = spacingSp),
        bodyM = baseTypography.bodyM.copy(letterSpacing = spacingSp),
        bodyS = baseTypography.bodyS.copy(letterSpacing = spacingSp),
        labelL = baseTypography.labelL.copy(letterSpacing = spacingSp),
        labelM = baseTypography.labelM.copy(letterSpacing = spacingSp),
        labelS = baseTypography.labelS.copy(letterSpacing = spacingSp)
    )
    CompositionLocalProvider(
        LocalTypography provides adjustedTypography,
        LocalColor provides lightColorScheme.copy(
            accent = localUiSettings.color.color,
        ),
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