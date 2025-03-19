package com.example.settings.main.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.settings.R
import com.example.settings.main.viewmodel.UserData
import com.point.ui.Theme

@Composable
fun UserAvatar(userData: UserData, modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        AsyncImage(
            model = userData.url,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            placeholder = painterResource(R.drawable.ic_person_default_24),
            error = painterResource(R.drawable.ic_person_error_24),
            modifier = Modifier.size(120.dp).clip(CircleShape).background(color = Theme.colorScheme.accent),
        )
        
        Spacer(modifier = Modifier.height(12.dp))
        
        Text(
            text = userData.name,
            style = Theme.typography.headlineM,
            color = Theme.colorScheme.textPrimary,
            maxLines = 1,
        )

        Text(
            text = userData.username,
            style = Theme.typography.bodyS,
            color = Theme.colorScheme.textSecondary,
            maxLines = 1,
        )
    }
}

@Preview
@Composable
private fun UserAvatarPreview() {
    val userData = UserData(
        name = "Daniil",
        username = "@skylejke",
        url = DEFAULT_IMAGE_URL,
    )
    UserAvatar(userData, modifier = Modifier.background(Color.White))
}

const val DEFAULT_IMAGE_URL = "https://i.pinimg.com/originals/32/b0/ad/32b0adaf073e4e17c4d36301047edb75.jpg"