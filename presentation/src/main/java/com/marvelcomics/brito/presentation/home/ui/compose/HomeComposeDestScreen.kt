package com.marvelcomics.brito.presentation.home.ui.compose

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.marvelcomics.brito.entity.CharacterEntity
import com.marvelcomics.brito.entity.ThumbnailEntity
import com.marvelcomics.brito.presentation.R
import com.marvelcomics.brito.presentation.destinations.DetailCharacterDestScreenDestination
import com.marvelcomics.brito.presentation.destinations.SearchComposeDestScreenDestination
import com.marvelcomics.brito.presentation.home.HomeUiEffect
import com.marvelcomics.brito.presentation.home.HomeViewModel
import com.marvelcomics.brito.presentation.ui.compose.extension.collectAsEffect
import com.marvelcomics.brito.presentation.ui.compose.extension.collectAsStateWithLifecycle
import com.marvelcomics.brito.presentation.ui.compose.theme.Black
import com.marvelcomics.brito.presentation.ui.compose.theme.MarvelComicsAppPreview
import com.marvelcomics.brito.presentation.ui.compose.theme.White
import com.marvelcomics.brito.presentation.ui.compose.theme.White60
import com.marvelcomics.brito.presentation.ui.models.MarvelThumbnailAspectRatio
import com.marvelcomics.brito.presentation.ui.models.fromEntityToBundle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.NavResult
import com.ramcosta.composedestinations.result.ResultRecipient
import org.koin.androidx.compose.koinViewModel

@RootNavGraph(start = true)
@Destination
@Composable
fun HomeComposeDestScreen(
    viewModel: HomeViewModel = koinViewModel(),
    navigator: DestinationsNavigator,
    resultRecipient: ResultRecipient<SearchComposeDestScreenDestination, Boolean>
) {
    val context = LocalContext.current
    fun handleDestEffect(homeUiEffect: HomeUiEffect) {
        when (homeUiEffect) {
            is HomeUiEffect.ShowError -> {
                Toast.makeText(context, "Show Error", Toast.LENGTH_LONG).show()
            }
            is HomeUiEffect.OpenSearchScreen -> {
                homeUiEffect.ids?.let {
                    navigator.navigate(
                        SearchComposeDestScreenDestination(
                            listIds = it.toIntArray()
                        )
                    )
//                    HomeFragmentComposeDirections.navigateToSearchFragmentCompose(
//                        it.toIntArray()
//                    )
                }
            }
            is HomeUiEffect.OpenDetailScreen -> {
                navigator.navigate(
                    DetailCharacterDestScreenDestination(
                        characterBundle = homeUiEffect.entity.fromEntityToBundle()
                    )
                )
//                HomeFragmentComposeDirections.navigateToDetailCharacterFragmentCompose(
//                    characterBundle = homeUiEffect.entity.fromEntityToBundle()
//                )
            }
        }
    }

    resultRecipient.onNavResult { result ->
        when (result) {
            is NavResult.Value -> {
                if (result.value) {
                    viewModel.getLocalCharacters()
                }
            }
            else -> {}
        }
    }

    val state = viewModel.state.collectAsStateWithLifecycle().value
    viewModel.effect.collectAsEffect(::handleDestEffect)
    if (state.showLoading) {
        viewModel.getLocalCharacters()
    }
    HomeBackgroundDestComponent {
        ConstraintLayout(
            modifier = Modifier.fillMaxSize()
        ) {
            val startGuideline = createGuidelineFromStart(16.dp)
            val endGuideline = createGuidelineFromEnd(16.dp)
            val topDivGuideline = createGuidelineFromTop(0.4f)

            val (toolbar, headerContent, bodyContent) = createRefs()
            HomeToolbarDestComponent(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(top = 16.dp)
                    .constrainAs(toolbar) {
                        top.linkTo(parent.top)
                        start.linkTo(startGuideline)
                        end.linkTo(endGuideline)
                    },
                viewModel::searchButtonClicked
            )
            HomeHeaderDestComponent(
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(headerContent) {
                        linkTo(top = toolbar.bottom, bottom = topDivGuideline)
                    }
            )
            TopSquaredDestComponent(
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(bodyContent) {
                        linkTo(top = topDivGuideline, bottom = parent.bottom)
                        height = Dimension.fillToConstraints
                    }
            ) {
                Box {
                    if (state.showLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .size(150.dp)
                                .align(Alignment.Center),
                            color = Color.Red,
                            strokeWidth = 12.dp
                        )
                    } else {
                        state.listCharacters?.let {
                            LazyRow(
                                modifier = Modifier.wrapContentHeight(),
                                horizontalArrangement = Arrangement.spacedBy(16.dp),
                                contentPadding = PaddingValues(horizontal = 16.dp)
                            ) {
                                items(it) { item ->
                                    HeroesCardDestComponent(
                                        modifier = Modifier.clickable {
                                            viewModel.adapterItemClicked(item)
                                        },
                                        backgroundResource = item.thumbnailEntity?.getFullUrlThumbnailWithAspect(
                                            MarvelThumbnailAspectRatio.Standard.MEDIUM
                                        ),
                                        heroesTitle = item.name,
                                        heroesName = "Loading..."
                                    )
                                }
                            }
                        } ?: HeroesCardDestComponent(
                            modifier = Modifier
                                .wrapContentHeight()
                                .clickable {
                                    viewModel.emptyButtonItemClicked()
                                },
                            backgroundResource = null,
                            heroesTitle = null,
                            heroesName = "Loading..."
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun HeroesCardDestComponent(
    modifier: Modifier,
    backgroundResource: String?,
    heroesTitle: String?,
    heroesName: String?
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        RoundedSquareBottomFlatDestComponent(
            modifier = Modifier
                .height(250.dp)
                .width(130.dp)
        ) {
            ConstraintLayout {
                val startGuideline = createGuidelineFromStart(0.05f)
                val endGuideline = createGuidelineFromEnd(0.05f)
                val bottomGuideline = createGuidelineFromBottom(0.04f)
                val centerVerticalGuideline = createGuidelineFromTop(0.5f)

                val (centerDiv, tooltip, backgroundHeroes, title, name) = createRefs()

                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(backgroundResource)
                        .error(R.drawable.ic_thanos)
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(R.drawable.ic_hail_hydra),
                    contentDescription = "Image Placeholder",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(backgroundHeroes) {
                            linkTo(top = parent.top, bottom = centerDiv.top)
                            height = Dimension.fillToConstraints
                        }
                )

                Surface(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(top = 4.dp)
                        .constrainAs(tooltip) {
                            linkTo(start = startGuideline, end = endGuideline)
                        },
                    color = Black,
                    shape = CutCornerShape(
                        topStart = 8.dp,
                        topEnd = 0.dp,
                        bottomEnd = 8.dp,
                        bottomStart = 0.dp
                    )
                ) {
                    Text(
                        modifier = Modifier.padding(
                            top = 4.dp,
                            bottom = 4.dp,
                            start = 16.dp,
                            end = 16.dp
                        ),
                        text = "MOVIES",
                        style = TextStyle(
                            fontFamily = FontFamily.Default,
                            fontWeight = FontWeight.Medium,
                            fontSize = 6.sp,
                            letterSpacing = 0.5.sp
                        ),
                        textAlign = TextAlign.Center,
                        color = White
                    )
                }
                Spacer(
                    modifier = Modifier
                        .height(5.dp)
                        .fillMaxWidth()
                        .background(color = Color.Red)
                        .constrainAs(centerDiv) {
                            linkTo(
                                top = centerVerticalGuideline,
                                bottom = centerVerticalGuideline
                            )
                        }
                )
                Text(
                    text = heroesTitle ?: "Empty",
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .constrainAs(title) {
                            start.linkTo(startGuideline)
                            top.linkTo(centerDiv.bottom)
                            end.linkTo(endGuideline)
                            width = Dimension.fillToConstraints
                        },
                    textAlign = TextAlign.Start,
                    style = TextStyle(
                        fontFamily = FontFamily.Default,
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp,
                        letterSpacing = 0.5.sp
                    ),
                    color = White
                )
                Text(
                    text = heroesName ?: "Empty",
                    modifier = Modifier.constrainAs(name) {
                        start.linkTo(startGuideline)
                        bottom.linkTo(bottomGuideline)
                    },
                    textAlign = TextAlign.Start,
                    style = TextStyle(
                        fontFamily = FontFamily.Default,
                        fontWeight = FontWeight.Normal,
                        fontSize = 10.sp,
                        letterSpacing = 0.5.sp
                    ),
                    color = White
                )
            }
        }
    }
}

@Composable
fun HomeBackgroundDestComponent(content: @Composable () -> Unit) {
    Box {
        Image(
            painter = painterResource(id = R.drawable.ic_background_marvel),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )
        Surface(
            color = Black,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .alpha(0.6f)
        ) {}
        content.invoke()
    }
}

@Composable
fun HomeToolbarDestComponent(modifier: Modifier = Modifier, searchButtonClick: () -> Unit) {
    Box(
        modifier = modifier
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_marvel_logo),
            contentDescription = null,
            modifier = Modifier
                .height(50.dp)
                .width(100.dp)
                .align(Alignment.Center)
        )
        Image(
            painter = painterResource(id = R.drawable.ic_search),
            contentDescription = null,
            modifier = Modifier
                .height(40.dp)
                .width(40.dp)
                .align(Alignment.CenterEnd)
                .padding(end = 16.dp)
                .clickable {
                    searchButtonClick.invoke()
                }
        )
    }
}

@Composable
fun HomeHeaderDestComponent(modifier: Modifier) {
    Column(
        modifier = modifier.padding(start = 16.dp, end = 16.dp)
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Marvel Characters",
            style = MaterialTheme.typography.h4,
            textAlign = TextAlign.Center,
            color = White
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Get hooked on a hearty helping of heroes and villains form the humble House of Ideas!",
            textAlign = TextAlign.Center,
            color = White
        )
    }
}

@Composable
fun TopSquaredDestComponent(modifier: Modifier, innerContent: @Composable () -> Unit) {
    Surface(
        color = White60,
        modifier = modifier,
        shape = AbsoluteRoundedCornerShape(
            topLeftPercent = 4,
            topRightPercent = 4,
            bottomLeftPercent = 0,
            bottomRightPercent = 0
        ),
        content = innerContent
    )
}

@Composable
fun RoundedSquareBottomFlatDestComponent(modifier: Modifier, innerContent: @Composable () -> Unit) {
    Surface(
        color = Black,
        modifier = modifier,
        shape = AbsoluteRoundedCornerShape(
            topLeftPercent = 16,
            topRightPercent = 16,
            bottomLeftPercent = 0,
            bottomRightPercent = 16
        ),
        content = innerContent
    )
}

@Preview(showBackground = true)
@Composable
fun HomeScreenConstraintDestPreview() {
    val listHeroes = listOf(
        CharacterEntity(
            id = 0,
            name = "Captain Marvel",
            description = "Little Description",
            thumbnailEntity = ThumbnailEntity(
                path = "https://path",
                extension = "JPEG"
            )
        ),
        CharacterEntity(
            id = 0,
            name = "Captain Marvel",
            description = "Little Description",
            thumbnailEntity = ThumbnailEntity(
                path = "https://path",
                extension = "JPEG"
            )
        )
    )
    MarvelComicsAppPreview {
        HomeScreenConstraint(false, listHeroes, {}, {}, {})
    }
}
