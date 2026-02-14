package com.youapps.designsystem.components.inputs

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.youapps.onlybeans.designsystem.R



@Composable
fun OBCounter(
    modifier: Modifier = Modifier,
    initialCount : Int = 0,
    maxCount : Int = Int.MAX_VALUE,
    minCount : Int = 0,
    onCountChanged: (Int)-> Unit
) {
    var currentCount  by remember {
        mutableIntStateOf(initialCount)
    }
    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.surfaceContainer,
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
           modifier = Modifier
               .padding(
                   horizontal = 8.dp,
                   vertical = 8.dp
               )
               .wrapContentSize(),
           verticalAlignment = Alignment.CenterVertically,
           horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_remove) ,
                contentDescription = stringResource(R.string.content_description_decrease_counter_input),
                tint = if (currentCount > minCount) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurface.copy(
                    alpha = 0.5f
                ),
                modifier = Modifier
                    .padding(
                        8.dp
                    )
                    .clickable{
                        if (currentCount > minCount) {
                            currentCount--
                            onCountChanged(currentCount)
                        }
                    }
                    .size(16.dp)
            )
            currentCount.toString().forEach { char ->
                AnimatedContent(
                    modifier = Modifier
                        .wrapContentSize(),
                    targetState = char,
                    transitionSpec = {
                        // Slide in from bottom, slide out to top
                        if (targetState > initialState) {
                            slideInVertically { it } + fadeIn() togetherWith
                                    slideOutVertically { -it } + fadeOut()
                        } else {
                            // Slide in from top, slide out to bottom
                            slideInVertically { -it } + fadeIn() togetherWith
                                    slideOutVertically { it } + fadeOut()
                        }
                    },
                    label = "CountAnimation"
                ) { targetChar ->
                    Text(
                        modifier = Modifier
                            .wrapContentSize(),
                        text = targetChar.toString(),
                        softWrap = false,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_add) ,
                contentDescription = stringResource(R.string.content_description_increase_counter_input),
                tint = if (currentCount < maxCount) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurface.copy(
                    alpha = 0.5f
                ),
                modifier = Modifier
                    .padding(
                        8.dp
                    )
                    .clickable{
                        if (currentCount < maxCount) {
                            currentCount++
                            onCountChanged(currentCount)
                        }
                    }
                    .size(16.dp)
            )

        }
    }
}