package com.example.settings.accessibility

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.settings.R
import com.point.settings.model.LetterSpacingPresetSettings
import com.point.ui.Theme

@Composable
fun AccessibilityScreen(
    viewModel: AccessibilityViewModel = hiltViewModel()
) {
    val selected by viewModel.selectedPreset.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = stringResource(R.string.dyslexia),
            style = Theme.typography.titleS,
            modifier = Modifier.padding(vertical = 4.dp, horizontal = 16.dp)
        )

        Text(
            text = stringResource(R.string.dislecsia_description),
            style = Theme.typography.bodyM,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        LetterSpacingPresetSettings.entries.forEach { preset ->
            LetterSpacingOptionItem(
                preset = preset,
                displayName = preset.displayName(),
                isSelected = preset == selected,
                onClick = { viewModel.onPresetSelected(preset) }
            )
        }

        HorizontalDivider(modifier = Modifier.fillMaxWidth())
    }
}

@Composable
fun LetterSpacingOptionItem(
    preset: LetterSpacingPresetSettings,
    displayName: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(),
                onClick = onClick
            )
            .padding(vertical = 12.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = displayName,
            style = Theme.typography.bodyM,
            modifier = Modifier.weight(1f)
        )
        if (isSelected) {
            Icon(
                imageVector = Icons.Rounded.Check,
                contentDescription = null,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
}

private fun LetterSpacingPresetSettings.displayName(): String = when (this) {
    LetterSpacingPresetSettings.NORMAL -> "Normal"
    LetterSpacingPresetSettings.MEDIUM -> "Medium"
    LetterSpacingPresetSettings.WIDE -> "Wide"
    LetterSpacingPresetSettings.EXTRA_WIDE -> "Extra Wide"
}
