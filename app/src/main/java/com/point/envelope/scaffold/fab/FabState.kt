package com.point.envelope.scaffold.fab

import androidx.compose.ui.graphics.vector.ImageVector

sealed interface FabState {

    data object Hidden : FabState

    data class Showed(val icon: ImageVector, val action: () -> Unit) : FabState
}