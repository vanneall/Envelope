package com.point.envelope.scaffold.topappbar.type

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier

@Immutable
sealed interface AppBarType {

    @Composable
    fun AsComposable(modifier: Modifier)

    data object EmptyAppBar : AppBarType {

        @Composable
        override fun AsComposable(modifier: Modifier) {
            EmptyAppBar(modifier = modifier)
        }
    }

    data class HeaderAppBar(
        @StringRes private val headerRes: Int? = null,
        private val header: String = "",
    ) : AppBarType {

        @Composable
        override fun AsComposable(modifier: Modifier) {
            HeaderAppBar(
                headerRes = headerRes,
                header = header,
                modifier = modifier,
            )
        }
    }

    data class SearchAppBar(
        @StringRes private val placeHolder: Int,
        private val onInput: (String) -> Unit,
    ) : AppBarType {

        @Composable
        override fun AsComposable(modifier: Modifier) {
            SearchAppBar(
                placeholder = placeHolder,
                onValueChange = { onInput(it) },
                modifier = modifier,
            )
        }
    }

    data class UserAppBar(
        private val name: String,
        private val photo: String?,
        private val onUserProfileClick: () -> Unit,
    ) : AppBarType {

        @Composable
        override fun AsComposable(modifier: Modifier) {
            UserAppBar(
                name = name,
                photo = photo,
                modifier = modifier,
            )
        }
    }
}