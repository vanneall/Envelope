package com.example.settings.main.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.settings.R
import com.example.settings.main.viewmodel.AppSettings
import com.example.settings.main.viewmodel.ExitSettings
import com.example.settings.main.viewmodel.MainSettingsState
import com.example.settings.main.viewmodel.Settings
import com.example.settings.main.viewmodel.SettingsAction
import com.example.settings.main.viewmodel.SettingsSection
import com.example.settings.main.viewmodel.UserData
import com.example.settings.main.viewmodel.UserSettings
import com.point.navigation.Route
import com.point.ui.EnvelopeTheme
import com.point.ui.Theme
import com.point.ui.colors.BlueContainerLight
import com.point.ui.colors.BlueContentLight
import com.point.ui.colors.GreenContainer
import com.point.ui.colors.GreenContent
import com.point.ui.colors.OrangeContainer
import com.point.ui.colors.OrangeContent
import com.point.ui.colors.RedContainer
import com.point.ui.colors.RedContent

@Composable
internal fun MainSettingsScreenContent(
    state: MainSettingsState,
    onNavigate: (Route) -> Unit,
    onAction: (SettingsAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        state.settings.forEach { section ->
            itemsIndexed(
                items = section.settings,
                key = { _, item -> item.textId }
            ) { index, item ->
                val backgroundShape = when {
                    index == 0 && index == section.settings.lastIndex -> {
                        RoundedCornerShape(size = CARD_CORNERS.dp)
                    }

                    index == 0 -> {
                        RoundedCornerShape(topStart = CARD_CORNERS.dp, topEnd = CARD_CORNERS.dp)
                    }

                    index == section.settings.lastIndex -> {
                        RoundedCornerShape(
                            bottomStart = CARD_CORNERS.dp,
                            bottomEnd = CARD_CORNERS.dp
                        )
                    }

                    else -> RoundedCornerShape(0.dp)
                }

                val modifier = Modifier
                    .height(54.dp)
                    .fillMaxWidth()
                    .background(color = Theme.colorScheme.surface, shape = backgroundShape)
                    .clip(shape = backgroundShape)

                when (item) {
                    is AppSettings -> AppSettings(
                        appSettings = item,
                        modifier = modifier,
                        onClick = { item.route?.let { onNavigate(it) } },
                    )

                    is ExitSettings -> ExitSettings(
                        exitSettings = item,
                        onClick = onAction,
                        modifier = modifier,
                    )

                    is UserSettings -> UserSettings(
                        userSettings = item,
                        onClick = { item.route?.let { onNavigate(it) } },
                        modifier = modifier,
                    )
                }

                if (index != section.settings.lastIndex) {
                    HorizontalDivider(
                        modifier = Modifier
                            .background(Theme.colorScheme.surface)
                            .padding(horizontal = 16.dp, vertical = 4.dp)
                    )
                } else {
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }
    }
}

private const val CARD_CORNERS = 16

@Preview
@Composable
private fun MainSettingsScreenContentPreview() {
    val userData = UserData(
        name = "User",
        username = "@username",
//        url = DEFAULT_IMAGE_URL,
    )

    val userSettings = listOf(
        UserSettings(
            textId = R.string.edit_profile_settings,
            icon = Icons.Default.Edit,
            iconColor = BlueContentLight,
            iconBackground = BlueContainerLight,
            count = 0,
//            route = Route.SettingsFeature.ProfileEdit,
        ),
        UserSettings(
            textId = R.string.requests_settings,
            icon = Icons.Default.Notifications,
            iconColor = OrangeContent,
            iconBackground = OrangeContainer,
            count = 15,
            route = Route.ContactsFeature.ContactsRequests,
        ),
        UserSettings(
            textId = R.string.contacts_settings,
            icon = Icons.Default.Person,
            iconColor = GreenContent,
            iconBackground = GreenContainer,
            count = 228,
            route = Route.ContactsFeature.UserContacts,
        ),
        ExitSettings(
            textId = R.string.exit_settings,
            icon = Icons.AutoMirrored.Filled.Logout,
            iconBackground = RedContainer,
            iconColor = RedContent,
        )
    )

    EnvelopeTheme {
        MainSettingsScreenContent(
            state = MainSettingsState(
                isRefreshing = true,
                userData = userData,
                settings = listOf(
                    SettingsSection(
                        settings = userSettings,
                        type = Settings.Type.APP,
                    )
                )
            ),
            onNavigate = { },
            onAction = {},
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White)
                .padding(12.dp)
        )
    }
}