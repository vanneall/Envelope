package com.point.ui.materials.textfields

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.point.ui.R
import com.point.ui.Theme
import kotlinx.coroutines.flow.debounce

@Composable
fun EnvelopeOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: (@Composable () -> Unit)? = null,
    enabled: Boolean = true,
    error: Boolean = false,
    @StringRes errorTextRes: Int? = null,
    minLines: Int = 1,
    maxLines: Int = 1,
    maxSymbols: Int = 100,
    keyboardOptions: KeyboardOptions = KeyboardOptions(),
    visualTransformation: VisualTransformation = VisualTransformation.None,
) {
    var cachedText by remember { mutableStateOf(value) }
    val isTextVisible by remember { derivedStateOf { cachedText.isNotEmpty() } }

    OutlinedTextField(
        value = cachedText,
        onValueChange = { if (it.length <= maxSymbols) cachedText = it },
        minLines = minLines,
        maxLines = maxLines,
        placeholder = placeholder,
        trailingIcon = {
            if (isTextVisible) {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                        .clickable { cachedText = "" }
                        .size(16.dp),
                )
            }
        },
        visualTransformation = visualTransformation,
        textStyle = Theme.typography.bodyM,
        keyboardOptions = keyboardOptions,
        enabled = enabled,
        isError = error,
        modifier = modifier,
        supportingText = {
            if (error && errorTextRes != null) {
                Text(
                    text = stringResource(errorTextRes),
                    style = Theme.typography.labelM,
                    color = Theme.colorScheme.error,
                )
            } else if (maxSymbols - cachedText.length <= maxSymbols * 0.3f) {
                Text(
                    text = stringResource(R.string.keep_symbols, maxSymbols - cachedText.length),
                    style = Theme.typography.labelM,
                    color = Theme.colorScheme.textSecondary,
                )
            }
        },
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
    )

    LaunchedEffect(Unit) {
        snapshotFlow { cachedText }
            .debounce(150)
            .collect { text -> onValueChange(text) }
    }
}
