package com.point.envelope

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.BatteryManager
import com.point.settings.repository.AppSettingsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

class BatteryLevelReceiver(
    private val context: Context,
    private val appSettingsRepository: AppSettingsRepository
) : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != Intent.ACTION_BATTERY_CHANGED) return

        val level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
        val scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
        if (level == -1 || scale == -1) return

        val batteryPct = (level * 100f / scale).roundToInt()

        CoroutineScope(Dispatchers.IO).launch {
            val threshold = appSettingsRepository.getSettings().first().batteryThreshold
            val shouldDisable = batteryPct < threshold
            appSettingsRepository.changeAnimationUsage(!shouldDisable)
        }
    }
}