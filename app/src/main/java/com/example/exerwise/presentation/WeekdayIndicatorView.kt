package com.example.exerwise.presentation

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.example.exerwise.R

class WeekdayIndicatorView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val daysOfWeek = listOf("M", "T", "W", "T", "F", "S", "S")
    private val completedDays = mutableSetOf<String>()

    private var circleRadius: Float = 0f
    private var circleSpacing: Float = 0f
    private var textYOffset: Float = 0f

    private val colorWeekdayPrimary = ContextCompat.getColor(context, R.color.md_theme_primary)
    private val colorWeekdaySurface = ContextCompat.getColor(context, R.color.md_theme_surfaceContainerHighest)
    private val colorWeekdayText = ContextCompat.getColor(context, R.color.md_theme_onSurface)

    private val bluePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = colorWeekdayPrimary
        style = Paint.Style.FILL
    }

    private val grayPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = colorWeekdaySurface
        style = Paint.Style.FILL
    }

    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = colorWeekdayText
        textSize = resources.getDimensionPixelSize(R.dimen.dimen_12sp).toFloat()
        typeface = resources.getFont(R.font.lexend_regular)
        textAlign = Paint.Align.CENTER
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        calculateSizes(w)
    }

    private fun calculateSizes(viewWidth: Int) {
        val numCircles = daysOfWeek.size
        if (numCircles > 0) {
            circleRadius = viewWidth / (2f * numCircles + (numCircles - 1))
            circleSpacing = circleRadius
            textYOffset = circleRadius + textPaint.textSize
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        calculateSizes(widthSize)

        val measuredWidth = resolveSize(widthSize, widthMeasureSpec)
        val measuredHeight = resolveSize((2 * circleRadius + textPaint.textSize + 2f).toInt(), heightMeasureSpec)
        setMeasuredDimension(measuredWidth, measuredHeight)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val centerY = circleRadius
        var cx = circleRadius

        for (day in daysOfWeek) {
            canvas.drawCircle(cx, centerY, circleRadius, grayPaint)

            if (completedDays.contains(day)) {
                canvas.drawCircle(cx, centerY, circleRadius, bluePaint)
            }

            canvas.drawText(day, cx, centerY + textYOffset, textPaint)

            cx += 2 * circleRadius + circleSpacing
        }
    }

    fun markDayCompleted(dayOfWeek: String) {
        completedDays.add(dayOfWeek)
        invalidate()
    }

    fun clearCompletedDays() {
        completedDays.clear()
        invalidate()
    }
}