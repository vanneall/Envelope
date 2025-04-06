package com.point.envelope

import androidx.compose.material3.FabPosition
import androidx.compose.runtime.Composable

data class BottomBarState(val isVisible: Boolean = false)

data class FabState(
    val composable: @Composable () -> Unit = {},
    val position: FabPosition = FabPosition.End,
)