package com.point.ui.materials.buttons.styled

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.point.ui.body14

enum class ButtonStyle(val height: Dp, val textStyle: TextStyle, val corners: Dp, val horizontalPadding: Dp) {
    S(height = 34.dp, textStyle = body14, corners = 12.dp, horizontalPadding = 16.dp),
    M(height = 40.dp, textStyle = body14, corners = 16.dp, horizontalPadding = 24.dp),
}