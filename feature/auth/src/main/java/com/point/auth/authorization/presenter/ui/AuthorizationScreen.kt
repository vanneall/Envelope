package com.point.auth.authorization.presenter.ui

import android.widget.Space
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.point.auth.R
import com.point.auth.authorization.presenter.mvi.AuthAction
import com.point.auth.authorization.presenter.mvi.AuthState
import com.point.ui.TextFieldContainerColor
import com.point.ui.TextFieldLabelColor
import com.point.auth.authorization.presenter.mvi.AuthAction.Action.*
import com.point.ui.AccentColor
import com.point.ui.AlertColor

@Composable
fun AuthorizationScreen(
    state: AuthState,
    onAction: (AuthAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(horizontal = 20.dp)
            .padding(bottom = 20.dp, top = 40.dp)
            .imePadding(),
    ) {
        Text(
            text = stringResource(R.string.authorization_title),
            style = TextStyle(
                color = Color.Black,
                fontSize = 32.sp,
                fontWeight = FontWeight.SemiBold,
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
        )

        CredentialsTextField(
            value = state.login,
            onValueChanged = { onAction(OnLoginInput(it)) },
            onClear = { onAction(OnLoginClear) },
            label = { Text(stringResource(R.string.login_label)) },
            isError = state.isInvalidCredentials,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                autoCorrect = false,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp)
        )

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
        )

        CredentialsTextField(
            value = state.login,
            onValueChanged = { onAction(OnPasswordInput(it)) },
            onClear = { onAction(OnPasswordClear) },
            label = { Text(stringResource(R.string.password_label)) },
            isError = state.isInvalidCredentials,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                autoCorrect = false,
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = stringResource(R.string.forget_password),
            color = AccentColor,
            fontSize = 12.sp,
            modifier = Modifier.padding(start = 4.dp)
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            {},
            colors = ButtonDefaults.buttonColors().copy(
                containerColor = Color.Black,
                contentColor = Color.White,
                disabledContentColor = Color.Black,
                disabledContainerColor = Color.White,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
        ) {
            Text(text = stringResource(R.string.authorize_button), fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            {},
            colors = ButtonDefaults.buttonColors().copy(
                containerColor = TextFieldContainerColor,
                contentColor = Color.Black,
                disabledContentColor = Color.Black,
                disabledContainerColor = TextFieldContainerColor,
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
fun CredentialsTextField(
    value: String,
    onValueChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    enabled: Boolean = true,
    onClear: (() -> Unit) = {},
    label: @Composable () -> Unit = {},
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
) {
    TextField(
        value = value,
        onValueChange = onValueChanged,
        modifier = modifier,
        enabled = enabled,
        isError = isError,
        trailingIcon = {
            Column {
                if (value.isNotEmpty()) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = null,
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .clickable { onClear() }
                    )
                }
            }
        },
        singleLine = true,
        shape = RoundedCornerShape(12.dp),
        colors = TextFieldDefaults.colors().copy(
            focusedContainerColor = TextFieldContainerColor,
            unfocusedContainerColor = TextFieldContainerColor,
            errorContainerColor = TextFieldContainerColor,
            disabledContainerColor = TextFieldContainerColor,
            focusedIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            unfocusedLabelColor = TextFieldLabelColor,
            focusedLabelColor = TextFieldLabelColor,
            errorLabelColor = AlertColor,
            errorTrailingIconColor = AlertColor,
            errorCursorColor = AlertColor,
        ),
        label = label,
        keyboardOptions = keyboardOptions,
    )
}

@Preview
@Composable
fun LoginTextFieldPreview() {
    val text = remember { mutableStateOf("") }
    CredentialsTextField(
        text.value,
        { text.value = it },
        onClear = { text.value = "" },
        label = { Text("Email") }
    )
}