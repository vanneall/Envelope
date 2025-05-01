package com.point.ui.scaffold.holder

import android.annotation.SuppressLint
import android.app.Activity
import androidx.core.app.ComponentActivity
import com.point.ui.scaffold.topappbar.state.TopAppBarState

interface ScaffoldHolder {

    fun setTopAppBar(topAppBarState: TopAppBarState)

    var topAppBarState: TopAppBarState
}

val Activity.scaffoldHolder: ScaffoldHolder
    @SuppressLint("RestrictedApi")
    get() = (this as ComponentActivity) as ScaffoldHolder