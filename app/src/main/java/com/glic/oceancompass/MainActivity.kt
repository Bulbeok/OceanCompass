package com.glic.oceancompass

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.transition.Slide
import android.transition.TransitionManager
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.PopupWindow
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)

        search_picture.setOnClickListener {
            startActivity(Intent(this@MainActivity,SearchActivity::class.java))
            overridePendingTransition(0, 0)
        }
        review_picture.setOnClickListener {
            startActivity(Intent(this@MainActivity,ReviewActivity::class.java))
            overridePendingTransition(0, 0)
        }
        val bottomNavigationView  = findViewById<View>(R.id.main_bottom_navigation_view) as BottomNavigationView
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                }
                R.id.search -> {
                    startActivity(Intent(this,SearchActivity::class.java))
                    overridePendingTransition(0, 0)
                }
                R.id.share -> {
                    startActivity(Intent(this,ReviewActivity::class.java))
                    overridePendingTransition(0, 0)
                }
            }
            true
        }
        sign_in.setOnClickListener{
            val view = LayoutInflater.from(this).inflate(R.layout.sign_in, this.findViewById(R.id.main),false)
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
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                // Create a new slide animation for popup window enter transition
                val slideIn = Slide()
                slideIn.slideEdge = Gravity.TOP
                popupWindow.enterTransition = slideIn

                // Slide animation for popup window exit transition
                val slideOut = Slide()
                slideOut.slideEdge = Gravity.RIGHT
                popupWindow.exitTransition = slideOut

            }

            val login = view.findViewById<Button>(R.id.Login_Button)
            login.setOnClickListener {
                //val myEmail = (findViewById<EditText>(R.id.Email)).getText().toString()
                //val myPasswd = (findViewById<EditText>(R.id.Password)).getText().toString()

                popupWindow.dismiss()
            }
            popupWindow.setOnDismissListener {
                Toast.makeText(applicationContext,"로그인이 완료되었습니다.",Toast.LENGTH_SHORT).show()
            }
            TransitionManager.beginDelayedTransition(main)
            popupWindow.showAtLocation(
                main,
                Gravity.CENTER,
                0,
                0
            )
        }
        sign_up.setOnClickListener{
            val view = LayoutInflater.from(this).inflate(R.layout.sign_up, this.findViewById(R.id.main),false)
            val popupWindow = PopupWindow(
                view,
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
            )
            // Set an elevation for the popup window
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                popupWindow.elevation = 10.0F
            }
            // If API level 23 or higher then execute the code
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                // Create a new slide animation for popup window enter transition
                val slideIn = Slide()
                slideIn.slideEdge = Gravity.TOP
                popupWindow.enterTransition = slideIn

                // Slide animation for popup window exit transition
                val slideOut = Slide()
                slideOut.slideEdge = Gravity.RIGHT
                popupWindow.exitTransition = slideOut

            }
            val login = view.findViewById<Button>(R.id.Sign_upbutton)
            login.setOnClickListener {
                popupWindow.dismiss()
                Toast.makeText(applicationContext,"회원가입이 완료되었습니다.",Toast.LENGTH_SHORT).show()
            }
            TransitionManager.beginDelayedTransition(main)
            popupWindow.showAtLocation(
                main,
                Gravity.CENTER,
                0,
                0
            )
        }


    }
}
