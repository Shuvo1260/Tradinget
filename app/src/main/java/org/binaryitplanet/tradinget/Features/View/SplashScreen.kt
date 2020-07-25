package org.binaryitplanet.tradinget.Features.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.view.WindowManager
import org.binaryitplanet.tradinget.R

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)


        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_splash_screen)


        val countDownTimer = object : CountDownTimer(4000, 1000){
            override fun onFinish() {

                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.lefttoright, R.anim.righttoleft)
                finish()
            }

            override fun onTick(millisUntilFinished: Long) {
                //
            }

        }

        countDownTimer.start()
    }
}