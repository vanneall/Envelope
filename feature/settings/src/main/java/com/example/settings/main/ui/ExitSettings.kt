package com.example.settings.main.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.settings.R
import com.example.settings.main.viewmodel.ExitSettings
import com.example.settings.main.viewmodel.SettingsAction
import com.point.ui.Theme

@Composable
fun ExitSettings(
    exitSettings: ExitSettings,
    onClick: (SettingsAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val ripple = rememberRipple(color = Color.Black)
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable(
                interactionSource = interactionSource,
                indication = ripple,
                onClick = { onClick(SettingsAction.Action.LeftFromAccount) }
            )
            .padding(horizontal = 12.dp),
    ) {
        Icon(
            imageVector = exitSettings.icon,
            contentDescription = null,
            tint = exitSettings.iconColor,
            modifier = Modifier
                .size(38.dp)
                .background(color = exitSettings.iconBackground, shape = RoundedCornerShape(12.dp))
                .padding(8.dp)
        )

        Text(
            text = stringResource(exitSettings.textId),
            style = Theme.typography.bodyL,
            color = Theme.colorScheme.error,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f),
        )
    }
}

@Preview
@Composable
private fun ExitSettingsPreview() {
    val appSettings = ExitSettings(
        textId = R.string.exit_settings,
        iconBackground = Color.Red,
        icon = Icons.Outlined.Logout,
        iconColor = Color.White,
    )
}