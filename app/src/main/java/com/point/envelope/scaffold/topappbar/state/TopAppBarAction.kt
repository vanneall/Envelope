package com.point.envelope.scaffold.topappbar.state

import androidx.compose.ui.graphics.vector.ImageVector

data class TopAppBarAction(val icon: ImageVector, val action: () -> Unit)