package com.point.envelope.navigation.navhost

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.point.envelope.BottomBarState
import com.point.envelope.TopAppBarState2
import com.point.envelope.navigation.feature.authFeature
import com.point.envelope.navigation.feature.chatsFeature
import com.point.envelope.navigation.feature.contactsFeature
import com.point.envelope.navigation.feature.settingsFeature

@Composable
fun EnvelopeNavHost(
    navHostController: NavHostController,
    startDestination: ComposeNavigationRoute,
    topAppBarState: MutableState<TopAppBarState2>,
    bottomBarState: MutableState<BottomBarState>,
    modifier: Modifier,
) {
    NavHost(
        navController = navHostController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        authFeature(
            navController = navHostController,
            topAppBarState = topAppBarState,
            bottomBarState = bottomBarState,
        )

        chatsFeature(
            navController = navHostController,
            topAppBarState = topAppBarState,
            bottomBarState = bottomBarState,
        )

        contactsFeature(
            navController = navHostController,
            topAppBarState = topAppBarState,
            bottomBarState = bottomBarState,
        )

        settingsFeature(
            navController = navHostController,
            topAppBarState = topAppBarState,
            bottomBarState = bottomBarState,
        )
    }
}

