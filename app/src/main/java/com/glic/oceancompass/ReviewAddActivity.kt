package com.glic.oceancompass

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class ReviewAddActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.reviewadd)

    }

    override fun onBackPressed() {
        startActivity(Intent(this,ReviewActivity::class.java))
        finish()
        overridePendingTransition(0, 0)
    }
}
