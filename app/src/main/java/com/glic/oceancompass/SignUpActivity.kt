package com.glic.oceancompass

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup)
    }

    override fun onBackPressed() {
        startActivity(Intent(this@SignUpActivity, SignInActivity::class.java))
        finish()
    }
}
