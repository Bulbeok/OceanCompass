package com.glic.oceancompass

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.search.*


class SearchActivity : AppCompatActivity() {

    private var location: String = ""
    private var play: String = ""
    private var day: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search)
        val bottomNavigationView  = findViewById<View>(R.id.bottom_navigation_view) as BottomNavigationView

        bottomNavigationView.menu.findItem(R.id.search).isChecked = true
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    startActivity(Intent(this,MainActivity::class.java))
                    finish()
                    overridePendingTransition(0, 0)
                }
                R.id.search -> {
                }
                R.id.share -> {
                    startActivity(Intent(this,ReviewActivity::class.java))
                    finish()
                    overridePendingTransition(0, 0)
                }
                R.id.mypage -> {
                    startActivity(Intent(this,SignInActivity::class.java))
                    finish()
                    overridePendingTransition(0, 0)
                }
            }
            true
        }

        input_local.setOnClickListener {
            startActivityForResult(Intent(this,SearchLocationActivity::class.java), 1)
        }
        input_play.setOnClickListener {
            startActivityForResult(Intent(this,SearchPlayActivity::class.java), 2)
        }

        tour_search.setOnClickListener {
            when {
                location == "" -> Toast.makeText(this, "위치를 선택해주세요", Toast.LENGTH_LONG).show()
                play == "" -> Toast.makeText(this, "놀거리를 선택해주세요", Toast.LENGTH_LONG).show()
                day == "" -> Toast.makeText(this, "여행 기간을 선택해주세요", Toast.LENGTH_LONG).show()
                else -> {
                    startActivity(Intent(this,RecommendActivity::class.java)
                        .putExtra("location",location)
                        .putExtra("play",play)
                        .putExtra("day",day))
                    finish()
                }
            }
        }

        day1.setOnClickListener {
            setColor(it, 1)
        }
        day2.setOnClickListener {
            setColor(it, 2)
        }
        day3.setOnClickListener {
            setColor(it, 3)
        }
        day4.setOnClickListener {
            setColor(it, 4)
        }
        day5.setOnClickListener {
            setColor(it, 5)
        }
        day6.setOnClickListener {
            setColor(it, 6)
        }
        day7.setOnClickListener {
            setColor(it, 7)
        }
    }

    private fun setColor(button: View, position: Int) {
        button.setBackgroundColor(Color.parseColor("#008577"))
        findViewById<Button>(resources.getIdentifier("day$position", "id", packageName)).setTextColor(Color.parseColor("#FFFFFF"))
        day = position.toString()
        for (i in 1 .. 7) {
            if(position == i) {
                continue
            } else {
                findViewById<Button>(resources.getIdentifier("day$i", "id", packageName)).setBackgroundColor(Color.parseColor("#FFFFFF"))
                findViewById<Button>(resources.getIdentifier("day$i", "id", packageName)).setTextColor(Color.parseColor("#000000"))
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == 1) {
            input_local.text = data!!.extras!!.getString("city")
            location = data.extras!!.getString("city")!!
        } else if(resultCode == RESULT_OK && requestCode == 2) {
            if(data!!.extras!!.getString("play") != "") {
                input_play.text = data.extras!!.getString("play")
                play = data.extras!!.getString("play")!!
            }
        }
    }

    override fun onBackPressed() {
        startActivity(Intent(this@SearchActivity,MainActivity::class.java))
        finish()
        overridePendingTransition(0, 0)
    }
}
