package com.point.contacts.profile.ui.initializing

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import com.point.ui.LocalUiSettings
import com.valentinilk.shimmer.Shimmer
import com.valentinilk.shimmer.shimmer

@Composable
internal fun UserAvatarShimmer(
    shimmer: Shimmer,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .then(if (LocalUiSettings.current.useAnimations) Modifier.shimmer(shimmer) else Modifier)
            .clip(CircleShape)
            .background(color = Color.Gray),
    )
}