package jmapps.lessonsoframadan.presentation.ui.firstScreen

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import jmapps.lessonsoframadan.presentation.ui.mainScreen.MainActivity

class SplashActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Handler().postDelayed({
            val toMainActivity = Intent(this, MainActivity::class.java)
            startActivity(toMainActivity)
            finish()
        }, 500)
    }
}