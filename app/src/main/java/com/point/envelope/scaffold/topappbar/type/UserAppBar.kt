package com.point.envelope.scaffold.topappbar.type

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.point.ui.Theme

@Composable
fun UserAppBar(name: String, photo: String?, modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    ) {
        AsyncImage(
            model = photo ?: DEFAULT_IMAGE_URL,
            contentDescription = null,
            modifier = Modifier.clip(CircleShape)
        )

        Text(
            text = name,
            style = Theme.typography.bodyL,
            color = Theme.colorScheme.textPrimary,
        )
    }
}

const val DEFAULT_IMAGE_URL = "https://i.pinimg.com/originals/32/b0/ad/32b0adaf073e4e17c4d36301047edb75.jpg"