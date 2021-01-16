package com.ilevy.circledrop.utils

import android.content.Context
import android.content.SharedPreferences
import android.util.DisplayMetrics
import android.view.Display
import android.view.WindowManager
import com.ilevy.circledrop.GameEngine
import com.ilevy.circledrop.models.BitmapBank

class Constants {
    companion object {
        private lateinit var bitmapBank: BitmapBank
        private lateinit var gameEngine: GameEngine
        private lateinit var preferences: SharedPreferences
        private lateinit var editor: SharedPreferences.Editor
        val preferencesKey: String = "myPreferences"
        val highScoreKey: String = "highScore"
        var SCREEN_WIDTH: Int = 0
        var SCREEN_HEIGHT: Int = 0
        val numOfBuckets: Int = 3
        var bucketWidth: Int = 0
        val bucketHeight: Float = 250f

        fun initialization(context: Context){
            setScreenSize(context)
            preferences = context.getSharedPreferences(
                preferencesKey, Context.MODE_PRIVATE)
            editor = preferences.edit()
            bitmapBank = BitmapBank(context.getResources())
            gameEngine =
                GameEngine()
            bucketWidth = SCREEN_WIDTH / numOfBuckets
        }

        fun getBitmapBank(): BitmapBank {
            return bitmapBank
        }

        fun getGameEngine(): GameEngine {
            return gameEngine
        }

        fun getHighScore(): Int {
            return preferences.getInt(highScoreKey, 0)
        }

        fun setHighScore(highScore: Int) {
            editor.putInt(highScoreKey, highScore)
            editor.commit()
        }

        private fun setScreenSize(context: Context){
            val wm : WindowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val display: Display = wm.getDefaultDisplay()
            val metrics: DisplayMetrics = DisplayMetrics()
            display.getMetrics(metrics)
            val width : Int = metrics.widthPixels
            val height : Int = metrics.heightPixels
            SCREEN_WIDTH = width
            SCREEN_HEIGHT = height
        }
    }
}