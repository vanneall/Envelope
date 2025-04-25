package com.point.auth.registration.ui.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.point.auth.R
import com.point.auth.registration.presenter.profile.RegProfileAction
import com.point.auth.registration.presenter.profile.RegProfileState
import com.point.ui.Theme

@Composable
internal fun ProfileScreenContent(
    state: RegProfileState,
    onAction: (RegProfileAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(WindowInsets.ime.exclude(WindowInsets.navigationBars).asPaddingValues())
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {

        Text(
            text = stringResource(R.string.invite_to_fill_user_info),
            style = Theme.typography.titleL,
        )

        UserInfoInputBlock(
            state = state,
            onAction = onAction,
            modifier = Modifier.fillMaxSize(),
        )
    }
}
