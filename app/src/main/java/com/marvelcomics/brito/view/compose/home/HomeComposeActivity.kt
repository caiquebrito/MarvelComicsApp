package com.marvelcomics.brito.view.compose.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
import com.marvelcomics.brito.R
import com.marvelcomics.brito.presentation.character.CharacterViewModel
import com.marvelcomics.brito.view.compose.theme.Black
import com.marvelcomics.brito.view.compose.theme.MarvelComicsAppPreview
import com.marvelcomics.brito.view.compose.theme.MarvelComicsAppTheme
import com.marvelcomics.brito.view.compose.theme.Typography
import com.marvelcomics.brito.view.compose.theme.White
import com.marvelcomics.brito.view.compose.theme.White60
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeComposeActivity : ComponentActivity() {

    private val characterViewModel: CharacterViewModel by viewModel()

    private val listHeroes = listOf(
        MockObjectHeroes(
            R.drawable.captain_marvel,
            "Captain Marvel",
            "Carol Danvers"
        ),
        MockObjectHeroes(
            R.drawable.captain_marvel,
            "Captain Marvel",
            "Carol Danvers"
        ),
        MockObjectHeroes(
            R.drawable.captain_marvel,
            "Captain Marvel",
            "Carol Danvers"
        ),
        MockObjectHeroes(
            R.drawable.captain_marvel,
            "Captain Marvel",
            "Carol Danvers"
        ),
        MockObjectHeroes(
            R.drawable.captain_marvel,
            "Captain Marvel",
            "Carol Danvers"
        ),
        MockObjectHeroes(
            R.drawable.captain_marvel,
            "Captain Marvel",
            "Carol Danvers"
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MarvelComicsAppTheme {
                HomeScreenConstraint(listHeroes)
            }
        }
    }
}

class MockObjectHeroes(val backgroundResource: Int, val heroeTitle: String, val heroeName: String)

@Composable
fun HomeScreenConstraint(listHeroes: List<MockObjectHeroes>) {
    HomeBackgroundComponent {
        ConstraintLayout(
            modifier = Modifier.fillMaxSize()
        ) {
            val startGuideline = createGuidelineFromStart(0.1f)
            val endGuideline = createGuidelineFromEnd(0.1f)
            val topDivGuideline = createGuidelineFromTop(0.4f)

            val (toolbar, headerContent, bodyContent) = createRefs()
            HomeToolbarComponent(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(top = 16.dp)
                    .constrainAs(toolbar) {
                        top.linkTo(parent.top)
                        start.linkTo(startGuideline)
                        end.linkTo(endGuideline)
                    }
            )
            HomeHeaderComponent(
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(headerContent) {
                        linkTo(top = toolbar.bottom, bottom = topDivGuideline)
                    }
            )
            TopSquaredComponent(
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(bodyContent) {
                        linkTo(top = topDivGuideline, bottom = parent.bottom)
                        height = Dimension.fillToConstraints
                    }
            ) {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(32.dp)
                ) {
                    items(listHeroes) { item ->
                        HeroesCardComponent(
                            modifier = Modifier,
                            backgroundResource = item.backgroundResource,
                            heroesTitle = item.heroeTitle,
                            heroesName = item.heroeName
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun HeroesCardComponent(
    modifier: Modifier,
    backgroundResource: Int,
    heroesTitle: String,
    heroesName: String
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        RoundedSquareBottomFlatComponent(
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

                Image(
                    painter = painterResource(id = backgroundResource),
                    contentDescription = null,
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
                    text = heroesTitle,
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
                    color = White,
                )
                Text(
                    text = heroesName,
                    modifier = Modifier
                        .constrainAs(name) {
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
fun HomeBackgroundComponent(content: @Composable () -> Unit) {
    Box {
        Image(
            painter = painterResource(id = R.drawable.ic_background_marvel),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize(),
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
fun HomeToolbarComponent(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_marvel_logo),
            contentDescription = null,
            modifier = Modifier
                .height(50.dp)
                .width(100.dp)
                .align(Alignment.Center),
        )
        Image(
            painter = painterResource(id = R.drawable.ic_search),
            contentDescription = null,
            modifier = Modifier
                .height(40.dp)
                .width(40.dp)
                .align(Alignment.CenterEnd)
                .padding(end = 16.dp)
        )
    }
}

@Composable
fun HomeHeaderComponent(modifier: Modifier) {
    Column(
        modifier = modifier
            .padding(start = 16.dp, end = 16.dp)
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
            style = Typography.bodySmall,
            textAlign = TextAlign.Center,
            color = White
        )
    }
}

@Composable
fun TopSquaredComponent(modifier: Modifier, innerContent: @Composable () -> Unit) {
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
fun RoundedSquareBottomFlatComponent(modifier: Modifier, innerContent: @Composable () -> Unit) {
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
fun HomeScreenConstraintPreview() {
    val listHeroes = listOf(
        MockObjectHeroes(
            R.drawable.captain_marvel,
            "Captain Marvel",
            "Carol Danvers"
        ),
        MockObjectHeroes(
            R.drawable.captain_marvel,
            "Captain Marvel",
            "Carol Danvers"
        ),
        MockObjectHeroes(
            R.drawable.captain_marvel,
            "Captain Marvel",
            "Carol Danvers"
        ),
        MockObjectHeroes(
            R.drawable.captain_marvel,
            "Captain Marvel",
            "Carol Danvers"
        ),
        MockObjectHeroes(
            R.drawable.captain_marvel,
            "Captain Marvel",
            "Carol Danvers"
        ),
        MockObjectHeroes(
            R.drawable.captain_marvel,
            "Captain Marvel",
            "Carol Danvers"
        )
    )
    MarvelComicsAppPreview {
        HomeScreenConstraint(listHeroes)
    }
}
