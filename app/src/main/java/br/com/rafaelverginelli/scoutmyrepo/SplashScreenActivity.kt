package br.com.rafaelverginelli.scoutmyrepo

import abstractions.CustomAppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.os.Handler

class SplashScreenActivity : CustomAppCompatActivity() {

    val SPLASH_TIME = 3000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val splashHandler = Handler()
        splashHandler.postDelayed({
            startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
        }, SPLASH_TIME)
    }
}
