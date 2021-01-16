package com.ilevy.circledrop.models

import com.ilevy.circledrop.utils.Constants

class Circle(private val circleIndex: Int) {
    private var circleX: Int = 0
    private var circleY: Int = -Constants.getBitmapBank().getCircleHeight()
    private var circleVelocity: Int = 20
    private var reachedBottom: Boolean = false

    fun getX(): Int {
        return circleX
    }

    fun setX(circleX: Int) {
        this.circleX = circleX
    }

    fun getY(): Int {
        return circleY
    }

    fun setY(circleY: Int) {
        this.circleY = circleY
    }

    fun getVelocity(): Int {
        return circleVelocity
    }

    fun setVelocity(circleVelocity: Int){
        this.circleVelocity = circleVelocity;
    }

    fun getReachedBottom(): Boolean {
        return reachedBottom
    }

    fun setReachedBottom(reachedBottom: Boolean) {
        this.reachedBottom = reachedBottom
    }

    fun getIndex(): Int {
        return circleIndex
    }
}