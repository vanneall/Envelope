package com.point.auth.registration.ui.host

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.point.auth.registration.presenter.mvi.RegAction
import com.point.auth.registration.presenter.mvi.RegEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@Composable
fun RegistrationHostScreenContent(
    onAction: (RegAction) -> Unit,
    onNavigateToMain: () -> Unit,
    events: Flow<RegEvent>,
    pages: ImmutableList<@Composable () -> Unit>,
    modifier: Modifier = Modifier,
) {
    val scrollableState = rememberScrollState()
    Column(modifier = modifier.verticalScroll(scrollableState)) {

        val pagerState = rememberPagerState { pages.value.size }
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f),
            userScrollEnabled = false,
        ) { index ->
            pages.value[index]()
        }

        NavigationButtons(
            onAction = onAction,
            currentPage = pagerState.currentPage,
            isLastPage = pagerState.currentPage == pages.value.lastIndex,
            modifier = Modifier.fillMaxWidth(),
        )

        LaunchedEffect(Unit) {
            launch {
                events.collect {
                    when(it) {
                        is RegEvent.SwitchPage -> pagerState.animateScrollToPage(it.new)
                        is RegEvent.NavigateToMainScreen -> onNavigateToMain()
                    }
                }
            }
        }
    }
}

@Immutable
@JvmInline
value class ImmutableList<T>(val value: List<T>)