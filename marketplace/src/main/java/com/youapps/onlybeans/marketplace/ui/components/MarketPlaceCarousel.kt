package com.youapps.onlybeans.marketplace.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.youapps.designsystem.OBFontFamilies.BrandFontRegular
import com.youapps.designsystem.components.images.OBCoverPhoto
import com.youapps.onlybeans.marketplace.domain.entities.MarketPlaceNewsCard


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MarketPlaceCarousel(
    modifier: Modifier = Modifier,
    items : List<MarketPlaceNewsCard>,
    onItemClicked : (MarketPlaceNewsCard)-> Unit
) {
    val carouselState = rememberPagerState {
        items.size
    }

    HorizontalPager(
        modifier = modifier,
        state = carouselState,
        pageSpacing = 32.dp,
        contentPadding = PaddingValues(0.dp),
    ) { item->
          val shape =  RoundedCornerShape(16.dp)
        val item = items[item]
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .clip(shape)
        ){
            OBCoverPhoto(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable{
                        onItemClicked(item)
                    },
                url =item.coverImage
            )
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(
                        start = 16.dp,
                        bottom = 16.dp
                    )
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color(0x00000000),
                                Color(0x66000000),
                                Color(0xCC000000)
                            )
                        )
                    )
                    .fillMaxWidth()
                    .wrapContentHeight(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Bottom
            ) {
                Text(
                    text = item.tag,
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontFamily = BrandFontRegular
                    ),
                    color = Color(0xFFFFF59D),
                    textAlign = TextAlign.Start
                )
                Text(
                    text = item.contentDescription,
                    style = TextStyle(
                        fontSize = 36.sp,
                        fontFamily = BrandFontRegular,
                        letterSpacing = 2.sp
                    ),
                    color = Color.White,
                    textAlign = TextAlign.Start
                )
            }
        }
    }
}