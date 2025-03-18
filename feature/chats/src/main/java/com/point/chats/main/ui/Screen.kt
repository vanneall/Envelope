package com.point.chats.main.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.point.chats.R
import com.point.chats.main.viewmodel.Chat
import com.point.ui.Theme

@Composable
fun ChatComposable(chat: Chat, modifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .background(color = Color.White)
            .padding(all = 8.dp),
    ) {
        UserPhoto(modifier = Modifier.size(54.dp))

        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
        ) {
            Text(
                text = chat.name,
                style = Theme.typography.titleM,
                color = Theme.colorScheme.textPrimary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )

            Text(
                text = chat.lastMessage,
                style = Theme.typography.bodyM,
                color = Theme.colorScheme.textSecondary,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

@Composable
fun UserPhoto(modifier: Modifier = Modifier) {
    AsyncImage(
        model = DEFAULT_IMAGE_URL,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        placeholder = painterResource(R.drawable.ic_person_default_24),
        error = painterResource(R.drawable.ic_person_error_24),
        modifier = modifier.clip(CircleShape),
    )
}

@Preview
@Composable
fun UserPhotoPreview() {
    UserPhoto(modifier = Modifier.size(54.dp))
}

const val DEFAULT_IMAGE_URL = "https://i.pinimg.com/originals/32/b0/ad/32b0adaf073e4e17c4d36301047edb75.jpg"