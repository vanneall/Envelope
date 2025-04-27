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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.point.envelope.bottombar.EnvelopeNavBar
import com.point.envelope.navigation.navhost.ComposeNavigationRoute
import com.point.envelope.navigation.navhost.EnvelopeNavHost
import com.point.ui.EnvelopeTheme
import com.point.ui.Theme
import com.point.ui.scaffold.fab.EnvelopeFab
import com.point.ui.scaffold.fab.FabState
import com.point.ui.scaffold.topappbar.EnvelopeTopAppBar
import com.point.ui.scaffold.topappbar.state.TopAppBarState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        splashScreen.setKeepOnScreenCondition { viewModel.isInitializing.value }

        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val selectedItemIndex = rememberSaveable { mutableIntStateOf(0) }
            val appBarState = remember { mutableStateOf(TopAppBarState()) }
            val bottomBarState = remember { mutableStateOf(BottomBarState(false)) }
            val fabState = remember { mutableStateOf<FabState>(FabState.Hidden) }
            EnvelopeTheme {
                Scaffold(
                    topBar = {
                        EnvelopeTopAppBar(
                            appBarState = appBarState.value,
                            modifier = Modifier.fillMaxWidth()
                        )
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
                    floatingActionButton = {
                        when (val state = fabState.value) {
                            is FabState.Showed -> {
                               EnvelopeFab(
                                   icon = state.icon,
                                   onClick = state.action,
                               )
                            }
                            FabState.Hidden -> {}
                        }
                    },
                    modifier = Modifier.fillMaxSize(),
                    containerColor = Theme.colorScheme.background,
                ) { innerPadding ->
                    EnvelopeNavHost(
                        navHostController = navController,
                        startDestination = if (viewModel.user.collectAsState().value != null) {
                            ComposeNavigationRoute.EntryRoute.Chats
                        } else {
                            ComposeNavigationRoute.SubRoute.Authorization
                        },
                        topAppBarState = appBarState,
                        bottomBarState = bottomBarState,
                        fabState = fabState,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(innerPadding),
                    )
                }
            }
        }
    }
}

