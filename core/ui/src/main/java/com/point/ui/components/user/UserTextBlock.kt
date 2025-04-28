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
internal fun UserTextBlock(name: String, modifier: Modifier = Modifier, supporting: (@Composable () -> Unit)? = null) {
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

        supporting?.invoke()
    }
}

@Preview
@Composable
private fun UserTextBlockPreview() {
    NewEnvelopeTheme {
        UserTextBlock(
            name = "User",
            supporting = {
                Text(
                    text = "Some user description",
                    style = EnvelopeTheme.typography.bodyS,
                    color = Gray600,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                )
            },
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
            modifier = Modifier.background(color = Color.White),
        )
    }
}