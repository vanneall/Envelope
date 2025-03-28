package com.point.envelope

import androidx.compose.material3.FabPosition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.vector.ImageVector

@Stable
class TopAppBarState(val composable: @Composable () -> Unit = {})

data class TopAppBarState2(
    val text: String,
    val isBackVisible: Boolean = false,
    val onBackClick: () -> Unit = {},
    val actions: List<TopBarAction> = emptyList(),
)

data class TopBarAction(val icon: ImageVector, val action: () -> Unit)

@Immutable
data class BottomBarState(val isVisible: Boolean)

data class FabState(
    val composable: @Composable () -> Unit = {},
    val position: FabPosition = FabPosition.End,
)