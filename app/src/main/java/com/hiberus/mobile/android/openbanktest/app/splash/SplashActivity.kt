package com.hiberus.mobile.android.openbanktest.app.splash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hiberus.mobile.android.openbanktest.app.common.navigation.Navigator

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Thread.sleep(1000)
        Navigator.navigateToMainActivity(this)
    }

}