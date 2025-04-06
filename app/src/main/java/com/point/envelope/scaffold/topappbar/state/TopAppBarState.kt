package com.point.envelope.scaffold.topappbar.state

import androidx.compose.runtime.Immutable
import com.point.envelope.scaffold.topappbar.type.AppBarType

@Immutable
data class TopAppBarState(
    val appBarType: AppBarType = AppBarType.EmptyAppBar,
    val actions: List<TopAppBarAction> = emptyList(),
    val onBack: (() -> Unit)? = null,
)