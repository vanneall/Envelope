package com.point.auth.authorization.presenter.ui.blocks

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.point.auth.R
import com.point.auth.authorization.presenter.viewmodel.AuthAction
import com.point.auth.authorization.presenter.viewmodel.AuthAction.Action.Authorization
import com.point.navigation.Route
import com.point.ui.Theme

@Composable
internal fun AuthorizationButtons(
    onAction: (AuthAction) -> Unit,
    onNavigate: (Route) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Button(
            onClick = { onAction(Authorization) },
            colors = ButtonDefaults.buttonColors().copy(
                containerColor = Color.Black,
                contentColor = Color.White,
                disabledContentColor = Color.Black,
                disabledContainerColor = Color.White,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
        ) {
            Text(text = stringResource(R.string.sign_in), style = Theme.typography.bodyL)
        }

        Button(
            onClick = { onNavigate(Route.AuthFeature.Registration) },
            colors = ButtonDefaults.buttonColors().copy(
                containerColor = Theme.colorScheme.surface,
                contentColor = Color.Black,
                disabledContentColor = Color.Black,
                disabledContainerColor = Theme.colorScheme.disabled,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
        ) {
            Text(text = stringResource(R.string.registration_button), style = Theme.typography.bodyL)
        }
    }
}