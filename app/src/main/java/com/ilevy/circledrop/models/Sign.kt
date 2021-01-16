package com.ilevy.circledrop.models
import android.graphics.Paint
import com.ilevy.circledrop.utils.Constants

class Sign(private var signMessage: String, fontSize: Float, signX: Float?, signY: Float?) {
    private var signPaint: Paint = Paint();
    private var signX: Float = 0f;
    private var signY: Float = 0f;

    init {
        this.signPaint.setTextSize(fontSize);
        this.signX = signX ?: Constants.SCREEN_WIDTH / 2 - signPaint.measureText(signMessage) / 2;
        this.signY = signY ?: 0f;
    }

    fun recenterText() {
        this.signX = Constants.SCREEN_WIDTH / 2 - signPaint.measureText(signMessage) / 2;
    }

    fun getX(): Float {
        return signX;
    }

    fun setX(signX: Float) {
        this.signX = signX;
    }

    fun getY(): Float {
        return signY;
    }

    fun setY(signY: Float) {
        this.signY = signY;
    }

    fun getMessage(): String {
        return signMessage;
    }

    fun setMessage(signMessage: String) {
        this.signMessage = signMessage;
    }

    fun getPaint(): Paint {
        return signPaint;
    }
}