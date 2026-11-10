package com.fabio.callblocker.ui.log

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.fabio.callblocker.CallBlockerApp
import com.fabio.callblocker.data.BlockedCallRepository
import com.fabio.callblocker.data.CallActionMode
import com.fabio.callblocker.data.SettingsRepository
import com.fabio.callblocker.notify.BlockedCallNotifier
import com.fabio.callblocker.tile.BlockingTileService
import com.fabio.callblocker.util.CallGrouper
import com.fabio.callblocker.util.CallSummary
import com.fabio.callblocker.util.DayCallGroup
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class LogUiState(
    val blockingEnabled: Boolean = true,
    val callActionMode: CallActionMode = CallActionMode.REJECT,
    val isRoleHeld: Boolean = true,
    val groups: List<DayCallGroup> = emptyList(),
    val summary: CallSummary = CallSummary(0, 0),
    val isLoading: Boolean = true
)

class LogViewModel(
    private val appContext: Context,
    private val callRepository: BlockedCallRepository,
    private val settingsRepository: SettingsRepository,
    private val notifier: BlockedCallNotifier
) : ViewModel() {

    private val isRoleHeldFlow = MutableStateFlow(true)

    val uiState: StateFlow<LogUiState> = combine(
        settingsRepository.blockingEnabled,
        settingsRepository.callActionMode,
        callRepository.allCalls,
        isRoleHeldFlow
    ) { enabled, mode, calls, roleHeld ->
        LogUiState(
            blockingEnabled = enabled,
            callActionMode = mode,
            isRoleHeld = roleHeld,
            groups = CallGrouper.groupCalls(calls),
            summary = CallGrouper.computeSummary(calls),
            isLoading = false
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = LogUiState()
    )

    init {
        // Ao abrir o log, limpa notificações pendentes
        viewModelScope.launch {
            settingsRepository.resetNotificationCount()
            notifier.cancelNotification()
        }
    }

    fun updateRoleStatus(held: Boolean) {
        isRoleHeldFlow.value = held
    }

    fun setBlockingEnabled(enabled: Boolean) {
        viewModelScope.launch {
            settingsRepository.setBlockingEnabled(enabled)
            BlockingTileService.requestUpdate(appContext)
        }
    }

    fun setCallActionMode(mode: CallActionMode) {
        viewModelScope.launch {
            settingsRepository.setCallActionMode(mode)
        }
    }

    fun deleteCalls(ids: List<Long>) {
        viewModelScope.launch {
            callRepository.deleteByIds(ids)
        }
    }

    fun clearAll() {
        viewModelScope.launch {
            callRepository.clearAll()
        }
    }

    fun resetOnboarding() {
        viewModelScope.launch {
            settingsRepository.setOnboardingDone(false)
        }
    }

    class Factory(private val app: CallBlockerApp) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return LogViewModel(
                appContext = app,
                callRepository = app.blockedCallRepository,
                settingsRepository = app.settingsRepository,
                notifier = app.blockedCallNotifier
            ) as T
        }
    }
}
