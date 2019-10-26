package com.glic.oceancompass

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.transition.Slide
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.PopupWindow
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.android.volley.NetworkResponse
import com.android.volley.Request.Method.GET
import com.android.volley.Request.Method.POST
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.main.*
import java.util.*
import kotlin.collections.HashMap


open class MainActivity : AppCompatActivity() {

    val url = "http://175.206.239.109:8080/oceancompass/"

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
                val id = findViewById<EditText>(R.id.signinid).text.toString()
                val pwd = findViewById<EditText>(R.id.signinpwd).text.toString()

                val request = object : StringRequest(
                    POST, url+"LoginServlet",
                    //요청 성공 시
                    Response.Listener { response ->
                        Log.e("결과", "[$response]") },
                    // 에러 발생 시
                    Response.ErrorListener { error ->
                        Log.e("에러", "[" + error.message + "]") }) {
                    // request 시 key, value 보낼 때
                    override fun getParams(): Map<String, String> {
                        val params = HashMap<String, String>()
                        params["id"] = id
                        params["password"] = pwd
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


        sign_up.setOnClickListener {
            val view = LayoutInflater.from(this)
                .inflate(R.layout.sign_up, this.findViewById(R.id.main), false)
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

            val checkid = view.findViewById<Button>(R.id.checkid)
            checkid.setOnClickListener {
                val id = findViewById<EditText>(R.id.uesr_id).text.toString()
                Log.e("주소값 확인",url + "idCheckForm.jsp?id="+id)
                if(id.isNotEmpty()) {

                    Log.e("주소값 확인",url + "idCheckForm.jsp?id="+id)

                    /*val stringRequest = StringRequest(
                        GET, url + "idCheckForm.jsp?id="+id,
                        //요청 성공 시
                        Response.Listener { response ->
                            Log.e("결과", "[$response]")
                        },
                        // 에러 발생 시
                        Response.ErrorListener { error ->
                            Log.e("에러", "[" + error.message + "]")
                        })
                    // request queue
                    val requestQueue = Volley.newRequestQueue(this)
                    requestQueue.add(stringRequest)*/

                    /*val request = object : StringRequest(
                        GET, url + "idCheckForm.jsp",
                        //요청 성공 시
                        Response.Listener { response ->
                            Log.e("결과", "[$response]")
                        },
                        // 에러 발생 시
                        Response.ErrorListener { error ->
                            Log.e("에러", "[" + error.message + "]")
                        })
                    val queue = Volley.newRequestQueue(this)
                    queue.add(request)

                    Log.e("또다른결과", request.toString())*/
                }
            }
            popupWindow.showAtLocation(
                main,
                Gravity.CENTER,
                0,
                0
            )
        }
    }
}
