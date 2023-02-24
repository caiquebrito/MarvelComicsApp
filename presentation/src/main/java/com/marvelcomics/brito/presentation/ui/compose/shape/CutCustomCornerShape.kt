package com.marvelcomics.brito.presentation.ui.compose.shape

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.drawable.shapes.Shape

class CutCustomCornerShape : Shape() {
    private val border = Paint()
    private val path: Path = Path()
    override fun onResize(width: Float, height: Float) {
        super.onResize(width, height)
        val dx = STROKE_WIDTH / 2.0f
        val dy = STROKE_WIDTH / 2.0f
        val w = width - dx
        val h = height - dy

        // RectF arc = new RectF(x,h-2*CORNER,x+2*CORNER,h);
        path.reset()
        path.moveTo(dx + CORNER, dy)
        path.lineTo(w - CORNER, dy)
        path.lineTo(w, dy + CORNER)
        path.lineTo(w, h)
        path.lineTo(dx + CORNER, h)
        // path.arcTo (arc,90.0f,90.0f);
        path.lineTo(dx, h - CORNER)
        path.lineTo(dx, dy) // path.lineTo(dx,y + CORNER);
        path.close()
    }

    override fun draw(canvas: Canvas, paint: Paint) {
        // TODO Auto-generated method stub
        canvas.drawPath(path, border)
    }

    companion object {
        private const val COLOUR = Color.BLACK
        private const val STROKE_WIDTH = 1.0f
        private const val CORNER = 35.0f
    }

    init {
        border.color = COLOUR
        border.style = Paint.Style.FILL
        border.strokeWidth = STROKE_WIDTH
        border.isAntiAlias = true
        border.isDither = true
        border.strokeJoin = Paint.Join.ROUND
        border.strokeCap = Paint.Cap.ROUND
    }
}
