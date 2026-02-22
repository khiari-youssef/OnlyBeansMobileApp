package com.youapps.search_module.search_list_map.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipAnchorPosition
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.CameraUpdateFactory
import com.youapps.onlybeans.search_module.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OBMapRecenterButton(
    modifier: Modifier = Modifier,
    enabled : Boolean = true,
    onClick : ()-> Unit
) {
    val tooltipState = rememberTooltipState()
    val toolTipScope = rememberCoroutineScope()
    LaunchedEffect(enabled) {
        if (enabled.not()) {
            tooltipState.show()
        } else {
            tooltipState.dismiss()
        }
    }
    Box(
        modifier = modifier
    ) {
        TooltipBox(
            modifier = Modifier
                .wrapContentSize(),
            positionProvider = TooltipDefaults.rememberTooltipPositionProvider(
                positioning = TooltipAnchorPosition.Above,
                spacingBetweenTooltipAndAnchor = 8.dp
            ),
            tooltip = {
                PlainTooltip(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    shape = RoundedCornerShape(8.dp),
                    shadowElevation = 1.dp
                ) {
                    Text(
                        if(enabled)
                            stringResource(com.youapps.onlybeans.designsystem.R.string.location_enabled)
                        else stringResource(com.youapps.onlybeans.designsystem.R.string.location_disabled),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.labelSmall,
                        color =  MaterialTheme.colorScheme.onSurface
                    )
                }
            },
            state = tooltipState
        ){
            FloatingActionButton(
                containerColor = if (enabled) MaterialTheme.colorScheme.surface else Color(0xFF808080),
                shape = CircleShape,
                modifier = Modifier
                    .wrapContentSize(),
                onClick = {
                    if (enabled) {
                        onClick()
                    } else {
                        toolTipScope.launch {
                            tooltipState.show()
                        }
                    }
                }
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(com.youapps.onlybeans.designsystem.R.drawable.ic_crosshair),
                    contentDescription = stringResource(R.string.content_description_map_recenter_icon),
                    tint = if(enabled) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
                )
            }
        }
    }

}