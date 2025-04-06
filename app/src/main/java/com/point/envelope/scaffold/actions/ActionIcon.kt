package com.point.envelope.scaffold.actions

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun ActionIcon(
    icon: ImageVector,
    action: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val interactionSource = remember { MutableInteractionSource() }
    Icon(
        imageVector = icon,
        contentDescription = null,
        modifier = modifier
            .size(40.dp)
            .clip(CircleShape)
            .clickable(
                interactionSource = interactionSource,
                indication = rememberRipple(color = Color.Black),
                onClick = action
            )
            .padding(8.dp)
    )
}