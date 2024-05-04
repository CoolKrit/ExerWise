package com.example.exerwise.presentation

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.example.exerwise.R

class ArcProgressBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var progress = 0
    private var maxProgress = 100
    private val progressPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val backgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val textPaintPercent = Paint(Paint.ANTI_ALIAS_FLAG)
    private val textPaintValue = Paint(Paint.ANTI_ALIAS_FLAG)
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var percentText: String = ""
    private var progressText: String = ""
    private var descriptionText: String = ""

    private val colorPrimary = ContextCompat.getColor(context, R.color.primary)
    private val colorSurface = ContextCompat.getColor(context, R.color.surface)
    private val colorWhite = ContextCompat.getColor(context, R.color.white)
    private val colorOnSurfaceVariant = ContextCompat.getColor(context, R.color.onSurfaceVariant)


    init {
        progressPaint.style = Paint.Style.STROKE
        progressPaint.strokeWidth = 20f
        progressPaint.strokeCap = Paint.Cap.ROUND
        progressPaint.color = colorPrimary

        backgroundPaint.style = Paint.Style.STROKE
        backgroundPaint.strokeWidth = 20f
        backgroundPaint.strokeCap = Paint.Cap.ROUND
        backgroundPaint.color = colorSurface

        textPaintPercent.style = Paint.Style.FILL
        textPaintPercent.color = colorWhite
        textPaintPercent.textSize = resources.getDimensionPixelSize(R.dimen.dimen_20sp).toFloat()
        textPaintPercent.typeface = resources.getFont(R.font.lexend_medium)
        textPaintPercent.textAlign = Paint.Align.CENTER

        textPaintValue.style = Paint.Style.FILL
        textPaintValue.color = colorWhite
        textPaintValue.textSize = resources.getDimensionPixelSize(R.dimen.dimen_12sp).toFloat()
        textPaintValue.typeface = resources.getFont(R.font.lexend_regular)
        textPaintValue.textAlign = Paint.Align.CENTER

        textPaint.style = Paint.Style.FILL
        textPaint.color = colorOnSurfaceVariant
        textPaint.textSize = resources.getDimensionPixelSize(R.dimen.dimen_12sp).toFloat()
        textPaint.typeface = resources.getFont(R.font.lexend_regular)
        textPaint.textAlign = Paint.Align.CENTER

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ArcProgressBar)
        percentText = typedArray.getString(R.styleable.ArcProgressBar_percentText) ?: ""
        progressText = typedArray.getString(R.styleable.ArcProgressBar_progressText) ?: ""
        descriptionText = typedArray.getString(R.styleable.ArcProgressBar_descriptionText) ?: ""
        typedArray.recycle()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val rectF = RectF(
            paddingLeft.toFloat(),
            paddingTop.toFloat(),
            (width - paddingRight).toFloat(),
            (height - paddingBottom).toFloat()
        )

        canvas.drawArc(rectF, 180f, 180f, false, backgroundPaint)

        val sweepAngle = 180 * progress / maxProgress.toFloat()
        canvas.drawArc(rectF, 180f, sweepAngle, false, progressPaint)

        val textX = width / 2f
        val percentText = "${(progress.toFloat() / maxProgress * 100).toInt()}%"
        val textYP = calculateTextY(height / 2f, 0.1f, textPaintPercent)
        canvas.drawText(percentText, textX, textYP, textPaintPercent)

        val progressText = "$progress/$maxProgress"
        val textYV = calculateTextY(height - paddingBottom.toFloat(), 0.2f, textPaintValue)
        canvas.drawText(progressText, textX, textYV, textPaintValue)

        val textYD = calculateTextY(textYV, -0.27f, textPaint)
        canvas.drawText(descriptionText, textX, textYD, textPaint)
    }

    private fun calculateTextY(centerY: Float, offsetRatio: Float, textPaint: Paint): Float {
        return centerY - offsetRatio * (centerY - (textPaint.descent() - textPaint.ascent()) / 2f)
    }

    fun setProgress(currentProgress: Int, totalProgress: Int) {
        this.progress = currentProgress
        this.maxProgress = totalProgress
        invalidate()
    }
}