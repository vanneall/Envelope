package com.point.auth.authorization.presenter.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.point.auth.R
import com.point.auth.authorization.presenter.viewmodel.AuthAction
import com.point.auth.authorization.presenter.viewmodel.AuthAction.Action.Authorization
import com.point.auth.authorization.presenter.viewmodel.AuthAction.Action.OnPasswordInput
import com.point.auth.authorization.presenter.viewmodel.AuthEvent
import com.point.auth.authorization.presenter.viewmodel.AuthState
import com.point.navigation.Route
import com.point.ui.Theme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch

@Composable
internal fun AuthorizationScreenContent(
    state: AuthState,
    onAction: (AuthAction) -> Unit,
    events: Flow<AuthEvent>,
    onNavigate: (Route) -> Unit,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(Unit) {
        launch {
            events.collect {
                onNavigate(Route.ChatsFeature.Chats)
            }
        }
    }

    val scrollableState = rememberScrollState()
    Column(
        modifier = modifier
            .verticalScroll(scrollableState)
            .imePadding()
    ) {
        Spacer(modifier = Modifier.weight(0.5f))

        val login = remember { mutableStateOf(state.login) }
        CredentialsTextField(
            value = login.value,
            onValueChanged = { login.value = it },
            onClear = { login.value = "" },
            label = R.string.login_label,
            isError = state.isInvalidCredentials,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                autoCorrect = false,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
            ),
            modifier = Modifier.fillMaxWidth()
        )

        LaunchedEffect(Unit) {
            snapshotFlow { login.value }
                .debounce(200)
                .collect { text -> onAction(AuthAction.Action.OnLoginInput(text)) }
        }


        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
        )

        var password by remember { mutableStateOf(state.login) }
        CredentialsTextField(
            value = password,
            onValueChanged = { password = it },
            onClear = { password = "" },
            label = R.string.password_label,
            isError = state.isInvalidCredentials,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                autoCorrect = false,
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done,
            ),
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        LaunchedEffect(Unit) {
            snapshotFlow { password }
                .debounce(200)
                .collect { text -> onAction(OnPasswordInput(text)) }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = stringResource(R.string.forget_password),
            color = Theme.colorScheme.accent,
            fontSize = 12.sp,
            modifier = Modifier.padding(start = 4.dp)
        )

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
        )

        Spacer(modifier = Modifier.weight(0.5f))

        Button(
            onClick = { onAction(Authorization) },
            colors = ButtonDefaults.buttonColors().copy(
                containerColor = Color.Black,
                contentColor = Color.White,
                disabledContentColor = Color.Black,
                disabledContainerColor = Color.White,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .imePadding()
                .height(52.dp)
        ) {
            Text(text = stringResource(R.string.authorize_button), fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = { onNavigate(Route.AuthFeature.Registration) },
            colors = ButtonDefaults.buttonColors().copy(
                containerColor = Theme.colorScheme.surface,
                contentColor = Color.Black,
                disabledContentColor = Color.Black,
                disabledContainerColor = Theme.colorScheme.disabled,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
        ) {
            Text(text = stringResource(R.string.registration_button), fontSize = 16.sp)
        }
    }
}

@Composable
internal fun CredentialsTextField(
    value: String,
    onValueChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    enabled: Boolean = true,
    onClear: (() -> Unit) = {},
    @StringRes label: Int? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
) {

    OutlinedTextField(
        value = value,
        onValueChange = onValueChanged,
        modifier = modifier,
        textStyle = Theme.typography.bodyL,
        enabled = enabled,
        isError = isError,
        trailingIcon = {
            Column {
                if (value.isNotEmpty()) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = null,
                        modifier = Modifier
                            .size(16.dp)
                            .clip(CircleShape)
                            .clickable { onClear() }
                    )
                }
            }
        },
        singleLine = true,
        shape = RoundedCornerShape(20.dp),
        colors = OutlinedTextFieldDefaults.colors().copy(
            focusedContainerColor = Theme.colorScheme.surface,
            unfocusedContainerColor = Theme.colorScheme.surface,
            errorContainerColor = Theme.colorScheme.surface,
            disabledContainerColor = Theme.colorScheme.surface,
            focusedIndicatorColor = Theme.colorScheme.accent,
            errorIndicatorColor = Theme.colorScheme.error,
            disabledIndicatorColor = Theme.colorScheme.accent,
            unfocusedIndicatorColor = Theme.colorScheme.accent,
            unfocusedLabelColor = Theme.colorScheme.accent,
            focusedLabelColor = Theme.colorScheme.accent,
            errorLabelColor = Theme.colorScheme.error,
            errorTrailingIconColor = Theme.colorScheme.error,
            errorCursorColor = Theme.colorScheme.error,
            cursorColor = Theme.colorScheme.accent,
        ),
        label = {
            if (label != null) {
                Text(
                    text = stringResource(label),
                    modifier = Modifier.background(Color.Transparent)
                )
            }
        },
        keyboardOptions = keyboardOptions,
        visualTransformation = visualTransformation,
    )
}
