package com.marvelcomics.brito.presentation.details.ui.compose

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.marvelcomics.brito.entity.ComicEntity
import com.marvelcomics.brito.entity.SeriesEntity
import com.marvelcomics.brito.presentation.R
import com.marvelcomics.brito.presentation.databinding.FragmentDetailCharacterComposeBinding
import com.marvelcomics.brito.presentation.details.DetailCharacterUiEffect
import com.marvelcomics.brito.presentation.details.DetailCharacterViewModel
import com.marvelcomics.brito.presentation.ui.compose.extension.collectAsEffect
import com.marvelcomics.brito.presentation.ui.compose.extension.collectAsStateWithLifecycle
import com.marvelcomics.brito.presentation.ui.compose.theme.Black
import com.marvelcomics.brito.presentation.ui.compose.theme.MarvelComicsAppPreview
import com.marvelcomics.brito.presentation.ui.compose.theme.MarvelComicsAppTheme
import com.marvelcomics.brito.presentation.ui.compose.theme.White
import com.marvelcomics.brito.presentation.ui.extensions.viewBinding
import com.marvelcomics.brito.presentation.ui.models.CharacterDataBundle
import com.marvelcomics.brito.presentation.ui.models.MarvelThumbnailAspectRatio
import com.marvelcomics.brito.presentation.ui.models.fromBundleToEntity
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailCharacterComposeFragment : Fragment(R.layout.fragment_detail_character_compose) {

    private val binding by viewBinding(FragmentDetailCharacterComposeBinding::bind)
    private val viewModel by viewModel<DetailCharacterViewModel>()
    private val navArgs by navArgs<DetailCharacterComposeFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.composeContent.setContent {
            val state = viewModel.state.collectAsStateWithLifecycle().value
            viewModel.effect.collectAsEffect(::handleEffect)
            MarvelComicsAppTheme {
                if (state.isIdle) {
                    viewModel.getComicsAndSeriesById(navArgs.characterBundle.id)
                }
                DetailCharacterScreenConstraint(
                    characterBundle = navArgs.characterBundle,
                    showComicsLoading = state.showComicsLoading,
                    showSeriesLoading = state.showSeriesLoading,
                    listComics = state.listComics,
                    listSeries = state.listSeries
                )
            }
        }
    }

    private fun handleEffect(effect: DetailCharacterUiEffect) {
        when (effect) {
            DetailCharacterUiEffect.ShowComicsError -> {
                Toast.makeText(requireContext(), "Show Comics Error", Toast.LENGTH_SHORT).show()
            }
            DetailCharacterUiEffect.ShowSeriesError -> {
                Toast.makeText(requireContext(), "Show Series Error", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

@Composable
fun DetailCharacterScreenConstraint(
    characterBundle: CharacterDataBundle,
    showComicsLoading: Boolean,
    showSeriesLoading: Boolean,
    listComics: List<ComicEntity>?,
    listSeries: List<SeriesEntity>?
) {
    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val startGuideline = createGuidelineFromStart(16.dp)
        val endGuideline = createGuidelineFromEnd(16.dp)
        val topDivGuideline = createGuidelineFromTop(0.4f)

        val (headerContent, bodyContent) = createRefs()
        DetailCharacterHeaderComponent(
            modifier = Modifier
                .fillMaxSize()
                .constrainAs(headerContent) {
                    linkTo(
                        start = startGuideline,
                        end = endGuideline,
                        top = parent.top,
                        bottom = topDivGuideline
                    )
                    height = Dimension.fillToConstraints
                },
            characterBundle = characterBundle
        )
        DetailCharacterBodyComponent(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(bodyContent) {
                    linkTo(
                        start = startGuideline,
                        end = endGuideline,
                        top = topDivGuideline,
                        bottom = parent.bottom
                    )
                    height = Dimension.fillToConstraints
                },
            showComicsLoading = showComicsLoading,
            showSeriesLoading = showSeriesLoading,
            listComics = listComics,
            listSeries = listSeries
        )
    }
}

@Composable
fun DetailCharacterHeaderComponent(
    modifier: Modifier,
    characterBundle: CharacterDataBundle
) {
    Box(
        modifier = modifier
    ) {
        val backgroundResource = characterBundle.thumbnailDataBundle?.fromBundleToEntity()
            ?.getFullUrlThumbnailWithAspect(
                MarvelThumbnailAspectRatio.Standard.LARGE
            )
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current).data(backgroundResource)
                .crossfade(true).build(),
            placeholder = painterResource(R.drawable.ic_hail_hydra),
            contentDescription = "Image Placeholder",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val startGuideline = createGuidelineFromStart(16.dp)
            val endGuideline = createGuidelineFromEnd(16.dp)

            val (titleText, descriptionText) = createRefs()
            Text(
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(bottom = 8.dp)
                    .background(color = Color.Black)
                    .constrainAs(titleText) {
                        bottom.linkTo(descriptionText.top)
                        start.linkTo(startGuideline)
                    },
                text = characterBundle.name ?: "Empty Title",
                style = TextStyle(
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight.Medium,
                    fontSize = 24.sp,
                    letterSpacing = 0.5.sp
                ),
                textAlign = TextAlign.Start,
                color = White
            )
            val scroll = rememberScrollState(0)
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
                    .background(color = Color.Black)
                    .verticalScroll(scroll)
                    .constrainAs(descriptionText) {
                        bottom.linkTo(parent.bottom)
                        linkTo(start = startGuideline, end = endGuideline)
                        width = Dimension.fillToConstraints
                    },
                text = characterBundle.description ?: "Empty Description",
                maxLines = 3,
                style = TextStyle(
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight.Normal,
                    fontSize = 20.sp,
                    letterSpacing = 0.5.sp
                ),
                textAlign = TextAlign.Start,
                color = White
            )
        }
    }
}

@Composable
fun DetailCharacterBodyComponent(
    showComicsLoading: Boolean,
    showSeriesLoading: Boolean,
    listComics: List<ComicEntity>?,
    listSeries: List<SeriesEntity>?,
    modifier: Modifier
) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.Black)
            .padding(16.dp)
    ) {
        val (leftSideComponent, centerComponent, endsideComponent) = createRefs()
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(end = 8.dp)
                .constrainAs(leftSideComponent) {
                    linkTo(
                        start = parent.start,
                        end = centerComponent.start,
                        top = parent.top,
                        bottom = parent.bottom
                    )
                    width = Dimension.fillToConstraints
                }
        ) {
            if (showComicsLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(150.dp)
                        .align(Alignment.Center),
                    color = Color.Red,
                    strokeWidth = 12.dp
                )
            } else {
                listComics?.let {
                    LazyColumn(
                        modifier = Modifier.wrapContentHeight(),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        contentPadding = PaddingValues(horizontal = 16.dp)
                    ) {
                        items(it) { item ->
                            DetailCharacterComicItemComponent(
                                comicEntity = item
                            )
                        }
                    }
                }
            }
        }
        Spacer(
            modifier = Modifier
                .width(4.dp)
                .fillMaxHeight()
                .background(color = Color.White)
                .constrainAs(centerComponent) {
                    linkTo(
                        start = parent.start,
                        end = parent.end,
                        top = parent.top,
                        bottom = parent.bottom
                    )
                }
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 8.dp)
                .constrainAs(endsideComponent) {
                    linkTo(
                        start = centerComponent.end,
                        end = parent.end,
                        top = parent.top,
                        bottom = parent.bottom
                    )
                    width = Dimension.fillToConstraints
                }
        ) {
            if (showSeriesLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(150.dp)
                        .align(Alignment.Center),
                    color = Color.Red,
                    strokeWidth = 12.dp
                )
            } else {
                listSeries?.let {
                    LazyColumn(
                        modifier = Modifier.wrapContentHeight(),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(vertical = 8.dp)
                    ) {
                        items(it) { item ->
                            DetailCharacterSeriesItemComponent(
                                seriesEntity = item
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DetailCharacterComicItemComponent(
    comicEntity: ComicEntity
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val backgroundResource = comicEntity.thumbnailEntity
            ?.getFullUrlThumbnailWithAspect(
                MarvelThumbnailAspectRatio.Standard.MEDIUM
            )
        RoundedSquareComponent(
            modifier = Modifier
                .width(100.dp)
                .height(150.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current).data(backgroundResource)
                    .crossfade(true).build(),
                placeholder = painterResource(R.drawable.ic_hail_hydra),
                contentDescription = "Image Placeholder",
                contentScale = ContentScale.Crop
            )
        }
        Text(
            modifier = Modifier.padding(top = 8.dp),
            text = comicEntity.description ?: "Empty Description",
            style = TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                letterSpacing = 0.5.sp
            ),
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
            color = Color.White
        )
    }
}

@Composable
fun DetailCharacterSeriesItemComponent(
    seriesEntity: SeriesEntity
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val backgroundResource = seriesEntity.thumbnailEntity
            ?.getFullUrlThumbnailWithAspect(
                MarvelThumbnailAspectRatio.Standard.MEDIUM
            )
        RoundedSquareComponent(
            modifier = Modifier
                .width(100.dp)
                .height(150.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current).data(backgroundResource)
                    .crossfade(true).build(),
                placeholder = painterResource(R.drawable.ic_hail_hydra),
                contentDescription = "Image Placeholder",
                contentScale = ContentScale.Crop
            )
        }
        Text(
            modifier = Modifier.padding(top = 8.dp),
            text = seriesEntity.description ?: "Empty Description",
            style = TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                letterSpacing = 0.5.sp
            ),
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
            color = Color.White
        )
    }
}

@Composable
fun RoundedSquareComponent(modifier: Modifier, innerContent: @Composable () -> Unit) {
    androidx.compose.material.Surface(
        color = Black,
        modifier = modifier,
        shape = AbsoluteRoundedCornerShape(
            topLeftPercent = 16,
            topRightPercent = 16,
            bottomLeftPercent = 16,
            bottomRightPercent = 16
        ),
        content = innerContent
    )
}

@Preview(showBackground = true)
@Composable
fun DetailCharacterScreenConstraintPreview() {
    val characterBundle = CharacterDataBundle(
        0,
        null,
        null,
        null
    )
    MarvelComicsAppPreview {
        DetailCharacterScreenConstraint(
            characterBundle = characterBundle,
            showComicsLoading = false,
            showSeriesLoading = false,
            listComics = null,
            listSeries = null
        )
    }
}
