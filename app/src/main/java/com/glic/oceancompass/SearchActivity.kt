package com.glic.oceancompass

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.search.*


class SearchActivity : AppCompatActivity() {

    private lateinit var location: String
    private lateinit var play: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search)
        val bottomNavigationView  = findViewById<View>(R.id.main_bottom_navigation_view) as BottomNavigationView

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

        recommend.setOnClickListener {
            startActivity(Intent(this,recommendActivity::class.java))
            finish()
        }

        radioButton1.setOnClickListener {
            radioButton1.isChecked=true
            radioButton2.isChecked=false
            button10.visibility = View.VISIBLE
            button11.visibility = View.VISIBLE
            button12.visibility = View.VISIBLE
            button13.visibility = View.VISIBLE
            direct_input.visibility = View.INVISIBLE
            day.visibility = View.INVISIBLE
        }
        radioButton2.setOnClickListener {
            radioButton2.isChecked=true
            radioButton1.isChecked=false
            button10.visibility = View.INVISIBLE
            button11.visibility = View.INVISIBLE
            button12.visibility = View.INVISIBLE
            button13.visibility = View.INVISIBLE
            direct_input.visibility = View.VISIBLE
            day.visibility = View.VISIBLE
        }

        input_local.setOnClickListener {
            startActivityForResult(Intent(this,SearchLocationActivity::class.java), 1)
        }
        input_play.setOnClickListener {
            startActivityForResult(Intent(this,SearchPlayActivity::class.java), 2)
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
