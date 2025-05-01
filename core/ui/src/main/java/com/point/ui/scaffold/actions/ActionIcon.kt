package com.point.ui.scaffold.actions

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun ActionIcon(
    icon: ImageVector,
    action: () -> Unit,
    modifier: Modifier = Modifier,
) {
    IconButton(
        onClick = action,
        modifier = modifier,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.padding(8.dp)
        )
    }
}