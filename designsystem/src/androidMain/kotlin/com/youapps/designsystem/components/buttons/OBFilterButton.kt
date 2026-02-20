package com.youapps.designsystem.components.buttons

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.youapps.onlybeans.designsystem.R

@Preview
@Composable
fun OBFilterButton(
    modifier: Modifier = Modifier,
    onClick : ()-> Unit = {}
) {
    Surface(
        modifier = modifier
            .wrapContentSize(),
        color = Color.White,
        border = BorderStroke(1.dp,Color(0xFFe7e5e4)),
        shape = RoundedCornerShape(8.dp)
    ) {
        IconButton(
            modifier = Modifier,
            onClick = onClick
        ) {
            Icon(
                modifier = Modifier
                    .size(24.dp),
                imageVector =ImageVector.vectorResource(R.drawable.ic_filter),
                contentDescription = stringResource(R.string.content_description_filter_button),
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}