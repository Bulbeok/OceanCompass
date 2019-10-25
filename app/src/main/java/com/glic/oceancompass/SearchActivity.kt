package com.glic.oceancompass

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.Slide
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.PopupWindow
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.search.*

class SearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search)
        val bottomNavigationView  = findViewById<View>(R.id.main_bottom_navigation_view) as BottomNavigationView

        bottomNavigationView.menu.findItem(R.id.search_layout).isChecked = true;
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
            }
            true
        }
        input_local.setOnClickListener {
            val view = LayoutInflater.from(this).inflate(R.layout.sign_in, this.findViewById(R.id.search_layout),false)
            val popupWindow = PopupWindow(
                view,
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
            )
            popupWindow.isFocusable = true //팝업윈도우 키보드강제화
            popupWindow.isOutsideTouchable //외부터치시 닫기
            // Set an elevation for the popup window
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                popupWindow.elevation = 10.0F
            }
            // If API level 23 or higher then execute the code
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                // Create a new slide animation for popup window enter transition
                val slideIn = Slide()
                slideIn.slideEdge = Gravity.TOP
                popupWindow.enterTransition = slideIn

                // Slide animation for popup window exit transition
                val slideOut = Slide()
                slideOut.slideEdge = Gravity.END
                popupWindow.exitTransition = slideOut

            }

            val login = view.findViewById<Button>(R.id.Login_Button)
            login.setOnClickListener {
                //val myEmail = (findViewById<EditText>(R.id.Email)).getText().toString()
                //val myPasswd = (findViewById<EditText>(R.id.Password)).getText().toString()

                popupWindow.dismiss()
            }
            popupWindow.showAtLocation(
                search_layout,
                Gravity.CENTER,
                0,
                0
            )
        }
    }

    override fun onBackPressed() {
        startActivity(Intent(this@SearchActivity,MainActivity::class.java))
        finish()
        overridePendingTransition(0, 0)
    }
}
