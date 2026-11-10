package com.fabio.callblocker.screening

import android.telecom.Call
import android.telecom.CallScreeningService
import com.fabio.callblocker.CallBlockerApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeoutOrNull

class BlockerScreeningService : CallScreeningService() {

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onScreenCall(callDetails: Call.Details) {
        // Se for chamada de saída, permite imediatamente
        if (callDetails.callDirection != Call.Details.DIRECTION_INCOMING) {
            respondToCall(callDetails, allowCallResponse())
            return
        }

        val app = applicationContext as CallBlockerApp

        serviceScope.launch {
            val enabled = app.settingsRepository.blockingEnabled.first()
            if (!enabled) {
                respondToCall(callDetails, allowCallResponse())
                return@launch
            }

            val rawNumber = extractNumber(callDetails)
            val now = System.currentTimeMillis()

            try {
                // Timeout de segurança para garantir a resposta ao sistema bem antes do limite (~5s)
                withTimeoutOrNull(2000L) {
                    app.blockedCallRepository.insert(rawNumber, now)
                    val newCount = app.settingsRepository.recordBlockedForNotification(rawNumber, now)
                    app.blockedCallNotifier.showBlockedNotification(newCount, rawNumber, now)
                }
            } finally {
                // Rejeita a chamada, suprime notificação de perdida do sistema, mas mantém no histórico
                respondToCall(callDetails, blockCallResponse())
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }

    private fun extractNumber(details: Call.Details): String? {
        val handle = details.handle ?: return null
        val number = handle.schemeSpecificPart
        return if (number.isNullOrBlank()) null else number
    }

    private fun allowCallResponse(): CallResponse {
        return CallResponse.Builder().build()
    }

    private fun blockCallResponse(): CallResponse {
        return CallResponse.Builder()
            .setDisallowCall(true)
            .setRejectCall(true)
            .setSkipCallLog(false) // Mantém no histórico do discador do sistema
            .setSkipNotification(true) // Evita que o discador mostre "chamada perdida"
            .build()
    }
}

