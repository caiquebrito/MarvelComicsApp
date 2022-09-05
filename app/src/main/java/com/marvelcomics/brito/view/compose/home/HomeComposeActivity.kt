package com.marvelcomics.brito.view.compose.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.marvelcomics.brito.R
import com.marvelcomics.brito.presentation.character.CharacterViewModel
import com.marvelcomics.brito.view.compose.theme.Black
import com.marvelcomics.brito.view.compose.theme.MarvelComicsAppPreview
import com.marvelcomics.brito.view.compose.theme.MarvelComicsAppTheme
import com.marvelcomics.brito.view.compose.theme.Typography
import com.marvelcomics.brito.view.compose.theme.White
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeComposeActivity : ComponentActivity() {

    private val characterViewModel: CharacterViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MarvelComicsAppTheme {
                HomeScreen()
            }
        }
    }
}

@Composable
fun HomeScreen() {
    HomeBackgroundComponent {
        Column {
            Surface(
                color = Color.Transparent,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.4f)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    HomeToolbarComponent()
                    HomeHeaderComponent(
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            Surface(
                color = White,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(1f)
                    .alpha(0.6f),
                shape = AbsoluteRoundedCornerShape(
                    topLeftPercent = 4,
                    topRightPercent = 4,
                    bottomLeftPercent = 0,
                    bottomRightPercent = 0
                )
            ) {
                HomeBodyComponent()
            }
        }
    }
}

@Composable
fun HomeHeaderComponent(modifier: Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
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
fun HomeToolbarComponent() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
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
fun HomeBodyComponent() {
}

@Composable
fun HomeHeroesCardComponent() {
    Surface(
        color = Color.Transparent,
        modifier = Modifier.fillMaxSize()
    ) {
        Surface(
            color = Black,
            modifier = Modifier
                .fillMaxHeight(
                    1f
                )
                .padding(
                    start = 20.dp,
                    top = 40.dp,
                    end = 20.dp,
                    bottom = 40.dp
                ),
            shape = AbsoluteRoundedCornerShape(
                topLeftPercent = 20,
                topRightPercent = 20,
                bottomLeftPercent = 0,
                bottomRightPercent = 20
            )
        ) {
            Text(
                text = "Wrapped content",
                style = MaterialTheme.typography.h2
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeToolbarPreview() {
    MarvelComicsAppPreview {
        HomeToolbarComponent()
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MarvelComicsAppTheme {
        HomeScreen()
    }
}
