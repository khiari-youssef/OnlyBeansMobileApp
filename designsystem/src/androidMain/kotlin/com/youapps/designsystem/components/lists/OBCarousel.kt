package com.youapps.designsystem.components.lists

import OBButtonContainedNeutral
import OBButtonContainedPrimary
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.youapps.designsystem.R
import com.youapps.designsystem.components.images.OBCoverPhoto
import com.youapps.designsystem.components.loading.shimmerEffect
import com.youapps.designsystem.components.media.OBVideoPlayer
import com.youapps.designsystem.components.text.PlaceholderText
import com.youapps.designsystem.R as ds

enum class OBCarouselMediaType{
    Image,Video
}
sealed interface CarouselState{
    @Immutable
    object Loading: CarouselState

    @Immutable
    data class Loaded(val medias: List<Pair<OBCarouselMediaType,String>>): CarouselState
}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OBCarousel(
    modifier: Modifier,
    state : CarouselState,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    itemSpacing: Dp = 8.dp,
    preferredItemWidth: Dp,
    onItemClicked : ((OBCarouselMediaType,String)-> Unit)?=null
) {
    val carouselState = rememberCarouselState {
      when(state){
          is CarouselState.Loading -> 3
          is CarouselState.Loaded -> state.medias.size
      }
    }

    HorizontalMultiBrowseCarousel(
        state = carouselState,
        modifier = modifier,
        preferredItemWidth = preferredItemWidth,
        itemSpacing = itemSpacing,
        contentPadding = contentPadding
    ) { index ->
        when(state){
            is CarouselState.Loading ->OBCoverPhoto(
                modifier = Modifier
                    .shimmerEffect(true)
                    .height(206.dp),
                url = ""
            )
            is CarouselState.Loaded -> {

                when (val mediaType = state.medias[index].first) {
                    OBCarouselMediaType.Image ->  OBCoverPhoto(
                        modifier = Modifier
                            .clickable(enabled = onItemClicked != null){
                                onItemClicked?.invoke(mediaType,state.medias[index].second)
                            }
                            .height(206.dp),
                        url = state.medias[index].second
                    )
                    OBCarouselMediaType.Video -> OBVideoPlayer(
                        modifier = Modifier
                            .height(206.dp)
                            .fillMaxWidth(),
                        videoUri = state.medias[index].second
                    )
                }

            }
        }

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OBCarouselEditable(
    modifier: Modifier,
    state : CarouselState.Loaded,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    itemSpacing: Dp = 8.dp,
    preferredItemWidth: Dp,
    imagePickLimit: Int = 5,
    onGalleryItemAdd: ()-> Unit,
    onItemClicked : ((OBCarouselMediaType,String)-> Unit)?=null,
    onItemDeleted: (String)-> Unit
) {
    if (state.medias.isNotEmpty()) {

        val carouselState = rememberCarouselState {
            state.medias.size
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
        ) {
            HorizontalMultiBrowseCarousel(
                state = carouselState,
                modifier = modifier,
                preferredItemWidth = preferredItemWidth,
                itemSpacing = itemSpacing,
                contentPadding = contentPadding
            ) { index ->
              

                Box{
                    when(val mediaType = state.medias[index].first){
                        OBCarouselMediaType.Image -> OBCoverPhoto(
                            modifier = Modifier
                                .clickable(enabled = onItemClicked != null){
                                    onItemClicked?.invoke(mediaType,state.medias[index].second)
                                }
                                .height(206.dp),
                            url =state.medias[index].second
                        )
                        OBCarouselMediaType.Video -> OBVideoPlayer(
                            modifier = Modifier
                                .height(206.dp)
                                .fillMaxWidth(),
                            videoUri = state.medias[index].second
                        )
                    }

                    Icon(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(4.dp)
                            .background(color = MaterialTheme.colorScheme.primaryContainer , shape = CircleShape)
                            .clip(CircleShape)
                            .clickable(onClick = {
                                onItemDeleted(state.medias[index].second)
                            })
                        ,
                        imageVector = ImageVector.vectorResource(R.drawable.ic_clear),
                        tint = MaterialTheme.colorScheme.onSurface,
                        contentDescription = stringResource(R.string.content_description_delete_button)
                    )
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OBButtonContainedPrimary(
                    text = stringResource(R.string.add_images),
                    isEnabled = state.medias.size <= imagePickLimit,
                    onClick = onGalleryItemAdd
                )
                Text(
                    modifier = Modifier.weight(0.1f),
                    text = "${state.medias.size}/$imagePickLimit",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
            }
        }
    } else {
        Column(
            modifier = Modifier.padding(
                vertical = 32.dp
            ).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(
                16.dp, Alignment.CenterVertically
            )
        ) {
            PlaceholderText(
                text = stringResource(ds.string.carousel_placeholder)
            )
            OBButtonContainedNeutral(
                text = stringResource(ds.string.add_images),
                onClick = onGalleryItemAdd
            )
        }
    }
}