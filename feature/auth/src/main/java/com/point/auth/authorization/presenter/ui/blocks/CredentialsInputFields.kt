package com.point.auth.authorization.presenter.ui.blocks

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.point.auth.R
import com.point.auth.authorization.presenter.viewmodel.AuthAction
import com.point.auth.authorization.presenter.viewmodel.AuthAction.Action.OnPasswordInput
import com.point.auth.authorization.presenter.viewmodel.AuthState
import com.point.ui.Theme
import com.point.ui.textfields.EnvelopeTextField
import kotlin.time.Duration.Companion.milliseconds

@Composable
internal fun CredentialsInputFields(state: AuthState, onAction: (AuthAction) -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        EnvelopeTextField(
            value = state.login,
            onValueChange = { value -> onAction(AuthAction.Action.OnLoginInput(value)) },
            singleLine = true,
            maxLines = 1,
            debounce = 100.milliseconds,
            isError = state.isLoginInvalid,
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
            ),
            supportingText = state.loginInvalidReason?.let { stringResource(it) },
            labelText = stringResource(R.string.login_label)
        )

        var isPasswordVisible by remember { mutableStateOf(false) }
        EnvelopeTextField(
            value = state.password,
            onValueChange = { value -> onAction(OnPasswordInput(value)) },
            singleLine = true,
            maxLines = 1,
            debounce = 100.milliseconds,
            isError = state.isPasswordInvalid,
            modifier = Modifier
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
            supportingText = state.passwordInvalidReason?.let { stringResource(it) },
            labelText = stringResource(R.string.password_label)
        )

        ForgotPasswordText(modifier = Modifier.fillMaxWidth())
    }
}

@Composable
private fun ForgotPasswordText(modifier: Modifier = Modifier) {
    Box(modifier = modifier, contentAlignment = Alignment.CenterEnd) {
        Text(
            text = stringResource(R.string.forget_password),
            color = Theme.colorScheme.accent,
            style = Theme.typography.labelL,
        )
    }
}
