package com.example.settings.profile.ui

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.settings.R
import com.example.settings.profile.viewmodel.ProfileEditAction
import com.example.settings.profile.viewmodel.ProfileEditState
import com.point.ui.EnvelopeTheme
import com.point.ui.Theme
import com.point.ui.materials.textfields.EnvelopeTextField
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
internal fun ProfileEditScreenContent(
    state: ProfileEditState,
    onAction: (ProfileEditAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val imagePickerLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickVisualMedia(),
        ) { uri ->
            uri?.let { onAction(ProfileEditAction.PickPhoto(it)) }
        }

        AsyncImage(
            model = state.initialPhotoUrl ?: state.photoUri,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            placeholder = painterResource(R.drawable.ic_person_default_24),
            error = painterResource(R.drawable.ic_person_error_24),
            modifier = modifier
                .height(300.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(color = Theme.colorScheme.accent)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(),
                    onClick = {
                        imagePickerLauncher.launch(
                            PickVisualMediaRequest(mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    }
                ),
        )

        Spacer(Modifier.height(40.dp))

        EnvelopeTextField(
            value = state.name,
            onValueChange = { onAction(ProfileEditAction.OnNameEntered(it)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
                .clip(RoundedCornerShape(12.dp)),
            maxLines = 1,
            labelText = stringResource(R.string.name),
            placeholder = stringResource(R.string.enter_name),
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
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
                .clip(RoundedCornerShape(12.dp)),
            maxLines = 1,
            labelText = stringResource(R.string.status),
            placeholder = stringResource(R.string.enter_status),
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
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
                .clip(RoundedCornerShape(12.dp)),
            maxLines = 1,
            labelText = stringResource(R.string.about),
            placeholder = stringResource(R.string.enter_about),
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
                autoCorrect = false,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done,
            ),
        )

        Spacer(modifier = Modifier.height(28.dp))

        EditableDateField(
            initialDate = state.date,
            onDateSelected = { onAction(ProfileEditAction.PickDate(it)) }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditableDateField(
    label: String = "Дата рождения",
    initialDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit
) {
    var selectedDate by remember(initialDate) { mutableStateOf(initialDate) }
    val formatter = remember { DateTimeFormatter.ofPattern("dd.MM.yyyy") }

    val datePickerState = rememberDatePickerState()
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        DatePickerDialog(
            colors = DatePickerDefaults.colors(
                containerColor = Theme.colorScheme.background,
            ),
            onDismissRequest = { showDialog = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        selectedDate = Instant.ofEpochMilli(millis)
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate()
                        onDateSelected(selectedDate)
                    }
                    showDialog = false
                }) {
                    Text("ОК", color = Theme.colorScheme.accent)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Отмена", color = Theme.colorScheme.accent)
                }
            }
        ) {
            DatePicker(
                state = datePickerState,
                colors = DatePickerDefaults.colors(
                    todayDateBorderColor = Theme.colorScheme.accent,
                    selectedDayContentColor = Color.White,
                    dayContentColor = Theme.colorScheme.primary,
                    dividerColor = Theme.colorScheme.primary,
                    selectedDayContainerColor = Theme.colorScheme.accent,
                )
            )
        }
    }

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 10.dp)) {
        Text(
            text = label,
            style = Theme.typography.bodyM,
            modifier = Modifier.padding(start = 8.dp, bottom = 4.dp)
        )

        Surface(
            shape = RoundedCornerShape(12.dp),
            color = Color(0xFFF5F5F5),
            modifier = Modifier
                .fillMaxWidth()
                .clickable { showDialog = true }
                .padding(horizontal = 0.dp, vertical = 0.dp)
        ) {
            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = selectedDate.format(formatter) ?: "Выбрать дату",
                    color = Theme.colorScheme.textSecondary,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector = Icons.Rounded.Edit,
                    contentDescription = "Изменить",
                    tint = Color.Gray
                )
            }
        }
    }
}


@Composable
fun SelectedDateText(date: LocalDate?) {
    val formatter = remember {
        DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale("ru"))
    }

    Text(
        text = date?.format(formatter) ?: "Дата не выбрана",
        style = Theme.typography.bodyM
    )
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