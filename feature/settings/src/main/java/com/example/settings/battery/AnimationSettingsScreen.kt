package com.example.settings.battery

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun AnimationSettingsScreen(viewModel: BatteryViewModel = hiltViewModel()) {
    val animationsEnabled by viewModel.useAnimations.collectAsState()
    val batteryThreshold by viewModel.batteryThreshold.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Анимации",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.weight(1f)
            )
            Switch(
                checked = animationsEnabled,
                onCheckedChange = { viewModel.onAnimationToggled(it) }
            )
        }

        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Отключать при заряде ниже:",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "${batteryThreshold.toInt()}%",
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            Slider(
                value = batteryThreshold.toFloat(),
                onValueChange = { viewModel.onBatteryThresholdChanged(it.toInt()) },
                valueRange = 0f..100f,
                steps = 98,
                enabled = animationsEnabled
            )
        }
    }
}

