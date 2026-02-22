package com.youapps.search_module.search_list_map.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.youapps.onlybeans.search_module.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OBMapLocationToggleButton(
    modifier: Modifier = Modifier,
    isLocationEnabled : Boolean,
    onLocationToggle : ()-> Unit
) {
    val tooltipState = rememberTooltipState()
    val animatedColor by animateColorAsState(
        targetValue =  if (isLocationEnabled) Color.Green else Color.Red,
        animationSpec = tween(durationMillis = 500), // Optional: customize speed
        label = "IconColorAnimation"
    )
    LaunchedEffect(isLocationEnabled) {
        tooltipState.show()
    }
    TooltipBox(
        modifier = modifier
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
                    if(isLocationEnabled)
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
            containerColor = MaterialTheme.colorScheme.surface,
            shape = CircleShape,
            modifier = Modifier
                .wrapContentSize(),
            onClick = onLocationToggle
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(com.youapps.onlybeans.designsystem.R.drawable.ic_location_pin),
                contentDescription = stringResource(R.string.content_description_map_enable_location_icon),
                tint = animatedColor
            )
        }
    }

}