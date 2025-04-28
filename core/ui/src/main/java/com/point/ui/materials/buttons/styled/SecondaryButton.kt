package com.point.ui.materials.buttons.styled

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.point.ui.colors.new.NewEnvelopeTheme
import com.point.ui.colors.new.White
import com.point.ui.materials.buttons.defaults.EnvelopeButton
import com.point.ui.materials.buttons.defaults.EnvelopeButtonDefaults

@Composable
fun SecondaryButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
    buttonStyle: ButtonStyle = ButtonStyle.M,
    enabled: Boolean = true,
    leadingIcon: ImageVector? = null,
) {
    EnvelopeButton(
        text = text,
        onClick = onClick,
        enabled = enabled,
        buttonStyle = buttonStyle,
        leadingIcon = leadingIcon,
        colors = EnvelopeButtonDefaults.SecondaryColors,
        modifier = modifier,
        interactionSource = remember { MutableInteractionSource() },
        indication = rememberRipple(
            bounded = true,
            color = White,
            radius = 24.dp
        ),
    )
}

@Preview(showBackground = true)
@Composable
private fun PrimaryButtonIconedPreview() {
    NewEnvelopeTheme {
        Box(
            modifier = Modifier.padding(8.dp)
        ) {
            SecondaryButton(
                onClick = {},
                text = "Button",
                leadingIcon = Icons.Rounded.Add,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PrimaryButtonPreview() {
    NewEnvelopeTheme {
        Box(
            modifier = Modifier.padding(8.dp)
        ) {
            SecondaryButton(
                onClick = {},
                text = "Button",
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PrimaryButtonDisabledPreview() {
    NewEnvelopeTheme {
        Box(
            modifier = Modifier.padding(8.dp)
        ) {
            SecondaryButton(
                onClick = {},
                text = "Button",
                enabled = false,
            )
        }
    }
}