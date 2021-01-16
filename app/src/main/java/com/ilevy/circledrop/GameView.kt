package com.ilevy.circledrop

import android.content.Context
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.ilevy.circledrop.models.Circle
import com.ilevy.circledrop.utils.Constants

class GameView(context: Context?): SurfaceView(context), SurfaceHolder.Callback {
    private lateinit var gameThread: GameThread

    init {
        initView()
    }

    override fun surfaceCreated(holder: SurfaceHolder?) {
        if(!gameThread.isRunning()){
            gameThread = GameThread(holder!!)
            gameThread.start()
        } else {
            gameThread.start()
        }
    }

    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {

    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {
        if(gameThread.isRunning()){
            gameThread.setIsRunning(false)
            var retry: Boolean = true
            while(retry){
                try {
                    gameThread.join()
                    retry = false
                } catch(e: InterruptedException){}
            }
        }
    }

    private fun initView(){
        val holder: SurfaceHolder = getHolder()
        holder.addCallback(this)
        setFocusable(true)
        gameThread = GameThread(holder)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val action: Int = event!!.getAction()
        val circles = Constants.getGameEngine().getCircles()

        when (action) {
            MotionEvent.ACTION_DOWN -> {
                if(!Constants.getGameEngine().isRoundDone()){
                    for (i in circles.indices) {
                        if (event.x > circles[i].getX().toFloat()
                            && event.x < (circles[i].getX().toFloat() + Constants.getBitmapBank().getCircleWidth())
                            && event.y > circles[i].getY()
                            && event.y < circles[i].getY() + Constants.getBitmapBank().getCircleHeight()
                        ) {
                            Constants.getGameEngine().setHeldCircle(circles[i])
                            break
                        }
                    }
                }
                else {
                    if(!Constants.getGameEngine().isNoTouch()){
                        if(Constants.getGameEngine().lostGame()){
                            Constants.getGameEngine().setUpRound(1)
                        } else {
                            Constants.getGameEngine().setUpRound(
                                Constants.getGameEngine().getRoundNumber() + 1)
                        }
                    }
                }
            }
            MotionEvent.ACTION_MOVE -> {
                if(Constants.getGameEngine().getHeldCircle() != null){
                    val circle: Circle = Constants.getGameEngine().getHeldCircle()!!
                    //Center circle on finger and track finger movement while pressed to screen
                    circle.setX(event.x.toInt() - (Constants.getBitmapBank().getCircleWidth() / 2))
                    circle.setY(event.y.toInt() - (Constants.getBitmapBank().getCircleHeight() / 2))

                    //Keep circle within bounds of screen
                    if(circle.getX() < 0) {
                        circle.setX(0)
                    }
                    else if(circle.getX() + Constants.getBitmapBank().getCircleWidth() > Constants.SCREEN_WIDTH){
                        circle.setX(Constants.SCREEN_WIDTH - Constants.getBitmapBank().getCircleWidth())
                    }
                }
            }
            MotionEvent.ACTION_UP -> {
                Constants.getGameEngine().setHeldCircle(null)
            }
        }
        return true
    }
}