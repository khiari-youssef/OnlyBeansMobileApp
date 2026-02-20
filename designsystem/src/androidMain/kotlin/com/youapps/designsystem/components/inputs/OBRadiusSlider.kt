package com.youapps.designsystem.components.inputs

import androidx.annotation.FloatRange
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun OBRadiusSlider(
    currentValue : Float,
    range : ClosedFloatingPointRange<Float>,
    onValueChange: (Float)-> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Radius: ${currentValue.toInt()} km",
            style = MaterialTheme.typography.headlineSmall
        )

        Slider(
            value = currentValue,
            onValueChange = onValueChange,
            valueRange = range,
            steps = 98, // Optional: makes the slider snap to whole numbers
            colors = SliderDefaults.colors(
                thumbColor = MaterialTheme.colorScheme.primary,
                activeTrackColor = MaterialTheme.colorScheme.primary
            )
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "${range.start.toInt()} km", style = MaterialTheme.typography.bodySmall)
            Text(text = "${range.endInclusive.toInt()} km", style = MaterialTheme.typography.bodySmall)
        }
    }
}