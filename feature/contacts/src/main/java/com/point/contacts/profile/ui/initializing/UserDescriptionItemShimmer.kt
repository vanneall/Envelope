package com.point.contacts.profile.ui.initializing

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
internal fun UserDescriptionItemShimmer(
    modifier: Modifier = Modifier,
    titleModifier: Modifier = Modifier,
    descriptionModifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier,
    ) {
        Text(
            text = "",
            modifier = titleModifier,
        )

        Text(
            text = "",
            modifier = descriptionModifier,
        )
    }
}