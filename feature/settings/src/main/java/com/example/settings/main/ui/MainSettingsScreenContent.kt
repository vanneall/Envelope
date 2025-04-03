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
import androidx.compose.material.icons.filled.BatterySaver
import androidx.compose.material.icons.filled.DataUsage
import androidx.compose.material.icons.filled.DesignServices
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
        item {
            UserAvatar(
                userData = state.userData,
            )
            
            Spacer(modifier = Modifier.height(24.dp))
        }
        
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
                    is AppSettings -> AppSettings(appSettings = item, modifier)
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
        name = "Daniil",
        username = "@skylejke",
        url = DEFAULT_IMAGE_URL,
    )

    val settings = listOf(
        AppSettings(
            textId = R.string.storage_settings,
            icon = Icons.Default.DataUsage,
            iconBackground = Color.Blue,
            iconColor = Color.Red
        ),
        AppSettings(
            textId = R.string.battery_settings,
            icon = Icons.Default.BatterySaver,
            iconBackground = Color.Green,
            iconColor = Color.Red
        ),
        AppSettings(
            textId = R.string.theme_settings,
            icon = Icons.Default.DesignServices,
            iconBackground = Color.Red,
            iconColor = Color.Red
        ),
    )

    EnvelopeTheme {
        MainSettingsScreenContent(
            state = MainSettingsState(
                isLoading = true,
                userData = userData,
                settings = listOf(
                    SettingsSection(
                        settings = settings,
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