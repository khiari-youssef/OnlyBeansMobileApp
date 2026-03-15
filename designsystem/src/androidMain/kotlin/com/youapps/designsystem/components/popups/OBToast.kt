package com.youapps.designsystem.components.popups

import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.youapps.designsystem.components.themev2.md_theme_dark_error
import com.youapps.designsystem.components.themev2.md_theme_dark_success
import com.youapps.designsystem.components.themev2.md_theme_light_error
import com.youapps.designsystem.components.themev2.md_theme_light_success
import com.youapps.onlybeans.designsystem.R




enum class OBToastType {
    Info,Error,Success
}

data class OBToastData (
    val message : String?=null,
    val resID : Int? = null,
    val type: OBToastType = OBToastType.Info,
)

@Composable
fun OBToast(
    modifier: Modifier = Modifier,
    data : OBToastData,
    onDismissRequest: () -> Unit
) {
    data.message?.takeIf { it.isNotBlank() }?.run {
        Surface(
            modifier = modifier,
            color = when(data.type){
                OBToastType.Info -> if (isSystemInDarkTheme()) Color(0xFF0046A8) else Color(
                    0xFF2573DC
                )
                OBToastType.Success -> if (isSystemInDarkTheme()) md_theme_dark_success else md_theme_light_success
                OBToastType.Error -> if (isSystemInDarkTheme()) md_theme_dark_error else md_theme_light_error
            },
            shape = MaterialTheme.shapes.medium
        ) {
            ConstraintLayout(
                modifier = Modifier
                    .zIndex(4f)
                    .padding(
                       horizontal = 12.dp,
                      vertical = 8.dp
                    )
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                val (iconRef, textRef, closeIconRef) = createRefs()
                data.resID?.run {
                    Icon(
                        modifier = Modifier
                            .semantics {
                                contentDescription = "ToastVariantIcon"
                            }
                            .constrainAs(iconRef) {
                                start.linkTo(parent.start)
                                top.linkTo(parent.top)
                                bottom.linkTo(parent.bottom)
                            }
                            .requiredSize(20.dp),
                        imageVector = ImageVector.vectorResource(
                            data.resID
                        ),
                        contentDescription = null,
                        tint =  Color.White
                    )
                }
                Text(
                    modifier = Modifier
                        .semantics {
                            contentDescription = "ToastTextContent"
                        }
                        .constrainAs(textRef) {
                            start.linkTo(iconRef.end, 8.dp)
                            end.linkTo(closeIconRef.start, 8.dp)
                            width = Dimension.fillToConstraints
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                        },
                    text = data.message,
                    style = MaterialTheme.typography.bodyMedium
                )
                Icon(
                    modifier = Modifier
                        .constrainAs(closeIconRef) {
                            end.linkTo(parent.end)
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                        }
                        .clickable(onClick = onDismissRequest)
                        .requiredSize(20.dp),
                    imageVector = ImageVector.vectorResource(
                        R.drawable.ic_clear
                    ),
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }
    }

}
