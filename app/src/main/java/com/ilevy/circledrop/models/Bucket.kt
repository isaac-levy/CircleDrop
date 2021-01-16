package com.ilevy.circledrop.models

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import com.ilevy.circledrop.utils.Constants

class Bucket(private var targetCircle: Int) {
    private var bucketPaint: Paint = Paint()
    private var bucketColor: Int = 0
    private lateinit var bucketRect: Rect

    init {
        when (targetCircle) {
            0 -> bucketColor = Color.RED
            1 -> bucketColor = Color.BLUE
            2 -> bucketColor = Color.GREEN
        }
    }

    fun paintBucket(canvas: Canvas) {
        bucketRect = Rect(
            (Constants.bucketWidth * targetCircle),
            (Constants.SCREEN_HEIGHT - Constants.bucketHeight).toInt(),
            (Constants.bucketWidth * targetCircle + Constants.bucketWidth),
            Constants.SCREEN_HEIGHT.toFloat().toInt()
        )

        bucketPaint.style = Paint.Style.FILL
        bucketPaint.color = bucketColor
        bucketPaint.alpha = 255 / 2

        canvas.drawRect(
            bucketRect,
            bucketPaint
        )

        bucketPaint.style = Paint.Style.STROKE
        bucketPaint.color = Color.BLACK
        bucketPaint.strokeWidth = 4f

        canvas.drawRect(
            bucketRect,
            bucketPaint
        )
    }

    fun getTargetCircle(): Int {
        return this.targetCircle
    }
}