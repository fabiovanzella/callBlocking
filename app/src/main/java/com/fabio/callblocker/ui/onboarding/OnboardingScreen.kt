package com.fabio.callblocker.ui.onboarding

import android.app.StatusBarManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BatteryAlert
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.TouchApp
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.fabio.callblocker.R
import com.fabio.callblocker.tile.BlockingTileService

@Composable
fun OnboardingScreen(
    isRoleHeld: Boolean,
    onRequestRole: () -> Unit,
    onRequestNotificationPermission: () -> Unit,
    onFinish: () -> Unit
) {
    val context = LocalContext.current
    var currentStep by remember { mutableIntStateOf(0) }

    val isXiaomi = remember {
        val manufacturer = Build.MANUFACTURER.lowercase()
        manufacturer.contains("xiaomi") || manufacturer.contains("redmi") || manufacturer.contains("poco")
    }

    val totalSteps = if (isXiaomi) 4 else 3

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                when (currentStep) {
                    0 -> StepCard(
                        icon = Icons.Default.Security,
                        title = stringResource(R.string.onboarding_role_title),
                        description = stringResource(R.string.onboarding_role_desc),
                        actionButton = {
                            if (isRoleHeld) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        Icons.Default.CheckCircle,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = stringResource(R.string.onboarding_role_granted),
                                        color = MaterialTheme.colorScheme.primary,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            } else {
                                Button(
                                    onClick = onRequestRole,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(stringResource(R.string.onboarding_btn_role))
                                }
                            }
                        }
                    )
                    1 -> StepCard(
                        icon = Icons.Default.Notifications,
                        title = stringResource(R.string.onboarding_notif_title),
                        description = stringResource(R.string.onboarding_notif_desc),
                        actionButton = {
                            Button(
                                onClick = onRequestNotificationPermission,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(stringResource(R.string.onboarding_btn_notif))
                            }
                        }
                    )
                    2 -> {
                        if (isXiaomi) {
                            StepCard(
                                icon = Icons.Default.BatteryAlert,
                                title = stringResource(R.string.onboarding_xiaomi_title),
                                description = stringResource(R.string.onboarding_xiaomi_desc),
                                actionButton = {
                                    Button(
                                        onClick = { openXiaomiSettings(context) },
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Text(stringResource(R.string.onboarding_btn_xiaomi))
                                    }
                                }
                            )
                        } else {
                            TileStepCard(context)
                        }
                    }
                    3 -> TileStepCard(context)
                }
            }

            // Barra inferior com navegação
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (currentStep > 0) {
                    OutlinedButton(onClick = { currentStep-- }) {
                        Text(stringResource(R.string.btn_back))
                    }
                } else {
                    Spacer(modifier = Modifier.width(1.dp))
                }

                if (currentStep < totalSteps - 1) {
                    Button(
                        onClick = { currentStep++ },
                        enabled = (currentStep != 0 || isRoleHeld)
                    ) {
                        Text(stringResource(R.string.btn_next))
                    }
                } else {
                    Button(onClick = onFinish) {
                        Text(stringResource(R.string.onboarding_btn_finish))
                    }
                }
            }
        }
    }
}

@Composable
private fun TileStepCard(context: Context) {
    StepCard(
        icon = Icons.Default.TouchApp,
        title = stringResource(R.string.onboarding_tile_title),
        description = stringResource(R.string.onboarding_tile_desc),
        actionButton = {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                Button(
                    onClick = {
                        val sbm = context.getSystemService(StatusBarManager::class.java)
                        sbm?.requestAddTileService(
                            ComponentName(context, BlockingTileService::class.java),
                            context.getString(R.string.tile_label),
                            android.graphics.drawable.Icon.createWithResource(
                                context,
                                android.R.drawable.ic_lock_lock
                            ),
                            context.mainExecutor
                        ) {}
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(stringResource(R.string.onboarding_btn_add_tile))
                }
            }
        }
    )
}

@Composable
private fun StepCard(
    icon: ImageVector,
    title: String,
    description: String,
    actionButton: @Composable () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        )
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(64.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(24.dp))
            actionButton()
        }
    }
}

private fun openXiaomiSettings(context: Context) {
    try {
        val intent = Intent().apply {
            component = ComponentName(
                "com.miui.securitycenter",
                "com.miui.permcenter.autostart.AutoStartManagementActivity"
            )
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        context.startActivity(intent)
    } catch (_: Exception) {
        // Fallback para tela de detalhes do app
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = Uri.fromParts("package", context.packageName, null)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        context.startActivity(intent)
    }
}
