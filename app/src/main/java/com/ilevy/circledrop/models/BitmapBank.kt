package com.ilevy.circledrop.models

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.ilevy.circledrop.utils.Constants
import com.ilevy.circledrop.R

class BitmapBank(res: Resources) {
    private var background: Bitmap = BitmapFactory.decodeResource(res, R.drawable.background)
    private var circles: Array<Bitmap>

    init {
        background = scaleImage(background)
        circles = Array<Bitmap>(3){
            scaleCircle(BitmapFactory.decodeResource(res, res.getIdentifier("circle${it}", "drawable", "com.ilevy.circledrop")));
        }
    }

    fun getBackground(): Bitmap {
        return background
    }

    fun getBackgroundWidth(): Int {
        return background.getWidth()
    }

    fun getBackgroundHeight(): Int {
        return background.getHeight()
    }

    fun getCircle(circle: Int): Bitmap {
        return circles[circle]
    }

    fun getCircleWidth(): Int {
        return circles[0].getWidth()
    }

    fun getCircleHeight(): Int {
        return circles[0].getHeight()
    }

    fun scaleImage(bitmap: Bitmap): Bitmap {
        val widthHeightRatio: Float = (getBackgroundWidth().toFloat() / getBackgroundHeight().toFloat())
        val backgroundScaledWidth: Int = (widthHeightRatio * Constants.SCREEN_HEIGHT).toInt()
        return Bitmap.createScaledBitmap(bitmap, backgroundScaledWidth, Constants.SCREEN_HEIGHT, false)
    }

    fun scaleCircle(bitmap: Bitmap): Bitmap {
        val widthFit: Int = Constants.SCREEN_WIDTH / 6
        return Bitmap.createScaledBitmap(bitmap, widthFit, widthFit, false)
    }
}