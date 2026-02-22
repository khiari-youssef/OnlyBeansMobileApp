package com.youapps.search_module.search_list_map.ui.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.ElevatedAssistChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp


@Composable
fun MapSearchAreaChip(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    ElevatedAssistChip(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        label = {
            Text(stringResource(com.youapps.onlybeans.search_module.R.string.search_this_area))
        },
        onClick = onClick,
        colors = AssistChipDefaults.elevatedAssistChipColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            labelColor = MaterialTheme.colorScheme.onSurfaceVariant
        )
    )
}
