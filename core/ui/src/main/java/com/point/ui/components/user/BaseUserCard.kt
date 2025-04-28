package com.point.ui.components.user

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
internal fun BaseUserCard(
    user: UserBase,
    modifier: Modifier = Modifier,
    supporting: (@Composable () -> Unit)? = null,
    trailing: (@Composable () -> Unit)? = null,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        UserPhoto(user.photo, modifier = Modifier.size(68.dp))

        UserTextBlock(
            name = user.name,
            supporting = supporting,
            modifier = Modifier.weight(1f)
        )

        trailing?.invoke()
    }
}

data class UserBase(val name: String, val photo: String? = null)