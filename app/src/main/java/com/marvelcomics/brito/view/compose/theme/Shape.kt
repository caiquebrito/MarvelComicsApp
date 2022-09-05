package com.marvelcomics.brito.view.compose.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

val Shapes = Shapes(
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(4.dp),
    large = RoundedCornerShape(0.dp)
)

@Composable
fun RoundedCornerShapeButBottomLeftSquared(
    backgroundColor: Color
) {
    val shape = RoundedCornerShape(16.dp)


}