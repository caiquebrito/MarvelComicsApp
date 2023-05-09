package com.marvelcomics.brito.presentation.ui.compose.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.marvelcomics.brito.presentation.ui.compose.theme.Gray
import com.marvelcomics.brito.presentation.ui.compose.theme.MarvelComicsAppPreview
import com.marvelcomics.brito.presentation.ui.compose.theme.Red

@Composable
fun ProgressBar(
    indicatorProgress: Float = 0f,
    modifier: Modifier
) {
    val radius = 16f
    val cornerRadius = CornerRadius(radius, radius)
    val progressAnimated = animateFloatAsState(targetValue = indicatorProgress, label = "")

    Canvas(
        modifier = modifier.height(4.dp)
    ) {
        // background track
        drawRoundRect(
            color = Gray,
            cornerRadius = cornerRadius,
            size = Size(size.width, size.height)

        )
        // foreground track
        drawRoundRect(
            color = Red,
            cornerRadius = cornerRadius,
            size = Size(calculateProgressWidth(size.width, progressAnimated.value), size.height)
        )
    }
}

fun calculateProgressWidth(viewWidth: Float, progress: Float) =
    when {
        progress <= 0 -> 0f
        progress <= 100 -> viewWidth * progress / 100
        else -> viewWidth
    }

@Preview
@Composable
fun ProgressBarPreview() {
    MarvelComicsAppPreview {
        Column {
            val progress = remember {
                mutableStateOf(10f)
            }
            ProgressBar(
                modifier = Modifier.fillMaxWidth(),
                indicatorProgress = progress.value
            )
        }
    }
}
