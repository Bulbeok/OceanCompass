package com.glic.oceancompass

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.main.*
import kotlinx.android.synthetic.main.search.*


class SearchActivity : AppCompatActivity() {

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
            view ->
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
                view ->
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
            //소스 수정 필요!
            /*val view = LayoutInflater.from(this).inflate(R.layout.sign_in, this.findViewById(R.id.search_layout),false)
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

            val signin = view.findViewById<Button>(R.id.Login_Button)
            signin.setOnClickListener {
                //val myEmail = (findViewById<EditText>(R.id.Email)).getText().toString()
                //val myPasswd = (findViewById<EditText>(R.id.Password)).getText().toString()

                popupWindow.dismiss()
            }
            popupWindow.showAtLocation(
                search_layout,
                Gravity.CENTER,
                0,
                0
            )*/
        }
    }

    override fun onBackPressed() {
        startActivity(Intent(this@SearchActivity,MainActivity::class.java))
        finish()
        overridePendingTransition(0, 0)
    }
}
