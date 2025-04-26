package com.point.ui.components.user

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.point.ui.colors.new.NewEnvelopeTheme
import com.point.ui.materials.switches.EnvelopeSwitch

@Composable
fun UserCard(user: UserCardInfo, modifier: Modifier = Modifier, trailing: (@Composable () -> Unit)? = null) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        UserPhoto(user.photoUrl, modifier = Modifier.size(52.dp))

        UserTextBlock(
            name = user.name,
            supportText = user.supportText,
            modifier = Modifier.weight(1f)
        )

        trailing?.invoke()
    }
}

@Preview
@Composable
private fun UserCardPreview() {
    NewEnvelopeTheme {
        UserCard(
            user = UserCardInfo(
                name = "User",
                supportText = "Some description",
            ),
            modifier = Modifier.width(400.dp).background(color = Color.White)
        )
    }
}

@Preview
@Composable
private fun UserCardWithoutSupportingPreview() {
    NewEnvelopeTheme {
        UserCard(
            user = UserCardInfo(
                name = "User",
            ),
            modifier = Modifier.width(400.dp).background(color = Color.White)
        )
    }
}

@Preview
@Composable
private fun UserCardWithTrailingPreview() {
    NewEnvelopeTheme {
        UserCard(
            user = UserCardInfo(
                name = "User",
                supportText = "Some description",
            ),
            modifier = Modifier.width(400.dp).background(color = Color.White),
            trailing = {
                EnvelopeSwitch(
                    checked = true,
                    onCheckedChange = {},
                )
            }
        )
    }
}


data class UserCardInfo(val name: String, val photoUrl: String? = null, val supportText: String? = null)