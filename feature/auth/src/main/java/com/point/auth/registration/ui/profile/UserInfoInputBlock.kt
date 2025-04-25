package com.point.auth.registration.ui.profile

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.point.auth.R
import com.point.auth.registration.presenter.profile.RegProfileAction.UiAction
import com.point.auth.registration.presenter.profile.RegProfileState
import com.point.ui.Theme
import com.point.ui.textfields.EnvelopeTextField
import kotlin.time.Duration.Companion.milliseconds

@Composable
internal fun UserInfoInputBlock(
    state: RegProfileState,
    onAction: (UiAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {

        UserPhoto(
            uri = state.uri,
            onPhotoSelected = { onAction(UiAction.OnPhotoPicked(it)) },
            modifier = Modifier.fillMaxWidth(),
        )

        NameInput(
            name = state.name,
            onNameEntered = { onAction(UiAction.OnNameInput(it)) },
            modifier = Modifier.fillMaxWidth(),
        )

        StatusInput(
            status = state.status,
            onStatusEntered = { onAction(UiAction.OnStatusInput(it)) },
            modifier = Modifier.fillMaxWidth(),
        )

        AboutInput(
            about = state.about,
            onAboutEntered = { onAction(UiAction.OnAboutInput(it)) },
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
private fun UserPhoto(
    uri: Uri?,
    onPhotoSelected: (Uri?) -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {

        val photoPicker = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            onPhotoSelected(uri)
        }

        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(uri)
                .crossfade(true)
                .placeholder(com.point.ui.R.drawable.ic_person_default_24)
                .fallback(com.point.ui.R.drawable.ic_person_default_24)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(164.dp)
                .clip(CircleShape)
                .background(color = Theme.colorScheme.surface)
                .clickable {
                    photoPicker.launch(
                        PickVisualMediaRequest(mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                },
        )
    }
}

@Composable
private fun NameInput(
    name: String,
    onNameEntered: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    EnvelopeTextField(
        value = name,
        onValueChange = { value -> onNameEntered(value) },
        singleLine = true,
        maxLines = 1,
        debounce = 100.milliseconds,
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .fillMaxWidth(),
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Words,
            autoCorrectEnabled = false,
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        ),
        labelText = stringResource(R.string.name_label)
    )
}

@Composable
private fun StatusInput(
    status: String,
    onStatusEntered: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    EnvelopeTextField(
        value = status,
        onValueChange = { value -> onStatusEntered(value) },
        maxLines = 3,
        debounce = 100.milliseconds,
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .fillMaxWidth(),
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Sentences,
            autoCorrectEnabled = true,
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        ),
        labelText = stringResource(R.string.status_label)
    )
}

@Composable
private fun AboutInput(
    about: String,
    onAboutEntered: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    EnvelopeTextField(
        value = about,
        onValueChange = { value -> onAboutEntered(value) },
        maxLines = 3,
        debounce = 100.milliseconds,
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .fillMaxWidth(),
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Sentences,
            autoCorrectEnabled = true,
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done,
        ),
        labelText = stringResource(R.string.about_label)
    )
}