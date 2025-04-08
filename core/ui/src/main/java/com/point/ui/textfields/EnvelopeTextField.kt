package com.point.ui.textfields

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.point.ui.Theme
import kotlinx.coroutines.flow.debounce
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun EnvelopeTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    debounce: Duration = 200.milliseconds,
    maxLines: Int = 3,
    singleLine: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    trailingIcon: @Composable (() -> Unit)? = null,
    supportingText: String? = null,
    labelText: String? = null,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (labelText != null) {
            Text(
                text = labelText,
                style = Theme.typography.bodyM,
                color = if (isError) Theme.colorScheme.error else Theme.colorScheme.textSecondary,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        var text by remember { mutableStateOf(value) }
        TextField(
            value = text,
            onValueChange = { text = it },
            maxLines = maxLines,
            singleLine = singleLine,
            keyboardOptions = keyboardOptions,
            colors = Theme.colorScheme.defaultTextFieldColors,
            modifier = modifier,
            visualTransformation = visualTransformation,
            trailingIcon = {
                when {
                    trailingIcon != null -> {
                        trailingIcon()
                    }
                    text.isNotEmpty() -> {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = null,
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .clickable { text = "" }
                                .padding(4.dp))
                    }
                }
            },
        )
        if (supportingText != null) {
            Text(
                text = supportingText,
                style = Theme.typography.labelM,
                color = Theme.colorScheme.error,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        LaunchedEffect(Unit) {
            snapshotFlow { text }
                .debounce(timeout = { debounce })
                .collect { text -> onValueChange(text) }
        }
    }
}