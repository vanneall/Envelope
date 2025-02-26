package com.point.auth.registration.presenter.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.point.auth.R
import com.point.auth.registration.presenter.mvi.RegAction.Action.*
import com.point.auth.authorization.presenter.ui.CredentialsTextField
import com.point.auth.registration.presenter.mvi.RegAction
import com.point.auth.registration.presenter.mvi.RegState
import com.point.ui.TextFieldContainerColor

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RegistrationScreen(
    state: RegState,
    onAction: (RegAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    val calculatedPadding = if (WindowInsets.isImeVisible) {
        PaddingValues(top = 40.dp, bottom = 0.dp, start = 20.dp, end = 20.dp)
    } else {
        PaddingValues(top = 40.dp, bottom = 20.dp, start = 20.dp, end = 20.dp)
    }

    Column(
        modifier = modifier
            .padding(calculatedPadding)
            .imePadding(),
    ) {
        Text(
            text = stringResource(R.string.registration_title),
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
                .height(32.dp)
        )

        CredentialsTextField(
            value = state.login,
            onValueChanged = { onAction(OnPasswordInput(it)) },
            onClear = { onAction(OnPasswordClear) },
            label = { Text(stringResource(R.string.name_label)) },
            isError = state.isInvalidCredentials,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                autoCorrect = true,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
        )

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(32.dp)
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

        Spacer(modifier = Modifier.weight(1f))

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

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = stringResource(R.string.registration_informer),
            color = Color.Black,
            fontSize = 12.sp,
            modifier = Modifier.padding(start = 4.dp)
        )
    }
}