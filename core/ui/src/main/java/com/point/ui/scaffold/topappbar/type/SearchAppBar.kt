package com.point.ui.scaffold.topappbar.type

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.point.ui.Theme
import kotlinx.coroutines.flow.debounce

@Composable
fun SearchAppBar(@StringRes placeholder: Int, onValueChange: (String) -> Unit, modifier: Modifier = Modifier) {
    var value by remember { mutableStateOf("") }
    val isClearVisible by remember { derivedStateOf { value.isNotEmpty() } }
    TextField(
        value = value,
        onValueChange = { value = it },
        maxLines = 1,
        textStyle = Theme.typography.bodyM,
        placeholder = { Text(text = stringResource(placeholder), style = Theme.typography.bodyM) },
        colors = TextFieldDefaults.colors(
            errorContainerColor = Theme.colorScheme.surface,
            focusedContainerColor = Theme.colorScheme.surface,
            unfocusedContainerColor = Theme.colorScheme.surface,
            disabledContainerColor = Theme.colorScheme.surface,
            disabledIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        trailingIcon = {
            if (isClearVisible) {
                val interactionSource = remember { MutableInteractionSource() }
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = null,
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .clickable(
                            interactionSource = interactionSource,
                            indication = rememberRipple(color = Color.Black),
                            onClick = { value = "" }
                        )
                        .padding(8.dp)
                )
            }
        },
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Words,
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done,
        ),
        modifier = modifier.clip(RoundedCornerShape(20.dp)),
    )

    LaunchedEffect(Unit) {
        snapshotFlow { value }
            .debounce(150)
            .collect { newValue ->
                onValueChange(newValue)
            }
    }
}