package com.ilevy.circledrop.models

class Background {
    private var backgroundX: Float = 0f
    private var backgroundY: Float = 0f
    private var backgroundVelocity: Float = 25f

    fun getX(): Float {
        return backgroundX
    }

    fun getY(): Float {
        return backgroundY
    }

    fun setY(backgroundY: Float){
        this.backgroundY = backgroundY
    }

    fun getVelocity(): Float {
        return backgroundVelocity
    }
}