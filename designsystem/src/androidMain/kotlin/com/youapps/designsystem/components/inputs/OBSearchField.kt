package com.youapps.designsystem.components.inputs


import androidx.compose.foundation.layout.fillMaxSize
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
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            )
        ),
        shape = MaterialTheme.shapes.medium,
        inputField = {
            TextField(
                modifier = Modifier.fillMaxSize(),
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
                        imageVector = ImageVector.vectorResource(R.drawable.ic_search),
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.secondary
                    )
                },
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent, // Removes the bottom line
                    unfocusedIndicatorColor = Color.Transparent,
                )
            )
        }
    )

}