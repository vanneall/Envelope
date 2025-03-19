package com.point.envelope

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Message
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.point.envelope.navigation.EnvelopeNavHost
import com.point.envelope.navigation.Screen
import com.point.ui.EnvelopeTheme
import com.point.ui.Theme
import com.point.ui.colors.White
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bottomNavList = listOf(
            BottomBarItem.AllChats,
            BottomBarItem.Profile,
        )
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val selectedItemIndex = rememberSaveable { mutableIntStateOf(0) }
            val appBarState = remember { mutableStateOf(TopAppBarState2("")) }
            EnvelopeTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(
                                    text = appBarState.value.text,
                                    style = Theme.typography.headlineM,
                                    color = Theme.colorScheme.textPrimary,
                                )
                            },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = Theme.colorScheme.background,
                            )
                        )
                    },
                    bottomBar = {
                        NavigationBar(
                            modifier = Modifier.fillMaxWidth(),
                            containerColor = Theme.colorScheme.background,
                        ) {
                            bottomNavList.forEachIndexed { index, item ->
                                val selected = index == selectedItemIndex.intValue
                                NavigationBarItem(
                                    selected = selected,
                                    onClick = {
                                        if (selectedItemIndex.intValue == index) return@NavigationBarItem

                                        selectedItemIndex.intValue = index
                                        navController.navigate(item.screen)
                                    },
                                    label = {
                                        Text(
                                            text = item.text,
                                            fontSize = 14.sp,
                                            fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal
                                        )
                                    },
                                    icon = {
                                        Icon(
                                            imageVector = item.icon(selected),
                                            contentDescription = null
                                        )
                                    },
                                    colors = NavigationBarItemDefaults.colors(
                                        indicatorColor = Theme.colorScheme.accent,
                                        selectedIconColor = White,
                                    )
                                )
                            }
                        }
                    },
                    modifier = Modifier.fillMaxSize(),
                    containerColor = Theme.colorScheme.background,
                ) { innerPadding ->
                    EnvelopeNavHost(
                        navHostController = navController,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(innerPadding),
                        topAppBarState = appBarState,
                    )
                }
            }
        }
    }
}

sealed class BottomBarItem(
    val text: String,
    val screen: Screen,
    private val selectedIcon: ImageVector,
    private val unselectedIcon: ImageVector,
) {

    fun icon(selected: Boolean) = if (selected) selectedIcon else unselectedIcon

    data object AllChats : BottomBarItem(
        text = "Chats",
        selectedIcon = Icons.AutoMirrored.Filled.Message,
        unselectedIcon = Icons.AutoMirrored.Default.Message,
        screen = Screen.AllChats,
    )

    data object Profile : BottomBarItem(
        text = "Profile",
        selectedIcon = Icons.Default.Person,
        unselectedIcon = Icons.Outlined.Person,
        screen = Screen.Profile
    )

}