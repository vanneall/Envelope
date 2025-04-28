package com.point.ui.materials.buttons.defaults

import androidx.compose.foundation.Indication
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.point.ui.materials.buttons.styled.ButtonStyle

@Composable
internal fun EnvelopeButton(
    text: String,
    onClick: () -> Unit,
    colors: ButtonColors,
    modifier: Modifier = Modifier,
    buttonStyle: ButtonStyle = ButtonStyle.M,
    enabled: Boolean = true,
    leadingIcon: ImageVector? = null,
    interactionSource: InteractionSource = MutableInteractionSource(),
    indication: Indication? = null,
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        colors = colors,
        shape = RoundedCornerShape(buttonStyle.corners),
        modifier = modifier
            .indication(interactionSource = interactionSource, indication = indication)
            .height(buttonStyle.height),
        contentPadding = PaddingValues(
            start = if (leadingIcon != null) 16.dp else buttonStyle.horizontalPadding,
            end = buttonStyle.horizontalPadding,
        ),
    ) {
        if (leadingIcon != null) {
            Icon(
                imageVector = leadingIcon,
                contentDescription = null,
                modifier = Modifier.size(18.dp),
            )

            Spacer(modifier = Modifier.width(8.dp))
        }
        Text(text = text, style = buttonStyle.textStyle)
    }
}
