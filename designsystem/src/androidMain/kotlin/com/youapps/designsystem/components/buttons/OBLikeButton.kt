package com.youapps.designsystem.components.buttons

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
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


@Composable
fun OBLikeButton(
    modifier: Modifier = Modifier,
    isLiked : Boolean = false,
    onClick : (Boolean)->Unit
) {

    val backgroundColor = if (isLiked) Color(0xFFFFE8F0) else MaterialTheme.colorScheme.surfaceContainerHigh
    val animatedBackgroundColor = animateColorAsState(targetValue = backgroundColor)

    val borderColor = if (isLiked) Color(0x80F585AB) else MaterialTheme.colorScheme.outline
    val animatedBorderColor = animateColorAsState(targetValue = borderColor)
    Surface(
        modifier = modifier
            .wrapContentSize(),
        color = animatedBackgroundColor.value,
        border = BorderStroke(width = 1.dp, color = animatedBorderColor.value),
        shape = RoundedCornerShape(8.dp)
    ) {
        IconButton(
            onClick = {
                onClick(isLiked.not())
            }
        ) {
            AnimatedContent(
                targetState = isLiked,
                transitionSpec = {
                    // Combine scale and fade for a "pop" effect
                    (fadeIn(animationSpec = tween(220)) + scaleIn())
                        .togetherWith(fadeOut(animationSpec = tween(220)) + scaleOut())
                },
                label = "IconTransition"
            ) { targetState ->
                Icon(
                    imageVector = if (targetState) ImageVector.vectorResource(R.drawable.ic_heart_filled) else  ImageVector.vectorResource(R.drawable.ic_heart_outlined),
                    contentDescription = stringResource(R.string.content_description_like_button),
                    tint = if (targetState) Color(0xFFE91E63) else MaterialTheme.colorScheme.outline
                )
            }
        }


    }
}


@Composable
fun OBLikeCardButton(
    modifier: Modifier = Modifier,
    isLiked : Boolean = false,
    onClick : (Boolean)->Unit
) {

    val backgroundColor = if (isLiked) Color(0xFFFFE8F0) else Color(0xFFDDE3E1)
    val animatedBackgroundColor = animateColorAsState(targetValue = backgroundColor)

    val borderColor = if (isLiked) Color(0x80F585AB) else MaterialTheme.colorScheme.outline
    val animatedBorderColor = animateColorAsState(targetValue = borderColor)
    Surface(
        modifier = modifier
            .wrapContentSize(),
        color = animatedBackgroundColor.value,
        border = if (isLiked) null else BorderStroke(width = 1.dp, color = animatedBorderColor.value),
        shape = CircleShape
    ) {
        IconButton(
            modifier = Modifier
                .size(35.dp),
            onClick = {
                onClick(isLiked.not())
            }
        ) {
            AnimatedContent(
                targetState = isLiked,
                transitionSpec = {
                    // Combine scale and fade for a "pop" effect
                    (fadeIn(animationSpec = tween(220)) + scaleIn())
                        .togetherWith(fadeOut(animationSpec = tween(220)) + scaleOut())
                },
                label = "IconTransition"
            ) { targetState ->
                Icon(
                    modifier = Modifier
                        .size(20.dp),
                    imageVector = if (targetState) ImageVector.vectorResource(R.drawable.ic_heart_filled) else  ImageVector.vectorResource(R.drawable.ic_heart_outlined),
                    contentDescription = stringResource(R.string.content_description_like_button),
                    tint = if (targetState) Color(0xFFE91E63) else Color(0xFFA89F9A)
                )
            }
        }


    }
}