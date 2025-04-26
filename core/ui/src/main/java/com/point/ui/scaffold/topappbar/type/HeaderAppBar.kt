package com.point.ui.scaffold.topappbar.type

import androidx.annotation.StringRes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.point.ui.Theme

@Composable
fun HeaderAppBar(@StringRes headerRes: Int?, header: String, modifier: Modifier = Modifier) {
    Text(
        text = headerRes?.let { stringResource(headerRes) } ?: header,
        style = Theme.typography.titleL,
        color = Theme.colorScheme.textPrimary,
        modifier = modifier,
    )
}