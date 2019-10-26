package com.glic.oceancompass

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.NetworkResponse
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.signin.*
import java.util.*
import kotlin.collections.HashMap

class SignInActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signin)

        val bottomNavigationView  = findViewById<View>(R.id.main_bottom_navigation_view) as BottomNavigationView
        bottomNavigationView.menu.findItem(R.id.mypage).isChecked = true
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    startActivity(Intent(this,MainActivity::class.java))
                    finish()
                    overridePendingTransition(0, 0)
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
                }
            }
            true
        }

        val login = findViewById<Button>(R.id.Login_Button)
        login.setOnClickListener {
            val id = findViewById<EditText>(R.id.id).text.toString()
            val password = findViewById<EditText>(R.id.password).text.toString()

            val request = object : StringRequest(
                Method.POST, "http://175.206.239.109:8080/oceancompass/LoginServlet",
                //요청 성공 시
                Response.Listener { response ->
                    Log.e("결과", "[$response]")
                },
                // 에러 발생 시
                Response.ErrorListener { error ->
                    Log.e("에러", "[" + error.message + "]")
                }) {
                // request 시 key, value 보낼 때
                override fun getParams(): Map<String, String> {
                    val params = HashMap<String, String>()
                    params["id"] = id
                    params["password"] = password
                    return params
                }
                override fun parseNetworkResponse(response: NetworkResponse?): Response<String> {
                    val cookiesInfo: TreeMap<String, String> = response?.headers as TreeMap<String, String>
                    val cookie = cookiesInfo["Set-Cookie"]
                    return super.parseNetworkResponse(response)
                }
            }
            val queue = Volley.newRequestQueue(this)
            queue.add(request)
        }
        Sign_upButton.setOnClickListener {
            startActivity(Intent(this,SignUpActivity::class.java))
            finish()
        }
    }
}
