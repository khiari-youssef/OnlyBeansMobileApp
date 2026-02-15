package com.youapps.designsystem.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.youapps.designsystem.OBFontFamilies


@Composable
fun PageSection(
    modifier: Modifier = Modifier,
    sectionTitle : String?=null,
    rightAction : (@Composable ()-> Unit)?=null,
    content : @Composable ColumnScope.()-> Unit
) {

    Column (
        modifier = modifier
            .wrapContentHeight()
            .fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
    ) {
        sectionTitle?.run {
            if (rightAction != null) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier
                            .wrapContentSize(),
                        text = sectionTitle,
                        style = TextStyle(
                            fontFamily = OBFontFamilies.MainBoldFontFamily,
                            fontSize = 20.sp
                        ),
                        textAlign = TextAlign.Start
                    )
                    rightAction()
                }
            } else {
                Text(
                    modifier = Modifier
                        .wrapContentSize(),
                    text = this,
                    style = TextStyle(
                        fontFamily = OBFontFamilies.MainBoldFontFamily,
                        fontSize = 20.sp
                    ),
                    textAlign = TextAlign.Start
                )
            }

        }
        content()
        }
}