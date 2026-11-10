package com.fabio.callblocker.ui

import android.Manifest
import android.app.role.RoleManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.fabio.callblocker.CallBlockerApp
import com.fabio.callblocker.ui.log.LogScreen
import com.fabio.callblocker.ui.log.LogViewModel
import com.fabio.callblocker.ui.onboarding.OnboardingScreen
import com.fabio.callblocker.ui.theme.CallBlockerTheme
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val logViewModel: LogViewModel by viewModels {
        LogViewModel.Factory(application as CallBlockerApp)
    }

    private val requestRoleLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        checkRoleStatus()
    }

    private val requestNotificationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        // Callback de permissão de notificação (opcional)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CallBlockerTheme {
                val app = application as CallBlockerApp
                val onboardingDone by app.settingsRepository.onboardingDone.collectAsState(initial = false)
                val logUiState by logViewModel.uiState.collectAsState()

                if (!onboardingDone) {
                    OnboardingScreen(
                        isRoleHeld = logUiState.isRoleHeld,
                        onRequestRole = { launchRoleRequest() },
                        onRequestNotificationPermission = { launchNotificationPermissionRequest() },
                        onFinish = {
                            lifecycleScope.launch {
                                app.settingsRepository.setOnboardingDone(true)
                            }
                        }
                    )
                } else {
                    LogScreen(
                        viewModel = logViewModel,
                        onRequestRole = { launchRoleRequest() }
                    )
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        checkRoleStatus()
    }

    private fun checkRoleStatus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val roleManager = getSystemService(Context.ROLE_SERVICE) as RoleManager
            val held = roleManager.isRoleHeld(RoleManager.ROLE_CALL_SCREENING)
            logViewModel.updateRoleStatus(held)
        }
    }

    private fun launchRoleRequest() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val roleManager = getSystemService(Context.ROLE_SERVICE) as RoleManager
            val intent = roleManager.createRequestRoleIntent(RoleManager.ROLE_CALL_SCREENING)
            requestRoleLauncher.launch(intent)
        }
    }

    private fun launchNotificationPermissionRequest() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestNotificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }
}
