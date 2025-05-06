package com.point.contacts.main.presenter.ui

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.point.ui.LocalUiSettings
import com.point.ui.Theme
import com.valentinilk.shimmer.LocalShimmerTheme
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer
import com.valentinilk.shimmer.shimmerSpec

@Composable
fun ContactComposableShimmer(modifier: Modifier = Modifier) {
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
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .background(color = Color.White)
            .padding(all = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .then(if (LocalUiSettings.current.useAnimations) Modifier.shimmer(shimmer) else Modifier)
                .size(54.dp)
                .background(color = Color.Gray, shape = CircleShape)
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = "",
                style = Theme.typography.bodyM,
                modifier = Modifier
                    .then(if (LocalUiSettings.current.useAnimations) Modifier.shimmer(shimmer) else Modifier)
                    .background(color = Color.Gray, shape = RoundedCornerShape(12.dp))
                    .width(165.dp)
            )

            Text(
                text = "",
                style = Theme.typography.bodyM,
                modifier = Modifier
                    .then(if (LocalUiSettings.current.useAnimations) Modifier.shimmer(shimmer) else Modifier)
                    .background(color = Color.Gray, shape = RoundedCornerShape(12.dp))
                    .width(100.dp)
            )
        }
    }
}