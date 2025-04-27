package com.point.ui.colors.new

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.staticCompositionLocalOf
import com.point.settings.AppUiSettings
import com.point.ui.EnvelopeTypography
import com.point.ui.LocalUiSettings
import com.point.ui.LocalUser
import com.point.user.LocalUser

@Composable
fun NewEnvelopeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    localUser: LocalUser? = null,
    localUiSettings: AppUiSettings = AppUiSettings(),
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        NewLocalTypography provides EnvelopeTypography(),
        NewLocalColor provides LightColorTheme,
        LocalUser provides localUser,
        LocalUiSettings provides localUiSettings,
        content = content,
    )
}

object EnvelopeTheme {

    val colorScheme: ColorTheme
        @Composable
        @ReadOnlyComposable
        get() = NewLocalColor.current

    val typography: EnvelopeTypography
        @Composable
        @ReadOnlyComposable
        get() = NewLocalTypography.current


    internal var envelopeDefaultTextFieldColors: TextFieldColors? = null

    internal var envelopePrimaryButtonColors: ButtonColors? = null
    internal var envelopeSecondaryButtonColors: ButtonColors? = null

    internal var envelopeIconFilledButton: IconButtonColors? = null

    internal var envelopeSwitch: SwitchColors? = null

}

internal val NewLocalColor = compositionLocalOf { LightColorTheme }

internal val NewLocalTypography = staticCompositionLocalOf { EnvelopeTypography() }