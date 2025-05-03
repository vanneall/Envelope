package com.point.contacts.profile.ui.initializing

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.point.ui.LocalUiSettings
import com.valentinilk.shimmer.LocalShimmerTheme
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer
import com.valentinilk.shimmer.shimmerSpec

@Composable
internal fun ProfileScreenContentShimmer(modifier: Modifier = Modifier) {
    val shimmer = rememberShimmer(
        shimmerBounds = ShimmerBounds.Window,
        theme = LocalShimmerTheme.current.copy(
            animationSpec = infiniteRepeatable(
                animation = shimmerSpec(
                    durationMillis = 1000,
                    easing = LinearEasing,
                    delayMillis = 300,
                ),
                repeatMode = RepeatMode.Restart,
            )
        )
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier,
    ) {
        UserAvatarShimmer(
            shimmer = shimmer,
            modifier = Modifier.size(120.dp)
        )

        repeat(2) {
            UserDescriptionItemShimmer(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                titleModifier = Modifier
                    .then(if (LocalUiSettings.current.useAnimations) Modifier.shimmer(shimmer) else Modifier)
                    .width(140.dp)
                    .background(color = Color.Gray, shape = RoundedCornerShape(12.dp)),
                descriptionModifier = Modifier
                    .then(if (LocalUiSettings.current.useAnimations) Modifier.shimmer(shimmer) else Modifier)
                    .width(100.dp)
                    .background(color = Color.Gray, shape = RoundedCornerShape(12.dp))
            )
        }

        UserDescriptionItemShimmer(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp), titleModifier = Modifier
                .then(if (LocalUiSettings.current.useAnimations) Modifier.shimmer(shimmer) else Modifier)
                .width(140.dp)
                .background(color = Color.Gray, shape = RoundedCornerShape(12.dp)),
            descriptionModifier = Modifier
                .then(if (LocalUiSettings.current.useAnimations) Modifier.shimmer(shimmer) else Modifier)
                .width(280.dp)
                .height(60.dp)
                .background(color = Color.Gray, shape = RoundedCornerShape(12.dp))
        )

        UserDescriptionItemShimmer(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp), titleModifier = Modifier
                .then(if (LocalUiSettings.current.useAnimations) Modifier.shimmer(shimmer) else Modifier)
                .width(140.dp)
                .background(color = Color.Gray, shape = RoundedCornerShape(12.dp)),
            descriptionModifier = Modifier
                .then(if (LocalUiSettings.current.useAnimations) Modifier.shimmer(shimmer) else Modifier)
                .fillMaxWidth()
                .height(140.dp)
                .background(color = Color.Gray, shape = RoundedCornerShape(12.dp))
        )
    }
}