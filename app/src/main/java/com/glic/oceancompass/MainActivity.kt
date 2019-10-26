package com.glic.oceancompass

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.transition.Slide
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.android.volley.NetworkResponse
import com.android.volley.Request.Method.POST
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.main.*
import kotlinx.android.synthetic.main.sign_up.*
import java.util.*
import kotlin.collections.HashMap


open class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)

        search_picture.setOnClickListener {
            startActivity(Intent(this@MainActivity,SearchActivity::class.java))
            finish()
            overridePendingTransition(0, 0)
        }
        review_picture.setOnClickListener {
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
            }
            true
        }
        sign_in.setOnClickListener{
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
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
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

                val request = object : StringRequest(
                    POST, "http://175.206.239.109:8080/oceancompass/LoginServlet",
                    //요청 성공 시
                    Response.Listener { response ->
                        Log.e("결과", "[$response]") },
                    // 에러 발생 시
                    Response.ErrorListener { error ->
                        Log.e("에러", "[" + error.message + "]") }) {
                    // request 시 key, value 보낼 때
                    override fun getParams(): Map<String, String> {
                        val params = HashMap<String, String>()
                        params["id"] = "test"
                        params["password"] = "fbxmtjqj"
                        return params
                    }
                    override fun parseNetworkResponse(response: NetworkResponse?): Response<String> {
                        Log.e("결과response", response.toString())
                        val cookiesInfo : TreeMap<String,String> = response?.headers as TreeMap<String, String>
                        val cookie = cookiesInfo["Set-Cookie"]
                        Log.e("결과쿠키response", cookie.toString())

                        return super.parseNetworkResponse(response)
                    }
                }

                val queue = Volley.newRequestQueue(this)
                queue.add(request)

                Log.e("또다른결과", request.toString())

                popupWindow.dismiss()
            }
            popupWindow.setOnDismissListener {
                Toast.makeText(applicationContext,"로그인이 완료되었습니다.",Toast.LENGTH_SHORT).show()
            }
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
                slideOut.slideEdge = Gravity.END
                popupWindow.exitTransition = slideOut

            }
            val login = view.findViewById<Button>(R.id.Sign_upbutton)
            login.setOnClickListener {
                popupWindow.dismiss()
                Toast.makeText(applicationContext,"회원가입이 완료되었습니다.",Toast.LENGTH_SHORT).show()
            }
            popupWindow.showAtLocation(
                main,
                Gravity.CENTER,
                0,
                0
            )/*
            email_select.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    Log.d("MainActivity",
                        "onItemSelected : $position, ${email_select.getItemAtPosition(position)}")
                    when (email_select.getItemAtPosition(position)) {
                        "naver.com" -> {
                        }
                        "daum.net" -> {
                        }
                        "gmail.com" -> {
                        }
                        else -> {
                        }
                    }
                }
            }*/
    }
    }
}
