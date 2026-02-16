package com.youapps.onlybeans.marketplace.ui.components


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.youapps.onlybeans.R



@Composable
fun OBProductRatingTag(
    modifier: Modifier = Modifier,
    rating: Float,
    reviewCount: Int,
    backgroundColor : Color = Color.Transparent,
     starColor : Color= Color(0xFFFFA000), // Deep Orange/Amber
     textColor : Color = Color(0xFF8B4513)
) {


    Row(
        modifier = modifier
            .background(color = backgroundColor, shape = RoundedCornerShape(8.dp))
            .padding(horizontal = 4.dp, vertical = 2.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        // The Star Icon
        Icon(
            imageVector = ImageVector.vectorResource(com.youapps.onlybeans.designsystem.R.drawable.ic_rating_star),
            contentDescription = stringResource(com.youapps.onlybeans.designsystem.R.string.content_description_rating_star_icon),
            tint = starColor,
            modifier = Modifier.size(16.dp),
        )

        // The Rating Score
        Text(
            text = rating.toString(),
            color = textColor,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            autoSize = TextAutoSize.StepBased(
                minFontSize = 12.sp,
                maxFontSize = 16.sp,
                stepSize = 1.sp
            )
        )

        // The Review Count
        Text(
            text = "($reviewCount)",
            color = textColor.copy(alpha = 0.8f),
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            autoSize = TextAutoSize.StepBased(
                minFontSize = 12.sp,
                maxFontSize = 16.sp,
                stepSize = 1.sp
            )
        )
    }
}