package com.point.auth.registration.ui.credentials

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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.point.auth.R
import com.point.auth.registration.presenter.credentials.CredentialsAction
import com.point.auth.registration.presenter.credentials.CredentialsAction.Action
import com.point.auth.registration.presenter.credentials.CredentialsState
import com.point.ui.Theme
import com.point.ui.textfields.EnvelopeOutlinedTextField

@Composable
fun CredentialsScreenContent(
    state: CredentialsState,
    onAction: (CredentialsAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    val scrollableState = rememberScrollState()
    Column(
        modifier = modifier
            .verticalScroll(scrollableState)
            .imePadding()
    ) {
        Spacer(modifier = Modifier.weight(0.1f))

        Text(
            text = stringResource(R.string.login_input_invite),
            style = Theme.typography.bodyL,
        )

        Spacer(modifier = Modifier.height(12.dp))

        EnvelopeOutlinedTextField(
            value = state.login,
            onValueChange = { onAction(Action.OnLoginInput(it)) },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                autoCorrectEnabled = false,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(R.string.password_input_invite),
            style = Theme.typography.bodyL,
        )

        Spacer(modifier = Modifier.height(12.dp))

        EnvelopeOutlinedTextField(
            value = state.password,
            onValueChange = { onAction(Action.OnPasswordInput(it)) },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                autoCorrectEnabled = false,
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Next
            ),
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(18.dp))

        Text(
            text = stringResource(R.string.repassword_input_invite),
            style = Theme.typography.bodyL,
        )

        Spacer(modifier = Modifier.height(12.dp))

        EnvelopeOutlinedTextField(
            value = state.passwordSecondInput,
            onValueChange = { onAction(Action.OnPasswordSecondInput(it)) },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                autoCorrectEnabled = false,
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done,
            ),
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.weight(1f))
    }
}