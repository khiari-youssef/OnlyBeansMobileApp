package com.youapps.designsystem.components.inputs


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberSearchBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.youapps.onlybeans.designsystem.R
import com.youapps.designsystem.components.text.PlaceholderText


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OBSearchField(
    modifier: Modifier = Modifier,
    placeholderRes: Int,
    query : String = "",
    onSearchQueryChanged : (query : String)->Unit
) {
    val searchBarState = rememberSearchBarState()
    SearchBar(
        modifier =modifier,
        state = searchBarState,
        colors = SearchBarDefaults.colors(
            inputFieldColors = TextFieldDefaults.colors(
                focusedTextColor = Color(0xFF292524),
                unfocusedTextColor = Color(0xFF292524)
            )
        ),
        tonalElevation = 1.dp,
        shadowElevation = 1.dp,
        shape = MaterialTheme.shapes.medium,
        inputField = {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = query,
                onValueChange = onSearchQueryChanged,
                placeholder = {
                    PlaceholderText(
                        text = stringResource(id = placeholderRes),
                        fontSize = 14.sp
                    )
                },
                leadingIcon = {
                    Icon(
                        tint = MaterialTheme.colorScheme.onSurface,
                        imageVector = ImageVector.vectorResource(R.drawable.ic_search),
                        contentDescription = stringResource(R.string.content_description_search_icon),
                    )
                },
                trailingIcon = {
                    Icon(
                        modifier = Modifier
                            .padding(8.dp)
                            .clickable{
                                onSearchQueryChanged("")
                            },
                        imageVector = ImageVector.vectorResource(R.drawable.ic_clear),
                        tint = MaterialTheme.colorScheme.onSurface,
                        contentDescription = stringResource(R.string.content_description_search_clear_icon),
                    )
                },
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedLeadingIconColor = Color(0xFFB45309),
                    unfocusedLeadingIconColor = Color(0xFF78716c),
                    focusedContainerColor = Color(0xFFf5f5f4),
                    unfocusedContainerColor = Color(0xFFf5f5f4),
                    focusedIndicatorColor = Color.Transparent, // Removes the bottom line
                    unfocusedIndicatorColor = Color.Transparent,
                )
            )
        }
    )

}