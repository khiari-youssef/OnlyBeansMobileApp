import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.youapps.designsystem.BrickRed
import com.youapps.designsystem.OBFontFamilies


enum class OBButtonSize {
    Small, Medium, Large
}

private val smallButtonHeightRange = 32..36
private val mediumButtonHeightRange = 40..44
private val largeButtonHeightRange = 48..56

fun getHeightForSizeCategory(obButtonSize: OBButtonSize) = when(obButtonSize){
    OBButtonSize.Small -> smallButtonHeightRange
    OBButtonSize.Medium -> mediumButtonHeightRange
    OBButtonSize.Large -> largeButtonHeightRange
}

@Preview
@Composable
fun BtnsPreview(modifier: Modifier = Modifier) {
    Row {
        OBButtonContainedSecondary(
            text = "ContainedSecondary",
            size = OBButtonSize.Large
        ) {

        }
        OBButtonContainedPrimary(
            text = "ContainedPrimary",
            size = OBButtonSize.Medium
        ) {

        }
        OBButtonContainedNeutral(
            text = "ContainedNeutral",
            size = OBButtonSize.Medium
        ) {

        }
    }
}



@Composable
fun OBButtonContainedPrimary(
    modifier: Modifier = Modifier,
    text: String,
    size: OBButtonSize = OBButtonSize.Medium,
    isEnabled: Boolean = true,
    isLoading: Boolean = false,
    icon: Int? = null,
    onClick: () -> Unit
) {
    val heightRange = getHeightForSizeCategory(size)
    OBButton(
        modifier = modifier.heightIn(
            min = heightRange.first.dp,
            max = heightRange.last.dp
        ),
        text = text,
        isLoading = isLoading,
        isEnabled = isEnabled,
        backgroundColor = MaterialTheme.colorScheme.primary,
        onClick = onClick,
        icon = icon,
        fontSize = when (size) {
            OBButtonSize.Large -> 18.sp
            OBButtonSize.Medium -> 16.sp
            OBButtonSize.Small -> 14.sp
        },
        paddingValues = when (size) {
            OBButtonSize.Large -> PaddingValues(
                horizontal = 16.dp,
                vertical = 12.dp
            )

            OBButtonSize.Medium -> PaddingValues(
                horizontal = 16.dp,
                vertical = 8.dp
            )

            OBButtonSize.Small -> PaddingValues(
                horizontal = 12.dp,
                vertical = 4.dp
            )
        }
    )
}

@Composable
fun OBButtonContainedNeutral(
    modifier: Modifier = Modifier,
    text: String,
    size: OBButtonSize = OBButtonSize.Medium,
    isEnabled: Boolean = true,
    isLoading: Boolean = false,
    icon: Int? = null,
    onClick: () -> Unit
) {
    val heightRange = getHeightForSizeCategory(size)
    OBButton(
        modifier = modifier.heightIn(
            min = heightRange.first.dp,
            max = heightRange.last.dp
        ),
        text = text,
        isLoading = isLoading,
        isEnabled = isEnabled,
        backgroundColor = Color(0xFFb3b3b3),
        onClick = onClick,
        fontColor = Color.Black,
        icon = icon,
        fontSize = when (size) {
            OBButtonSize.Large -> 18.sp
            OBButtonSize.Medium -> 16.sp
            OBButtonSize.Small -> 14.sp
        },
        paddingValues = when (size) {
            OBButtonSize.Large -> PaddingValues(
                horizontal = 16.dp,
                vertical = 12.dp
            )

            OBButtonSize.Medium -> PaddingValues(
                horizontal = 16.dp,
                vertical = 8.dp
            )

            OBButtonSize.Small -> PaddingValues(
                horizontal = 12.dp,
                vertical = 4.dp
            )
        },
    )
}

@Composable
fun OBButtonContainedSecondary(
    modifier: Modifier = Modifier,
    text: String,
    size: OBButtonSize = OBButtonSize.Medium,
    isEnabled: Boolean = true,
    isLoading: Boolean = false,
    icon: Int? = null,
    onClick: () -> Unit
) {
    val heightRange = getHeightForSizeCategory(size)
    OBButton(
        modifier = modifier.heightIn(
            min = heightRange.first.dp,
            max = heightRange.last.dp
        ),
        text = text,
        isLoading = isLoading,
        isEnabled = isEnabled,
        backgroundColor = BrickRed,
        fontSize = when (size) {
            OBButtonSize.Large -> 18.sp
            OBButtonSize.Medium -> 16.sp
            OBButtonSize.Small -> 14.sp
        },
        icon = icon,
        onClick = onClick,
        paddingValues = when (size) {
            OBButtonSize.Large -> PaddingValues(
                horizontal = 16.dp,
                vertical = 12.dp
            )

            OBButtonSize.Medium -> PaddingValues(
                horizontal = 16.dp,
                vertical = 8.dp
            )

            OBButtonSize.Small -> PaddingValues(
                horizontal = 12.dp,
                vertical = 4.dp
            )
        },
    )
}


@Composable
fun OBTextButton(
    modifier: Modifier = Modifier,
    text: String,
    style: TextStyle = MaterialTheme.typography.labelMedium,
    onClick: () -> Unit
) {
    TextButton(
        onClick = onClick,
        modifier = modifier,
        contentPadding = PaddingValues(4.dp)
    ) {
        Text(
            text,
            style = style,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun OBButton(
    modifier: Modifier = Modifier,
    text: String,
    icon: Int? = null,
    iconDescription: String? = null,
    backgroundColor: Color,
    border: BorderStroke? = null,
    isEnabled: Boolean = true,
    isLoading: Boolean = false,
    paddingValues: PaddingValues = PaddingValues(
        horizontal = 16.dp,
        vertical = 8.dp
    ),
    fontSize: TextUnit = 16.sp,
    fontColor: Color = Color.White,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier,
        enabled = isEnabled or isLoading,
        shape = MaterialTheme.shapes.medium,
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
        ),
        border = border,

        contentPadding = paddingValues,
        onClick = onClick
    ) {
        Crossfade(
            modifier = Modifier.height(20.dp),
            targetState = isLoading,
            label = "ButtonLoading",
            animationSpec = spring()
        ) {
            if (it) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .semantics {
                            contentDescription = "OBButtonLoadingCircularProgressBar"
                        }
                        .size(20.dp),
                    strokeWidth = 2.dp,
                    color = Color.White
                )
            } else {
                icon?.run {
                    Row(
                        modifier = Modifier.wrapContentSize(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = icon),
                            contentDescription = iconDescription,
                            tint = fontColor,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = text,
                            style = TextStyle(
                                color = fontColor,
                                fontFamily = OBFontFamilies.MainMediumFontFamily,
                                fontSize = fontSize
                            )
                        )
                    }
                } ?: Text(
                    text = text,
                    style = TextStyle(
                        color = fontColor,
                        fontFamily = OBFontFamilies.MainMediumFontFamily,
                        fontSize = fontSize
                    )
                )

            }
        }


    }
}