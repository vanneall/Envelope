package com.point.envelope

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.point.envelope.navigation.navhost.ComposeNavigationRoute
import com.point.envelope.navigation.navhost.EnvelopeNavHost
import com.point.envelope.scaffold.bottombar.EnvelopeNavBar
import com.point.envelope.scaffold.bottombar.entryPoints
import com.point.envelope.scaffold.topappbar.EnvelopeTopAppBar
import com.point.envelope.scaffold.topappbar.state.TopAppBarState
import com.point.envelope.scaffold.topappbar.type.AppBarType
import com.point.ui.EnvelopeTheme
import com.point.ui.Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val selectedItemIndex = rememberSaveable { mutableIntStateOf(0) }
            val appBarState = remember { mutableStateOf(TopAppBarState()) }
            val bottomBarState = remember { mutableStateOf(BottomBarState(false)) }
            EnvelopeTheme {
                Scaffold(
                    topBar = {
                        if (appBarState.value.appBarType != AppBarType.EmptyAppBar) {
                            EnvelopeTopAppBar(
                                appBarState = appBarState.value,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    },
                    bottomBar = {
                        if (bottomBarState.value.isVisible) {
                            EnvelopeNavBar(
                                entryPoints = entryPoints,
                                onItemSelected = { index ->
                                    if (selectedItemIndex.intValue != index) {
                                        selectedItemIndex.intValue = index
                                        navController.navigate(entryPoints[index].composeNavigationRoute)
                                    }
                                },
                                selectedItemIndex = selectedItemIndex.intValue,
                                modifier = Modifier.fillMaxWidth(),
                            )
                        }
                    },
                    modifier = Modifier.fillMaxSize(),
                    containerColor = Theme.colorScheme.background,
                ) { innerPadding ->
                    EnvelopeNavHost(
                        navHostController = navController,
                        startDestination = if (viewModel.token != null) {
                            ComposeNavigationRoute.EntryRoute.Chats
                        } else {
                            ComposeNavigationRoute.SubRoute.Authorization
                        },
                        topAppBarState = appBarState,
                        bottomBarState = bottomBarState,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(innerPadding),
                    )
                }
            }
        }
    }
}

