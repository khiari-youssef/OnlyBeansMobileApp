package com.youapps.onlybeans.ui.product.components


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
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.youapps.onlybeans.R



@Composable
fun OBProductRatingTag(
    modifier: Modifier = Modifier,
    rating: Float,
    reviewCount: Int
) {
    // Defining the specific colors from your image
    val starColor = Color(0xFFFFA000) // Deep Orange/Amber
    val textColor = Color(0xFF8B4513) // Brownish-orange text
    val backgroundColor = Color(0xFFFFFBE6) // Light cream background

    Row(
        modifier = modifier
            .background(color = backgroundColor, shape = RoundedCornerShape(8.dp))
            .padding(horizontal = 4.dp, vertical = 2.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        // The Star Icon
        Icon(
            imageVector = ImageVector.vectorResource(com.youapps.designsystem.R.drawable.ic_rating_star),
            contentDescription = "Rating",
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