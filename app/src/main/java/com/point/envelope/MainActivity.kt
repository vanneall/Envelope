package com.point.envelope

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.point.envelope.bottombar.EnvelopeNavBar
import com.point.envelope.navigation.navhost.ComposeNavigationRoute
import com.point.envelope.navigation.navhost.EnvelopeNavHost
import com.point.settings.AppUiSettings
import com.point.ui.EnvelopeTheme
import com.point.ui.LocalUiSettings
import com.point.ui.Theme
import com.point.ui.scaffold.fab.EnvelopeFab
import com.point.ui.scaffold.fab.FabState
import com.point.ui.scaffold.holder.ScaffoldHolder
import com.point.ui.scaffold.topappbar.EnvelopeTopAppBar
import com.point.ui.scaffold.topappbar.state.TopAppBarState
import com.point.ui.scaffold.topappbar.type.AppBarType
import com.point.user.LocalUser
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.map

@AndroidEntryPoint
class MainActivity : ComponentActivity(), ScaffoldHolder {

    private val viewModel by viewModels<MainViewModel>()

    private val appBarState = mutableStateOf(TopAppBarState())

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        splashScreen.setKeepOnScreenCondition { viewModel.isInitializing.value }

        WindowCompat.setDecorFitsSystemWindows(window, false)
        val localUser = viewModel.localUser.map { user -> user?.let { LocalUser(username = it.username) } }
        val localSettings =
            viewModel.appSettings.map { settings -> AppUiSettings(useAnimations = settings.useAnimations) }

        val batteryLevelReceiver = BatteryLevelReceiver(applicationContext, viewModel.appSettingsRepository)
        val filter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        applicationContext.registerReceiver(batteryLevelReceiver, filter)

        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val selectedItemIndex = rememberSaveable { mutableIntStateOf(0) }
            val bottomBarState = remember { mutableStateOf(BottomBarState(false)) }
            val fabState = remember { mutableStateOf<FabState>(FabState.Hidden) }
            EnvelopeTheme(
                localUser = localUser.collectAsState(null).value,
                localUiSettings = localSettings.collectAsState(AppUiSettings()).value
            ) {
                Scaffold(
                    topBar = {
                        if (appBarState.value.appBarType != AppBarType.Invisible) {
                            EnvelopeTopAppBar(
                                appBarState = appBarState.value,
                                modifier = Modifier.fillMaxWidth()
                            )
                        } else {

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
                    contentWindowInsets = if (appBarState.value.appBarType == AppBarType.Invisible) {
                        WindowInsets(0.dp, 0.dp, 0.dp, 0.dp)
                    } else {
                        ScaffoldDefaults.contentWindowInsets
                    },
                    containerColor = Theme.colorScheme.background,
                ) { innerPadding ->
                    EnvelopeNavHost(
                        navHostController = navController,
                        startDestination = if (viewModel.localUser.collectAsState().value != null) {
                            ComposeNavigationRoute.EntryRoute.Chats
                        } else {
                            ComposeNavigationRoute.SubRoute.Authorization
                        },
                        topAppBarState = appBarState,
                        bottomBarState = bottomBarState,
                        fabState = fabState,
                        useAnim = LocalUiSettings.current.useAnimations,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(innerPadding),
                    )
                }
            }
        }
    }

    override fun setTopAppBar(topAppBarState: TopAppBarState) {
        appBarState.value = topAppBarState
    }

    override var topAppBarState: TopAppBarState
        get() = appBarState.value
        set(value) {
            appBarState.value = value
        }
}

