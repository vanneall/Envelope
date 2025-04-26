package com.point.auth.registration.ui.credentials

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.point.auth.R
import com.point.auth.registration.presenter.credentials.CredentialsAction
import com.point.auth.registration.presenter.credentials.CredentialsState
import com.point.ui.materials.textfields.EnvelopeTextField
import kotlin.time.Duration.Companion.milliseconds

@Composable
internal fun UserCredentialsInputBlock(
    state: CredentialsState,
    onAction: (CredentialsAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        UsernameInput(
            username = state.login,
            onUsernameEntered = { onAction(CredentialsAction.Action.OnLoginInput(it)) },
            modifier = Modifier.fillMaxWidth(),
        )

        PasswordInput(
            password = state.password,
            onPasswordInput = { onAction(CredentialsAction.Action.OnPasswordInput(it)) },
            label = stringResource(R.string.password_label),
            modifier = Modifier.fillMaxWidth(),
        )

        PasswordInput(
            password = state.passwordSecondInput,
            onPasswordInput = { onAction(CredentialsAction.Action.OnPasswordSecondInput(it)) },
            label = stringResource(R.string.repassword_input_invite),
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
private fun UsernameInput(
    username: String,
    onUsernameEntered: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    EnvelopeTextField(
        value = username,
        onValueChange = { value -> onUsernameEntered(value) },
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
        labelText = stringResource(R.string.login_label)
    )
}

@Composable
private fun PasswordInput(
    password: String,
    onPasswordInput: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
) {
    var isPasswordVisible by remember { mutableStateOf(false) }
    EnvelopeTextField(
        value = password,
        onValueChange = { value -> onPasswordInput(value) },
        singleLine = true,
        maxLines = 1,
        debounce = 100.milliseconds,
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .fillMaxWidth(),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Password,
        ),
        trailingIcon = {
            Icon(
                imageVector = if (isPasswordVisible) {
                    Icons.Default.Visibility
                } else {
                    Icons.Default.VisibilityOff
                },
                contentDescription = null,
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .clickable { isPasswordVisible = !isPasswordVisible }
                    .padding(4.dp))
        },
        visualTransformation = if (isPasswordVisible) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
        labelText = label
    )
}