package com.marvelcomics.brito.presentation.splash

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults.buttonColors
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
import com.marvelcomics.brito.presentation.R
import com.marvelcomics.brito.presentation.main.ui.compose.MainComposeActivity
import com.marvelcomics.brito.presentation.main.ui.legacy.MainActivity
import com.marvelcomics.brito.presentation.ui.compose.theme.Black
import com.marvelcomics.brito.presentation.ui.compose.theme.MarvelComicsAppPreview
import com.marvelcomics.brito.presentation.ui.compose.theme.MarvelComicsAppTheme

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MarvelComicsAppTheme {
                SplashScreenConstraint(xmlButtonClick = {
                    startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                }, composeButtonClick = {
                        startActivity(Intent(this@SplashActivity, MainComposeActivity::class.java))
                    })
            }
        }
    }
}

@Composable
fun SplashScreenConstraint(xmlButtonClick: () -> Unit, composeButtonClick: () -> Unit) {
    SplashBackgroundComponent {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                modifier = Modifier.padding(
                    bottom = 32.dp
                ),
                colors = buttonColors(backgroundColor = Color.Red),
                onClick = xmlButtonClick
            ) {
                Text(
                    modifier = Modifier.padding(
                        16.dp
                    ),
                    text = "Open XML",
                    style = TextStyle(
                        fontFamily = FontFamily.Default,
                        fontWeight = FontWeight.Medium,
                        fontSize = 24.sp,
                        letterSpacing = 0.5.sp
                    ),
                    textAlign = TextAlign.Center,
                    color = Color.White
                )
            }
            Button(
                modifier = Modifier.padding(
                    top = 32.dp
                ),
                colors = buttonColors(backgroundColor = Color.Red),
                onClick = composeButtonClick
            ) {
                Text(
                    modifier = Modifier.padding(
                        16.dp
                    ),
                    text = "Open Compose",
                    style = TextStyle(
                        fontFamily = FontFamily.Default,
                        fontWeight = FontWeight.Medium,
                        fontSize = 24.sp,
                        letterSpacing = 0.5.sp
                    ),
                    textAlign = TextAlign.Center,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun SplashBackgroundComponent(content: @Composable () -> Unit) {
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

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    MarvelComicsAppPreview {
        SplashScreenConstraint({}, {})
    }
}
