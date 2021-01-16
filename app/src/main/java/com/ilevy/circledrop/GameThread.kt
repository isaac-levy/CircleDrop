package com.ilevy.circledrop

import android.graphics.Canvas
import android.os.SystemClock
import android.util.Log
import android.view.SurfaceHolder
import com.ilevy.circledrop.utils.Constants

class GameThread(private val surfaceHolder: SurfaceHolder) : Thread() {
    private var isRunning: Boolean = true
    private var startTime: Long = 0
    private var loopTime: Long = 0
    private var totalTime: Long = 0
    private var lastIncrement: Long = 0
    private val DELAY = 33

    override fun run() {
        while (isRunning) {
            val canvas: Canvas = surfaceHolder.lockCanvas(null)
            startTime = SystemClock.uptimeMillis()

            synchronized(surfaceHolder) {
                Constants.getGameEngine().updateAndDrawBackground(canvas)
                Constants.getGameEngine().updateAndDrawCircle(canvas)
                Constants.getGameEngine().updateAndDrawBuckets(canvas)
                if (Constants.getGameEngine().isRoundDone()) {
                    Constants.getGameEngine().updateAndDrawMessage(canvas)
                }
                surfaceHolder.unlockCanvasAndPost(canvas)
            }
            loopTime = SystemClock.uptimeMillis() - startTime

            if (loopTime < DELAY) {
                try {
                    Thread.sleep(DELAY - loopTime);
                } catch (e: InterruptedException) {
                    Log.e("Interrupted", "Interrupted while sleeping")
                }
            }
            totalTime = totalTime + (SystemClock.uptimeMillis() - startTime)
            if (Constants.getGameEngine().getCircleCountInRound() < Constants.getGameEngine().getTotalCircles() && totalTime - lastIncrement > 1000) {
                Constants.getGameEngine().setCircleCountInRound(
                    Constants.getGameEngine().getCircleCountInRound() + 1
                )
                lastIncrement = totalTime
            }

            if (Constants.getGameEngine().isRoundDone()) {
                totalTime = 0
                lastIncrement = 0
            }
        }
    }

    fun isRunning(): Boolean {
        return isRunning
    }

    fun setIsRunning(state: Boolean) {
        isRunning = state
    }
}