package com.glic.oceancompass

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.recommend.*

class RecommendActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recommend)

        Log.e("테스트", intent.toString())
        if (!intent.hasExtra("count")) {
            day.text = "1일차"
            complete.text = "1일차 완성"
        } else {
            day.text = intent.getStringExtra("count")!! + "일차"
            complete.text = intent.getStringExtra("count")!! + "일차"
        }
        location.text = intent.getStringExtra("location")

        type1.setOnClickListener {
            startActivityForResult(Intent(this, RecommendSelectActivity::class.java).putExtra("play",intent.getStringExtra("play")), 1)
        }
        type2.setOnClickListener {
            startActivityForResult(Intent(this, RecommendSelectActivity::class.java).putExtra("play",intent.getStringExtra("play")), 2)
        }
        type3.setOnClickListener {
            startActivityForResult(Intent(this, RecommendSelectActivity::class.java).putExtra("play",intent.getStringExtra("play")), 3)
        }
        type4.setOnClickListener {
            startActivityForResult(Intent(this, RecommendSelectActivity::class.java).putExtra("play",intent.getStringExtra("play")), 4)
        }
        type5.setOnClickListener {
            startActivityForResult(Intent(this, RecommendSelectActivity::class.java).putExtra("play",intent.getStringExtra("play")), 5)
        }
        type6.setOnClickListener {
            startActivityForResult(Intent(this, RecommendSelectActivity::class.java).putExtra("play",intent.getStringExtra("play")), 6)
        }
        type7.setOnClickListener {
            startActivityForResult(Intent(this, RecommendSelectActivity::class.java).putExtra("play",intent.getStringExtra("play")), 7)
        }
        type8.setOnClickListener {
            startActivityForResult(Intent(this, RecommendSelectActivity::class.java).putExtra("play",intent.getStringExtra("play")), 8)
        }
        type9.setOnClickListener {
            startActivityForResult(Intent(this, RecommendSelectActivity::class.java).putExtra("play",intent.getStringExtra("play")), 9)
        }
        type10.setOnClickListener {
            startActivityForResult(Intent(this, RecommendSelectActivity::class.java).putExtra("play",intent.getStringExtra("play")), 10)
        }
        type11.setOnClickListener {
            startActivityForResult(Intent(this, RecommendSelectActivity::class.java).putExtra("play",intent.getStringExtra("play")), 11)
        }
        type12.setOnClickListener {
            startActivityForResult(Intent(this, RecommendSelectActivity::class.java).putExtra("play",intent.getStringExtra("play")), 12)
        }

        complete.setOnClickListener {

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == 1) {
            type1.text = data!!.extras!!.getString("type")!!
        } else if(resultCode == RESULT_OK && requestCode == 2) {
            type2.text = data!!.extras!!.getString("type")!!
        }else if(resultCode == RESULT_OK && requestCode == 3) {
            type3.text = data!!.extras!!.getString("type")!!
        }else if(resultCode == RESULT_OK && requestCode == 4) {
            type4.text = data!!.extras!!.getString("type")!!
        }else if(resultCode == RESULT_OK && requestCode == 5) {
            type5.text = data!!.extras!!.getString("type")!!
        }else if(resultCode == RESULT_OK && requestCode == 6) {
            type6.text = data!!.extras!!.getString("type")!!
        }else if(resultCode == RESULT_OK && requestCode == 7) {
            type7.text = data!!.extras!!.getString("type")!!
        }else if(resultCode == RESULT_OK && requestCode == 8) {
            type8.text = data!!.extras!!.getString("type")!!
        }else if(resultCode == RESULT_OK && requestCode == 9) {
            type9.text = data!!.extras!!.getString("type")!!
        }else if(resultCode == RESULT_OK && requestCode == 10) {
            type10.text = data!!.extras!!.getString("type")!!
        }else if(resultCode == RESULT_OK && requestCode == 11) {
            type11.text = data!!.extras!!.getString("type")!!
        }else if(resultCode == RESULT_OK && requestCode == 12) {
            type12.text = data!!.extras!!.getString("type")!!
        }
    }

}
