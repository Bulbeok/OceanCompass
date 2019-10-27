package com.glic.oceancompass

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.main.*
import kotlinx.android.synthetic.main.reviewadd.*

class ReviewAddActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.reviewadd)
        upload.setOnClickListener {
        }
    }

    override fun onBackPressed() {
        startActivity(Intent(this,ReviewActivity::class.java))
        finish()
        overridePendingTransition(0, 0)
    }
}
