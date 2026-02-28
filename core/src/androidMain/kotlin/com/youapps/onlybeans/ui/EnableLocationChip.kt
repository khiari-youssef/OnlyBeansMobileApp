package com.youapps.onlybeans.ui

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.provider.Settings
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.component1
import androidx.activity.result.component2
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.youapps.onlybeans.R
import com.youapps.onlybeans.di.OBLocationServicePlayServicesImplTag
import com.youapps.onlybeans.platform.OBLocationService
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import com.youapps.onlybeans.designsystem.R as ds


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun EnableLocationChip(
    modifier: Modifier = Modifier,
    onLocationEnabled: () -> Unit
) {
    val locationService: OBLocationService =
        koinInject<OBLocationService>(OBLocationServicePlayServicesImplTag)
    val localContext = LocalContext.current
    val locationPermissions = arrayOf(
        ACCESS_COARSE_LOCATION,
    )
    val localCoroutineScope = rememberCoroutineScope()
    val locationSettingsLauncher: ActivityResultLauncher<Intent> =
        rememberLauncherForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { (resultCode, _) ->
            localCoroutineScope.launch {
                if (resultCode == Activity.RESULT_CANCELED && locationService.isLocationServiceEnabled()) {
                    onLocationEnabled()
                } else {
                    Toast.makeText(
                        localContext,
                        localContext.getString(R.string.random_error),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

    val enabledPermissions: List<Boolean> = locationPermissions.map { permission ->
        localContext.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
    }
    val permissionLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { result ->
            if (result.values.any { it }) {
                locationSettingsLauncher.launch(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                })
            } else {
                Toast.makeText(
                    localContext,
                    localContext.getString(R.string.permission_required),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    AssistChip(
        colors = AssistChipDefaults.elevatedAssistChipColors(
            containerColor = MaterialTheme.colorScheme.primary,
            disabledContainerColor = MaterialTheme.colorScheme.primary.copy(
                alpha = 0.5f
            )
        ),
        modifier = modifier
            .padding(
                horizontal = 12.dp,
                vertical = 8.dp
            )
            .wrapContentSize(),
        shape = RoundedCornerShape(8.dp),
        border = null,
        onClick = {
            if (enabledPermissions.none { it }) {
                permissionLauncher.launch(locationPermissions)
            } else {
                locationSettingsLauncher.launch(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                })
            }
        },
        label = {
            Text(
                text = stringResource(R.string.enable_location_service),
                style = MaterialTheme.typography.labelLarge.copy(
                    color = Color.White
                ),
                textAlign = TextAlign.Start
            )
        },
        leadingIcon = {
            Icon(
                modifier = Modifier
                    .padding(
                        start = 4.dp
                    )
                    .size(
                        height = 14.dp,
                        width = 10.dp
                    ),
                imageVector = ImageVector.vectorResource(ds.drawable.ic_location_pin),
                contentDescription = stringResource(R.string.enable_location_service),
                tint = Color.White
            )
        }
    )
}
