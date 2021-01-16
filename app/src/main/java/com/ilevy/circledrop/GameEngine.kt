package com.ilevy.circledrop

import android.graphics.Canvas
import com.ilevy.circledrop.models.*
import com.ilevy.circledrop.utils.Constants
import kotlin.random.Random

class GameEngine {
    private val background: Background = Background()
    private lateinit var circles: Array<Circle>
    private var buckets: Array<Bucket> = Array<Bucket>(3) {
        Bucket(it);
    }
    private var heldCircle: Circle? = null
    private lateinit var scoreBoard: Sign
    private lateinit var highScoreBoard: Sign
    private lateinit var sign: Sign
    private var secondarySign = Sign("Tap on screen to continue playing!", 60f, null, null)

    private var baseNumberOfCircles = 5
    private var totalCircles: Int = baseNumberOfCircles
    private var circleCountInRound: Int = 1
    private var circleVelocityIncrease: Int = 2

    private var roundNumber = 1
    private var roundDone: Boolean = false

    private var score = 0

    private val baseNumOfLives = 5
    private var currentLives = baseNumOfLives
    private var lostGame = false
    private var noTouchState = false
    private var pauseState = false

    init {
        setUpRound(roundNumber)
    }

    fun setUpRound(round: Int) {
        roundNumber = round
        totalCircles = baseNumberOfCircles * roundNumber
        circleCountInRound = 1
        circles = addCircles(totalCircles)
        currentLives = baseNumOfLives

        score = if(lostGame) 0 else score

        roundDone = false
        lostGame = false

        scoreBoard = Sign(
            "Score: ${getScore()}",
            50f,
            30f,
            60f
        )
        highScoreBoard = Sign(
            "High Score: ${Constants.getHighScore()}",
            50f,
            30f,
            120f
        )
        sign = Sign(
            "Round $roundNumber Over!",
            100f,
            null,
            Constants.SCREEN_HEIGHT.toFloat()
        )
    }

    fun updateAndDrawBackground(canvas: Canvas) {
        background.setY(background.getY() - background.getVelocity())

        if (background.getY() < -Constants.SCREEN_HEIGHT) {
            background.setY(0f)
        }
        canvas.drawBitmap(
            Constants.getBitmapBank().getBackground(),
            background.getX(),
            background.getY(),
            null
        )
        if (background.getY() < 0) {
            canvas.drawBitmap(
                Constants.getBitmapBank().getBackground(),
                background.getX(),
                (Constants.SCREEN_HEIGHT + background.getY()),
                null
            )
        }

        scoreBoard.setMessage("Score: $score")
        highScoreBoard.setMessage("High Score: ${Constants.getHighScore()}")

        canvas.drawText(
            scoreBoard.getMessage(),
            scoreBoard.getX(),
            scoreBoard.getY(),
            scoreBoard.getPaint()
        )
        canvas.drawText(
            highScoreBoard.getMessage(),
            highScoreBoard.getX(),
            highScoreBoard.getY(),
            highScoreBoard.getPaint()
        )
    }

    fun updateAndDrawCircle(canvas: Canvas) {
        var isDone: Boolean = true
        if (!roundDone) {
            for (i in 0 until circleCountInRound) {
                if (!circles[i].getReachedBottom()) {
                    isDone = false

                    if (circles[i] != heldCircle) {
                        circles[i].setY(circles[i].getY() + circles[i].getVelocity())
                    }

                    if (circles[i].getY() > Constants.SCREEN_HEIGHT) {
                        circles[i].setReachedBottom(true)
                        calculateScore(circles[i])
                    }
                }

                canvas.drawBitmap(
                    Constants.getBitmapBank().getCircle(circles[i].getIndex()),
                    circles[i].getX().toFloat(),
                    circles[i].getY().toFloat(),
                    null
                )
            }
        }
        if (isDone || currentLives == 0) {
            roundDone = true

            if(currentLives == 0){
                lostGame = true
                setLostGameMessages()
                if(Constants.getHighScore() < score){
                    Constants.setHighScore(score)
                }
            }
        }
    }

    fun updateAndDrawBuckets(canvas: Canvas) {
        buckets.forEach {
            it.paintBucket(canvas)
        }
    }

    fun updateAndDrawMessage(canvas: Canvas) {
        noTouchState = true
        if (sign.getY() > (Constants.SCREEN_HEIGHT / 2) - 50) {
            sign.setY(sign.getY() - 15)
        } else {
            canvas.drawText(
                secondarySign.getMessage(),
                secondarySign.getX(),
                sign.getY() + 100,
                secondarySign.getPaint()
            )
            noTouchState = false
        }

        canvas.drawText(
            sign.getMessage(),
            sign.getX(),
            sign.getY(),
            sign.getPaint()
        )
    }

    private fun calculateScore(circle: Circle) {
        score += if (circle.getX() + Constants.getBitmapBank().getCircleWidth() / 2 < Constants.bucketWidth) {
            circleToBucket(circle.getIndex(), buckets[0].getTargetCircle())
        } else if (circle.getX() + Constants.getBitmapBank().getCircleWidth() / 2 < Constants.bucketWidth * 2) {
            circleToBucket(circle.getIndex(), buckets[1].getTargetCircle())
        } else {
            circleToBucket(circle.getIndex(), buckets[2].getTargetCircle())
        }
    }

    private fun circleToBucket(circleIndex: Int, bucketIndex: Int): Int {
        if (circleIndex == bucketIndex) {
            return 10
        }
        currentLives--;
        return -20
    }

    private fun getScore(): Int {
        return score
    }

    private fun setLostGameMessages(){
        sign.setMessage("You Lost!")
        secondarySign.setMessage("Press on screen to play again!")

        sign.recenterText()
        secondarySign.recenterText()
    }

    private fun addCircles(number: Int): Array<Circle> {
        val result = Array<Circle>(number) {
            Circle(Random(System.nanoTime()).nextInt(0, 3))
        }
        result.forEach {
            it.setX(
                Random(System.nanoTime()).nextInt(
                    0,
                    Constants.SCREEN_WIDTH - Constants.getBitmapBank().getCircleWidth()
                )
            )
            if(roundNumber > 1){
                it.setVelocity(it.getVelocity() + (circleVelocityIncrease * roundNumber))
            }
        }
        return result
    }

    fun isRoundDone(): Boolean {
        return roundDone
    }

    fun getRoundNumber(): Int {
        return roundNumber
    }

    fun getTotalCircles(): Int {
        return totalCircles
    }

    fun getCircleCountInRound(): Int {
        return circleCountInRound
    }

    fun setCircleCountInRound(count: Int) {
        this.circleCountInRound = count
    }

    fun getCircles(): Array<Circle> {
        return circles
    }

    fun getHeldCircle(): Circle? {
        return heldCircle
    }

    fun setHeldCircle(heldCircle: Circle?) {
        this.heldCircle = heldCircle
    }

    fun isNoTouch(): Boolean {
        return noTouchState
    }

    fun lostGame(): Boolean {
        return lostGame
    }
}