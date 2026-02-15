package com.youapps.designsystem.components
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.youapps.designsystem.OBFontFamilies
import com.youapps.onlybeans.designsystem.R


@Preview
@Composable
fun AppBrand(
    modifier: Modifier = Modifier
) {
Row(
    modifier = modifier,
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(8.dp,Alignment.CenterHorizontally)
) {
    Text(
        modifier = Modifier.wrapContentSize(),
        text = stringResource(id = R.string.app_name),
        style = TextStyle(
            fontSize = 24.sp,
            fontFamily = OBFontFamilies.BrandFontRegular,
            fontWeight = FontWeight.W700,
            fontStyle = FontStyle.Normal,
            color = MaterialTheme.colorScheme.primary,
        )
    )
}
}