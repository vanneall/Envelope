package com.point.ui.components.user

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.point.ui.R

@Composable
fun UserPhoto(uri: Any?, modifier: Modifier = Modifier, contentDescription: String? = null) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current.applicationContext)
            .data(uri)
            .crossfade(true)
            .placeholder(R.drawable.ic_person_default_24)
            .error(R.drawable.ic_person_default_24)
            .fallback(R.drawable.ic_person_default_24)
            .build(),
        contentDescription = contentDescription,
        contentScale = ContentScale.Crop,
        modifier = modifier,
    )
}