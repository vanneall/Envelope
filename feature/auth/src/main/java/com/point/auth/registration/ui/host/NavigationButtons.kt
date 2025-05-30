package com.point.auth.registration.ui.host

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.point.auth.R
import com.point.auth.registration.presenter.host.HostAction
import com.point.auth.registration.presenter.host.HostAction.UiAction
import com.point.ui.Theme
import kotlinx.coroutines.launch

@Composable
fun NavigationButtons(
    onAction: (HostAction) -> Unit,
    currentPage: Int,
    isLastPage: Boolean,
    isCodeEntered: Boolean,
    email: String,
    modifier: Modifier = Modifier,
) {
    val coroutineScope = rememberCoroutineScope()
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        AnimatedVisibility(
            visible = currentPage != 0,
            enter = slideInHorizontally(initialOffsetX = { -it }) + fadeIn(),
            exit = slideOutHorizontally(targetOffsetX = { -it }) + fadeOut(),
            modifier = Modifier.weight(1f)
        ) {
            Button(
                onClick = {
                    coroutineScope.launch {
                        if (currentPage > 0) onAction(UiAction.OnNewPage(currentPage, currentPage - 1))
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Theme.colorScheme.surface,
                    contentColor = Color.Black,
                    disabledContentColor = Color.Black,
                    disabledContainerColor = Theme.colorScheme.disabled,
                )
            ) {
                Text("Назад", style = Theme.typography.bodyL)
            }
        }

        AnimatedContent(
            targetState = isLastPage,
            modifier = Modifier
                .weight(1f)
                .animateContentSize()
        ) { isLastPage ->
            val colorAnim =
                animateColorAsState(if (isLastPage) Color.Black else Theme.colorScheme.surface)
            val colorTextAnim =
                animateColorAsState(if (isLastPage) Color.White else Color.Black)
            Button(
                onClick = {
                    when {
                        isCodeEntered && isLastPage -> onAction(UiAction.OnRegistration)
                        isLastPage -> onAction(UiAction.RequestCode(email))
                        else -> onAction(UiAction.OnNewPage(currentPage, currentPage + 1))
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorAnim.value,
                    contentColor = colorTextAnim.value,
                    disabledContentColor = Color.Black,
                    disabledContainerColor = Theme.colorScheme.disabled,
                )
            ) {
                Text(
                    text = when {
                        isCodeEntered && isLastPage -> stringResource(R.string.create_account)
                        isLastPage -> stringResource(R.string.request)
                        else -> stringResource(R.string.next)
                    },
                    style = Theme.typography.bodyL
                )
            }
        }
    }
}