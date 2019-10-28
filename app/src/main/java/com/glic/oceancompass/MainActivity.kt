package com.glic.oceancompass

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.main.*


open class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)

        search.setOnClickListener {
            startActivity(Intent(this@MainActivity,SearchActivity::class.java))
            finish()
            overridePendingTransition(0, 0)
        }
        review.setOnClickListener {
            startActivity(Intent(this@MainActivity,ReviewActivity::class.java))
            finish()
            overridePendingTransition(0, 0)
        }

        val bottomNavigationView  = findViewById<View>(R.id.main_bottom_navigation_view) as BottomNavigationView
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                }
                R.id.search -> {
                    startActivity(Intent(this,SearchActivity::class.java))
                    finish()
                    overridePendingTransition(0, 0)
                }
                R.id.share -> {
                    startActivity(Intent(this,ReviewActivity::class.java))
                    finish()
                    overridePendingTransition(0, 0)
                }
                R.id.mypage -> {
                    startActivity(Intent(this@MainActivity,SignInActivity::class.java))
                    finish()
                    overridePendingTransition(0, 0)
                }
            }
            true
        }
    }

    override fun onBackPressed() {
        val pref = this.getSharedPreferences("sessionCookie", Context.MODE_PRIVATE)
        val edit = pref.edit()
        edit.putString("sessionCookie", "")
        edit.apply()
        super.onBackPressed()
    }
}
