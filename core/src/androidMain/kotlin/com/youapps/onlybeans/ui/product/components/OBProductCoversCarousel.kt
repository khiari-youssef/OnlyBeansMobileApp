package com.youapps.onlybeans.ui.product.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.youapps.designsystem.components.images.OBCoverPhoto
import com.youapps.designsystem.components.loading.CarouselDotIndicator
import com.youapps.onlybeans.designsystem.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OBProductCoversCarousel(
    modifier: Modifier = Modifier,
    preferredItemWidth : Dp,
    covers: List<String>,
    onPhotoClicked : (String)-> Unit
) {
    if (covers.isNotEmpty()){
        val carouselState = rememberCarouselState {
            covers.size
        }
        Box(
            modifier = modifier,
        ){
            HorizontalMultiBrowseCarousel(
                state = carouselState,
                modifier = Modifier,
                preferredItemWidth = preferredItemWidth,
                itemSpacing = 0.dp,
                contentPadding = PaddingValues(0.dp),
            ) { item->
                val photo = covers[item]
                OBCoverPhoto(
                    modifier = Modifier
                        .clickable{
                            onPhotoClicked(photo)
                        },
                    url =photo
                )
            }
            CarouselDotIndicator(
                pageCount = covers.size,
                currentPage = carouselState.currentItem,
                modifier = Modifier
                    .padding(
                        bottom = 12.dp
                    )
                    .align(Alignment.BottomCenter),
                selectedColor = Color.White ,
                unselectedColor = Color(0x80ffffff)
            )
        }
    } else {
        Image(
            modifier = Modifier
                .height(200.dp),
            painter = painterResource(R.drawable.placeholder_cover),
            contentDescription = ""
        )
    }

}