package com.point.contacts.profile.ui.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.point.ui.Theme

@Composable
internal fun UserDescriptionItem(
    title: String,
    description: String,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier,
    ) {
        Text(
            text = title,
            style = Theme.typography.bodyM,
            color = Theme.colorScheme.textSecondary,
        )

        Text(
            text = description,
            style = Theme.typography.bodyL,
            color = Theme.colorScheme.textPrimary,
        )
    }
}