package com.point.envelope.navigation.extensions

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.point.envelope.navigation.navhost.ComposeNavigationRoute.EntryRoute
import com.point.envelope.navigation.navhost.ComposeNavigationRoute.SubRoute


inline fun <reified T : SubRoute> NavGraphBuilder.subComposable(
    noinline content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit,
) {
    composable<T>(
        enterTransition = { slideInHorizontally(initialOffsetX = { it }) },
        exitTransition = { slideOutHorizontally(targetOffsetX = { -it }) },
        popEnterTransition = { slideInHorizontally(initialOffsetX = { -it }) },
        popExitTransition = { slideOutHorizontally(targetOffsetX = { it }) },
        content = content,
    )
}

inline fun <reified T : EntryRoute> NavGraphBuilder.entryComposable(
    noinline content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit,
) {
    composable<T>(
        enterTransition = {
            slideInVertically(
                initialOffsetY = { it / 2 }
            ) + scaleIn(
                initialScale = 0.85f,
                animationSpec = tween(ANIM_DURATION, easing = FastOutSlowInEasing)
            ) + fadeIn(
                animationSpec = tween(ANIM_DURATION)
            )
        },
        exitTransition = { fadeOut(animationSpec = tween(0)) },
        popEnterTransition = { slideInHorizontally(initialOffsetX = { -it }) },
        popExitTransition = { slideOutHorizontally(targetOffsetX = { it }) },
        content = content,
    )
}

const val ANIM_DURATION = 200