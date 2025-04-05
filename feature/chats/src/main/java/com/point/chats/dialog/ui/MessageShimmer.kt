package com.point.chats.dialog.ui

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.valentinilk.shimmer.LocalShimmerTheme
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer
import com.valentinilk.shimmer.shimmerSpec

@Composable
fun MessageShimmer(modifier: Modifier = Modifier) {
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
        modifier = modifier.padding(horizontal = 8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
        widths.forEach {
            Row(
                modifier = Modifier
                    .shimmer(shimmer)
                    .width(it)
                    .height(28.dp)
                    .background(color = Color.Gray, shape = RoundedCornerShape(12.dp))
            ) { }
        }
    }
}

val widths = listOf(
    45.dp,
    120.dp,
    90.dp,
    100.dp,
    170.dp,
    50.dp,
    140.dp,
    170.dp,
    45.dp,
    120.dp,
    90.dp,
    100.dp,
    170.dp,
    50.dp,
    140.dp,
    170.dp,
)