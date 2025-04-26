package com.example.settings.profile.ui

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.settings.R
import com.example.settings.profile.viewmodel.ProfileEditAction
import com.example.settings.profile.viewmodel.ProfileEditState
import com.point.ui.EnvelopeTheme
import com.point.ui.materials.textfields.EnvelopeTextField

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
            placeholder = painterResource(R.drawable.ic_person_default_24),
            error = painterResource(R.drawable.ic_person_error_24),
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

        EnvelopeTextField(
            value = state.name,
            onValueChange = { onAction(ProfileEditAction.OnNameEntered(it)) },
            modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(12.dp)),
            maxLines = 1,
            labelText = "Имя",
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                autoCorrect = false,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done,
            ),
        )

        Spacer(modifier = Modifier.height(28.dp))

        EnvelopeTextField(
            value = state.status,
            onValueChange = { onAction(ProfileEditAction.OnStatusEntered(it)) },
            modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(12.dp)),
            maxLines = 1,
            labelText = "Статус",
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                autoCorrect = false,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done,
            ),
        )

        Spacer(modifier = Modifier.height(28.dp))

        EnvelopeTextField(
            value = state.about,
            onValueChange = { onAction(ProfileEditAction.OnAboutEntered(it)) },
            modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(12.dp)),
            maxLines = 1,
            labelText = "О себе",
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
                autoCorrect = false,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done,
            ),
        )
    }
}

@Preview(locale = "ru")
@Composable
private fun ContentPreview() {
    EnvelopeTheme {
        ProfileEditScreenContent(
            state = ProfileEditState(
                name = "User",
                status = "Status",
                about = "About",
            ),
            onAction = {},
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White)
                .padding(horizontal = 10.dp)
        )
    }
}