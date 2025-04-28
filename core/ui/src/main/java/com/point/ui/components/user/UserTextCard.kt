package com.point.ui.components.user

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.point.ui.colors.new.EnvelopeTheme
import com.point.ui.colors.new.Gray600
import com.point.ui.colors.new.NewEnvelopeTheme
import com.point.ui.materials.switches.EnvelopeSwitch

@Composable
fun UserTextCard(user: UserCardInfo, modifier: Modifier = Modifier, trailing: (@Composable () -> Unit)? = null) {
    BaseUserCard(
        user = user.userBase,
        modifier = modifier,
        supporting = if (user.supportText != null) {
            {
                Text(
                    text = user.supportText,
                    style = EnvelopeTheme.typography.bodyS,
                    color = Gray600,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        } else {
            {}
        },
        trailing = trailing,
    )
}

@Preview
@Composable
private fun UserCardPreview() {
    NewEnvelopeTheme {
        UserTextCard(
            user = UserCardInfo(
                userBase = UserBase(name = "User"),
                supportText = "Some description",
            ),
            modifier = Modifier
                .width(400.dp)
                .background(color = Color.White)
        )
    }
}

@Preview
@Composable
private fun UserCardWithoutSupportingPreview() {
    NewEnvelopeTheme {
        UserTextCard(
            user = UserCardInfo(
                userBase = UserBase(name = "User"),
            ),
            modifier = Modifier
                .width(400.dp)
                .background(color = Color.White)
        )
    }
}

@Preview
@Composable
private fun UserCardWithTrailingPreview() {
    NewEnvelopeTheme {
        UserTextCard(
            user = UserCardInfo(
                userBase = UserBase(name = "User"),
                supportText = "Some description",
            ),
            modifier = Modifier
                .width(400.dp)
                .background(color = Color.White),
            trailing = {
                EnvelopeSwitch(
                    checked = true,
                    onCheckedChange = {},
                )
            }
        )
    }
}

data class UserCardInfo(val userBase: UserBase, val supportText: String? = null)