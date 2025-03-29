package com.point.contacts.profile.ui.content

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import com.point.contacts.R
import com.point.ui.Theme

@Composable
internal fun UserAvatar(url: String?, modifier: Modifier = Modifier) {
    AsyncImage(
        model = url ?: DEFAULT_IMAGE_URL,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        placeholder = painterResource(R.drawable.ic_person_default_24),
        error = painterResource(R.drawable.ic_person_error_24),
        modifier = modifier.clip(CircleShape).background(color = Theme.colorScheme.accent),
    )
}

const val DEFAULT_IMAGE_URL = "https://i.pinimg.com/originals/32/b0/ad/32b0adaf073e4e17c4d36301047edb75.jpg"