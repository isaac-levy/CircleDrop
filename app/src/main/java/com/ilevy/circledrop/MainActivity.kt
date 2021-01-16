package com.ilevy.circledrop

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.ilevy.circledrop.utils.Constants

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Constants.initialization(this.getApplicationContext())
    }

    fun startGame(view: View){
        val intent: Intent = Intent(this, GameActivity::class.java);
        startActivity(intent);
        finish();
    }
}
