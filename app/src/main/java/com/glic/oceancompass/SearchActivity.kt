package com.glic.oceancompass

import android.content.Context
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
    private var day: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search)

        val pref = this.getSharedPreferences("history", Context.MODE_PRIVATE)
        val edit = pref.edit()

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
                day == 0 -> Toast.makeText(this, "여행 기간을 선택해주세요", Toast.LENGTH_LONG).show()
                else -> {
                    val count = pref.getInt("count", 1)
                    val randomString: String = List(20) { (('a'..'z') + ('A'..'Z') + ('0'..'9')).random() }.joinToString("")
                    edit.putString("$count", randomString)
                    edit.putString("$count"+"address", location)
                    edit.putInt("count", count+1)
                    edit.apply()
                    startActivity(Intent(this,RandomRecommendActivity::class.java)
                        .putExtra("key",randomString)
                        .putExtra("location",location)
                        .putExtra("play",play)
                        .putExtra("day",day)
                        .putExtra("historyCount",count))
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
        day = position
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
            if(data!!.extras!!.getString("city")!!.split(" ")[0] == data.extras!!.getString("city")!!.split(" ")[1]) {
                input_local.text = data.extras!!.getString("city")!!.split(" ")[0]
                location = data.extras!!.getString("city")!!
            } else {
                input_local.text = data.extras!!.getString("city")
                location = data.extras!!.getString("city")!!
            }
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
