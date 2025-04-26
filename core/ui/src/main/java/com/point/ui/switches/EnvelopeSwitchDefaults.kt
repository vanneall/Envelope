package com.point.ui.switches

import androidx.compose.material3.SwitchColors
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.point.ui.colors.new.Black
import com.point.ui.colors.new.EnvelopeTheme
import com.point.ui.colors.new.Gray100
import com.point.ui.colors.new.Gray200
import com.point.ui.colors.new.Gray400

object EnvelopeSwitchDefaults {

    @Composable
    fun colors(): SwitchColors = EnvelopeTheme.envelopeSwitch ?: SwitchDefaults.colors().copy(
        checkedThumbColor = Black,
        checkedTrackColor = Black.copy(alpha = 0.48f),
        uncheckedThumbColor = Gray200,
        uncheckedTrackColor = Gray100,
        disabledCheckedThumbColor = Gray400,
        disabledCheckedTrackColor = Gray200,
        disabledUncheckedThumbColor = Gray400,
        disabledUncheckedTrackColor = Gray100,
        checkedBorderColor = Color.Unspecified,
    )
}