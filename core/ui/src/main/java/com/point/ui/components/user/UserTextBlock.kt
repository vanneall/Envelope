package com.point.ui.components.user

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.point.ui.colors.new.EnvelopeTheme
import com.point.ui.colors.new.Gray600
import com.point.ui.colors.new.NewEnvelopeTheme

@Composable
internal fun UserTextBlock(name: String, supportText: String?, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = name,
            style = EnvelopeTheme.typography.titleM,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )

        if (supportText != null) {
            Text(
                text = supportText,
                style = EnvelopeTheme.typography.bodyS,
                color = Gray600,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

@Preview
@Composable
private fun UserTextBlockPreview() {
    NewEnvelopeTheme {
        UserTextBlock(
            name = "User",
            supportText = "Some user description",
            modifier = Modifier.background(color = Color.White),
        )
    }
}

@Preview
@Composable
private fun UserTextBlockWithEmptySupportPreview() {
    NewEnvelopeTheme {
        UserTextBlock(
            name = "User",
            supportText = null,
            modifier = Modifier.background(color = Color.White),
        )
    }
}