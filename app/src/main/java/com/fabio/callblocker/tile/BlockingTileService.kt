package com.fabio.callblocker.tile

import android.content.ComponentName
import android.content.Context
import android.os.Build
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import com.fabio.callblocker.CallBlockerApp
import com.fabio.callblocker.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class BlockingTileService : TileService() {

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    companion object {
        fun requestUpdate(context: Context) {
            try {
                requestListeningState(
                    context,
                    ComponentName(context, BlockingTileService::class.java)
                )
            } catch (_: Exception) {
            }
        }
    }

    override fun onStartListening() {
        super.onStartListening()
        updateTileState()
    }

    override fun onClick() {
        super.onClick()
        val app = applicationContext as CallBlockerApp
        serviceScope.launch {
            val current = app.settingsRepository.blockingEnabled.first()
            val new = !current
            app.settingsRepository.setBlockingEnabled(new)
            applyTileState(new)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }

    private fun updateTileState() {
        val app = applicationContext as CallBlockerApp
        serviceScope.launch {
            val enabled = app.settingsRepository.blockingEnabled.first()
            applyTileState(enabled)
        }
    }

    private fun applyTileState(enabled: Boolean) {
        val tile = qsTile ?: return
        tile.state = if (enabled) Tile.STATE_ACTIVE else Tile.STATE_INACTIVE
        tile.label = getString(R.string.tile_label)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            tile.subtitle = if (enabled) {
                getString(R.string.tile_state_active)
            } else {
                getString(R.string.tile_state_inactive)
            }
        }
        tile.updateTile()
    }
}
