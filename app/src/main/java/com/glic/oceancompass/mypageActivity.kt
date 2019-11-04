package com.glic.oceancompass

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_mypage.*
import kotlinx.android.synthetic.main.signin.*

class mypageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage)

        myreview.setOnClickListener {
                startActivity(Intent(this, myreviewActivity::class.java))
                finish()
        }
        history.setOnClickListener {
            startActivity(Intent(this, historyActivity::class.java))
            finish()
        }
    }
}
