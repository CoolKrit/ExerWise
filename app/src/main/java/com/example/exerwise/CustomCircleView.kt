package com.example.exerwise

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class CustomCircleView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var circlePaint: Paint = Paint()

    init {
        circlePaint.color = Color.BLUE // Цвет круга (можно изменить)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val centerX = width / 2.toFloat()
        val centerY = height / 2.toFloat()
        val radius = (if (width < height) width else height) / 2.toFloat() // Радиус круга

        canvas?.drawCircle(centerX, centerY, radius, circlePaint)
    }
}
