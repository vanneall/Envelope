package com.point.ui.components.user

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.point.ui.colors.new.NewEnvelopeTheme
import com.point.ui.materials.buttons.styled.ButtonStyle
import com.point.ui.materials.buttons.styled.PrimaryButton
import com.point.ui.materials.buttons.styled.SecondaryButton

@Composable
fun UserButtonCard(
    user: UserCardButtonInfo,
    primaryText: String,
    onPrimaryClick: () -> Unit,
    secondaryText: String,
    onSecondaryClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    BaseUserCard(
        user = user.userBase,
        modifier = modifier,
        supporting = {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 4.dp),
            ) {
                PrimaryButton(
                    onClick = onPrimaryClick,
                    text = primaryText,
                    buttonStyle = ButtonStyle.S,
                )

                SecondaryButton(
                    onClick = onSecondaryClick,
                    text = secondaryText,
                    buttonStyle = ButtonStyle.S,
                )
            }
        },
    )
}

@Preview
@Composable
private fun UserCardButtonPreview() {
    NewEnvelopeTheme {
        UserButtonCard(
            user = UserCardButtonInfo(userBase = UserBase(name = "User")),
            onPrimaryClick = {},
            onSecondaryClick = {},
            primaryText = "Accept",
            secondaryText = "Reject",
            modifier = Modifier
                .width(400.dp)
                .background(color = Color.White)
        )
    }
}

data class UserCardButtonInfo(val userBase: UserBase)