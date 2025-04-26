package com.point.ui.badges

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.point.ui.colors.new.EnvelopeTheme
import com.point.ui.colors.new.NewEnvelopeTheme

@Composable
fun EnvelopeBadge(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(size = 24.dp)
            .background(
                color = EnvelopeTheme.colorScheme.tertiary,
                shape = CircleShape
            )
    )
}

@Preview
@Composable
private fun EnvelopeBadgePreview() {
    NewEnvelopeTheme {
        EnvelopeBadge()
    }
}