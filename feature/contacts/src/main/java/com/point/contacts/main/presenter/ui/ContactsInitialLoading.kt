package com.point.contacts.main.presenter.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ContactsInitialLoading(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        repeat(9) {
            ContactComposableShimmer(modifier = Modifier.fillMaxWidth())
        }
    }
}