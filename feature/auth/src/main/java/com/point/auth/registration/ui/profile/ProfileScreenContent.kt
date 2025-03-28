package com.point.auth.registration.ui.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.point.auth.R
import com.point.auth.registration.presenter.profile.RegProfileAction
import com.point.auth.registration.presenter.profile.RegProfileState
import com.point.ui.Theme
import com.point.ui.textfields.EnvelopeOutlinedTextField

@Composable
fun ProfileScreenContent(
    state: RegProfileState,
    onAction: (RegProfileAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollableState = rememberScrollState()
    Column(
        modifier = modifier
            .verticalScroll(scrollableState)
            .imePadding()
    ) {
        Spacer(modifier = Modifier.weight(0.1f))

        Text(
            text = stringResource(R.string.name_input_invite),
            style = Theme.typography.bodyL,
        )

        Spacer(modifier = Modifier.height(12.dp))

        EnvelopeOutlinedTextField(
            value = state.name,
            onValueChange = { onAction(RegProfileAction.OnNameInput(it)) },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                autoCorrectEnabled = false,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            placeholder = {
                Text(text = stringResource(R.string.name_label))
            },
            maxSymbols = 30,
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(R.string.status_input_invite),
            style = Theme.typography.bodyL,
        )

        Spacer(modifier = Modifier.height(12.dp))

        EnvelopeOutlinedTextField(
            value = state.status,
            onValueChange = { onAction(RegProfileAction.OnStatusInput(it)) },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
                autoCorrectEnabled = true,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            placeholder = {
                Text(text = stringResource(R.string.status_label))
            },
            modifier = Modifier.fillMaxWidth(),
            minLines = 2,
            maxLines = 3,
        )

        Spacer(modifier = Modifier.height(18.dp))

        Text(
            text = stringResource(R.string.about_user_input_invite),
            style = Theme.typography.bodyL,
        )

        Spacer(modifier = Modifier.height(12.dp))

        EnvelopeOutlinedTextField(
            value = state.about,
            onValueChange = { onAction(RegProfileAction.OnAboutInput(it)) },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
                autoCorrectEnabled = true,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done,
            ),
            placeholder = {
                Text(text = stringResource(R.string.about_label))
            },
            modifier = Modifier.fillMaxWidth(),
            minLines = 2,
            maxLines = 20,
        )

        Spacer(modifier = Modifier.weight(1f))
    }
}

@Preview
@Composable
private fun ProfileScreenContentPreview() {
//    ProfileScreenContent(modifier = Modifier.fillMaxSize())
}