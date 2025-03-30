package com.example.settings.main.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.settings.R
import com.example.settings.main.viewmodel.UserSettings
import com.point.ui.Theme

@Composable
fun UserSettings(userSettings: UserSettings, onClick: () -> Unit, modifier: Modifier = Modifier) {
    val interactionSource = remember { MutableInteractionSource() }
    val ripple = rememberRipple(color = Color.Black)
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier
            .clickable(
                interactionSource = interactionSource,
                indication = ripple,
                onClick = onClick
            )
            .padding(horizontal = 12.dp),
    ) {
        Icon(
            imageVector = userSettings.icon,
            contentDescription = null,
            tint = userSettings.iconColor,
            modifier = Modifier
                .size(38.dp)
                .background(color = userSettings.iconBackground, shape = RoundedCornerShape(12.dp))
                .padding(8.dp)
        )

        Text(
            text = stringResource(userSettings.textId),
            style = Theme.typography.bodyL,
            color = Theme.colorScheme.textPrimary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f),
        )

        if (userSettings.count != 0) {
            Text(
                text = userSettings.count.toString(),
                style = Theme.typography.bodyM,
                color = Theme.colorScheme.textSecondary,
            )
        }

        Icon(
            imageVector = Icons.Default.ArrowBackIosNew,
            contentDescription = null,
            tint = Theme.colorScheme.overlay,
            modifier = Modifier
                .rotate(180f)
                .size(16.dp),
        )
    }
}

@Preview
@Composable
private fun AppSettingsPreview() {
    val appSettings = UserSettings(
        textId = R.string.edit_profile_settings,
        iconBackground = Color.Blue,
        icon = Icons.Outlined.Edit,
        count = 0,
        iconColor = Color.Red
    )
    UserSettings(
        userSettings = appSettings,
        onClick = {},
        modifier = Modifier
            .height(60.dp)
            .fillMaxWidth()
            .background(color = Color.White)
            .padding(horizontal = 8.dp)
    )
}