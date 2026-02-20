package com.youapps.search_module.search_list_map.ui.components

import OBButton
import OBButtonContainedSecondary
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.youapps.designsystem.components.inputs.OBRadiusSlider
import com.youapps.designsystem.components.menus.OBFilterMenu
import com.youapps.designsystem.components.text.PlaceholderText
import com.youapps.onlybeans.search_module.R
import com.youapps.onlybeans.designsystem.R as ds
import com.youapps.search_module.search_list_map.ui.community_search_state.SearchFilterList






@Composable
fun OBSearchFilterDialog(
    modifier: Modifier = Modifier,
    isVisible : Boolean,
    categoryFilters : SearchFilterList?,
    selectedFilterIndex : Int,
    currentRadiusValue : Float,
    onDismiss : ()-> Unit,
    onApply : (onSelectedFilterIndex : Int,radiusValue : Float)-> Unit
) {
    if (isVisible){
        Dialog(
            onDismissRequest = onDismiss,
            content = {
                Surface(
                    modifier = modifier,
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = RoundedCornerShape(16.dp)
                ){
                    var selectedFilterIndex by remember(selectedFilterIndex) {
                        mutableIntStateOf(selectedFilterIndex)
                    }
                    var selectedRadiusValue by remember {
                        mutableFloatStateOf(currentRadiusValue)
                    }
                    Column(
                        modifier = Modifier
                            .padding(
                                horizontal = 12.dp,
                                vertical = 16.dp
                            )
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ){
                        Text(
                            text = stringResource(ds.string.categories_label),
                            textAlign = TextAlign.Start,
                            style = MaterialTheme.typography.titleMedium
                        )
                        categoryFilters?.data?.run {
                            OBFilterMenu(
                                modifier = Modifier,
                                filters = this,
                                selectedFilterIndex = selectedFilterIndex,
                                onFilterSelected = {
                                    selectedFilterIndex = it
                                }
                            )
                        } ?: run {
                            PlaceholderText(
                                text = stringResource(ds.string.filters_not_available),
                                fontSize = 12.sp
                            )
                        }
                        Text(
                            text = stringResource(ds.string.radius_label),
                            textAlign = TextAlign.Start,
                            style = MaterialTheme.typography.titleMedium
                        )
                        OBRadiusSlider(
                            currentValue = selectedRadiusValue,
                            range = 1f..100f,
                            onValueChange = {
                                selectedRadiusValue = it
                            }
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            OBButtonContainedSecondary(
                                text = stringResource(ds.string.apply_button),
                                onClick = {
                                    onApply(selectedFilterIndex,currentRadiusValue)
                                    onDismiss()
                                }
                            )
                        }
                    }
                }
            }
        )
    }
}