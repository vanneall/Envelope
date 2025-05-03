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
import androidx.compose.material.icons.filled.ArrowBackIosNew
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
import androidx.compose.ui.unit.dp
import com.example.settings.main.viewmodel.AppSettings
import com.point.ui.Theme

@Composable
fun AppSettings(appSettings: AppSettings, onClick: () -> Unit, modifier: Modifier = Modifier) {
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
            imageVector = appSettings.icon,
            contentDescription = null,
            tint = appSettings.iconColor,
            modifier = Modifier
                .size(38.dp)
                .background(color = appSettings.iconBackground, shape = RoundedCornerShape(12.dp))
                .padding(8.dp)
        )

        Text(
            text = stringResource(appSettings.textId),
            style = Theme.typography.bodyL,
            color = Theme.colorScheme.textPrimary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f),
        )

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