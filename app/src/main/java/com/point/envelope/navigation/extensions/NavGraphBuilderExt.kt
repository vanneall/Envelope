package com.point.envelope.navigation.extensions

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
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
    useAnim: Boolean = true,
    noinline content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit,
) {
    composable<T>(
        enterTransition = {
            if (useAnim) {
                slideInHorizontally(initialOffsetX = { it })
            } else EnterTransition.None
        },
        exitTransition = {
            if (useAnim) {
                slideOutHorizontally(targetOffsetX = { -it })
            } else ExitTransition.None
        },
        popEnterTransition = {
            if (useAnim) {
                slideInHorizontally(initialOffsetX = { -it })
            } else EnterTransition.None
        },
        popExitTransition = {
            if (useAnim) {
                slideOutHorizontally(targetOffsetX = { it })
            } else ExitTransition.None
        },
        content = content,
    )
}

inline fun <reified T : EntryRoute> NavGraphBuilder.entryComposable(
    useAnim: Boolean = true,
    noinline content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit,
) {
    composable<T>(
        enterTransition = {
            if (useAnim) {
                slideInVertically(
                    initialOffsetY = { it / 2 }
                ) + scaleIn(
                    initialScale = 0.85f,
                    animationSpec = tween(ANIM_DURATION, easing = FastOutSlowInEasing)
                ) + fadeIn(
                    animationSpec = tween(ANIM_DURATION)
                )
            } else {
                EnterTransition.None
            }
        },
        exitTransition = {
            if (useAnim) {
                fadeOut(animationSpec = tween(0))
            } else {
                ExitTransition.None
            }
        },
        popEnterTransition = {
            if (useAnim) {
                slideInHorizontally(initialOffsetX = { -it })
            } else {
                EnterTransition.None
            }
        },
        popExitTransition = {
            if (useAnim) {
                slideOutHorizontally(targetOffsetX = { it })
            } else {
                ExitTransition.None
            }
        },
        content = content,
    )
}

const val ANIM_DURATION = 200