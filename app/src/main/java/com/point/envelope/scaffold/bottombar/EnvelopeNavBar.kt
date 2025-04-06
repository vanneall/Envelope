package com.point.envelope.scaffold.bottombar

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.point.ui.Theme
import com.point.ui.colors.White

@Composable
fun EnvelopeNavBar(
    entryPoints: List<BottomBarEntryPoint>,
    selectedItemIndex: Int,
    onItemSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    NavigationBar(
        modifier = modifier,
        containerColor = Theme.colorScheme.background
    ) {
        entryPoints.forEachIndexed { index, item ->
            val selected = index == selectedItemIndex
            NavigationBarItem(
                selected = selected,
                onClick = {
                    onItemSelected(index)
                },
                label = {
                    Text(
                        text = stringResource(item.textId),
                        fontSize = 14.sp,
                        fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal
                    )
                },
                icon = {
                    Icon(
                        imageVector = item.icon(selected),
                        contentDescription = null
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Theme.colorScheme.accent,
                    selectedIconColor = White,
                    selectedTextColor = Theme.colorScheme.accent,
                    unselectedIconColor = Theme.colorScheme.secondary,
                    unselectedTextColor = Theme.colorScheme.secondary,
                )
            )
        }
    }
}