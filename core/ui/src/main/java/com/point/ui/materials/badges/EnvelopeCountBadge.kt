package com.point.ui.materials.badges

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.point.ui.colors.new.EnvelopeTheme
import com.point.ui.colors.new.NewEnvelopeTheme

@Composable
fun EnvelopeCountBadge(count: Int, modifier: Modifier = Modifier, max: Int = 100) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .background(
                color = EnvelopeTheme.colorScheme.tertiary,
                shape = CircleShape
            )
            .padding(all = 4.dp)
    ) {
        Text(
            text = if (count >= max) "${max - 1}+" else count.toString(),
            style = EnvelopeTheme.typography.labelS,
            color = EnvelopeTheme.colorScheme.onPrimary,
        )
    }
}

@Preview
@Composable
private fun EnvelopeCountBadgePreview() {
    NewEnvelopeTheme {
        EnvelopeCountBadge(count = 10)
    }
}

@Preview
@Composable
private fun EnvelopeCountWithMaxBadgePreview() {
    NewEnvelopeTheme {
        EnvelopeCountBadge(count = 100, max = 100)
    }
}