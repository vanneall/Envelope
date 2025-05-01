package com.point.ui.scaffold.topappbar.state

import androidx.compose.ui.graphics.vector.ImageVector

data class TopAppBarAction(val icon: ImageVector, val action: () -> Unit, val tag: String = "")
