package com.example.settings.profile.ui

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.settings.profile.viewmodel.ProfileEditAction
import com.example.settings.profile.viewmodel.ProfileEditState
import com.point.ui.Theme

@Composable
internal fun ProfileEditScreenContent(
    state: ProfileEditState,
    onAction: (ProfileEditAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        val imagePickerLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickVisualMedia(),
        ) { uri ->
            uri?.let { onAction(ProfileEditAction.PickPhoto(it)) }
        }

        val context = LocalContext.current

        AsyncImage(
            model = if (state.photoUri != null) {
                ImageRequest.Builder(context)
                    .data(state.photoUri)
                    .crossfade(true)
                    .build()
            } else {
                state.initialPhotoUrl
            },
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(size = 128.dp)
                .clip(CircleShape)
                .clickable {
                    imagePickerLauncher.launch(
                        PickVisualMediaRequest(
                            mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly,
                        )
                    )
                }
        )

        Text(
            text = "Ваше имя",
            style = Theme.typography.bodyL,
            color = Theme.colorScheme.accent,
        )

        Spacer(modifier = Modifier.height(12.dp))

        TextField(
            value = state.name,
            onValueChange = { onAction(ProfileEditAction.OnNameEntered(it)) },
            maxLines = 1,
            textStyle = Theme.typography.bodyM,
            placeholder = {
                Text(
                    text = "Имя",
                    style = Theme.typography.bodyM,
                    color = Theme.colorScheme.textSecondary,
                )
            },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                autoCorrect = false,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done,
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(28.dp))

        Text(
            text = "Статус",
            style = Theme.typography.bodyL,
            color = Theme.colorScheme.accent,
        )

        Spacer(modifier = Modifier.height(12.dp))

        TextField(
            value = state.status,
            onValueChange = { onAction(ProfileEditAction.OnStatusEntered(it)) },
            maxLines = 1,
            textStyle = Theme.typography.bodyM,
            placeholder = {
                Text(
                    text = "Статус",
                    style = Theme.typography.bodyM,
                    color = Theme.colorScheme.textSecondary,
                )
            },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                autoCorrect = false,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done,
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(28.dp))

        Text(
            text = "О себе",
            style = Theme.typography.bodyL,
            color = Theme.colorScheme.accent,
        )

        Spacer(modifier = Modifier.height(12.dp))

        TextField(
            value = state.about,
            onValueChange = { onAction(ProfileEditAction.OnAboutEntered(it)) },
            textStyle = Theme.typography.bodyM,
            placeholder = {
                Text(
                    text = "О себе",
                    style = Theme.typography.bodyM,
                    color = Theme.colorScheme.textSecondary,
                )
            },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                autoCorrect = false,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done,
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }
}