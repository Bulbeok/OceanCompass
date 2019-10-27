package com.glic.oceancompass

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
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

        val pref = this.getSharedPreferences("sessionCookie", Context.MODE_PRIVATE)
        val edit = pref.edit()

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

        NukeSSLCerts().nuke()

        login.setOnClickListener {
            if(login.text == "로그인") {
                if(id.text.toString() == "" || password.text.toString() == "") {
                    Toast.makeText(this, "아이디와 비밀번호를 입력해주세요", Toast.LENGTH_LONG).show()
                } else {
                    val request = object : StringRequest(
                        Method.POST, "https://175.206.239.109:8443/oceancompass/LoginServlet",
                        //요청 성공 시
                        Response.Listener {
                            if (it.toInt() != 1) {
                                Toast.makeText(this, "아이디와 비밀번호를 확인해주세요", Toast.LENGTH_LONG).show()
                            } else {
                                Toast.makeText(this, "로그인 성공", Toast.LENGTH_LONG).show()
                                id.isEnabled = false
                                password.isEnabled = false
                                login.text = "로그아웃"
                            }
                        },
                        // 에러 발생 시
                        Response.ErrorListener {
                            Log.e("에러", "[${it.message}]")
                        }) {
                        // request 시 key, value 보낼 때
                        override fun getParams(): Map<String, String> {
                            val params = HashMap<String, String>()
                            params["id"] = id.text.toString()
                            params["password"] = password.text.toString()
                            return params
                        }
                        override fun parseNetworkResponse(response: NetworkResponse?): Response<String> {
                            val cookiesInfo: TreeMap<String, String> = response?.headers as TreeMap<String, String>
                            edit.putString("sessionid", cookiesInfo["Set-Cookie"])
                            edit.apply()
                            return super.parseNetworkResponse(response)
                        }
                    }
                    val queue = Volley.newRequestQueue(this)
                    queue.add(request)
                }
            } else if(login.text == "로그아웃") {

            }
        }
        signup.setOnClickListener {
            startActivity(Intent(this,SignUpActivity::class.java))
            finish()
        }
    }

    override fun onBackPressed() {
        startActivity(Intent(this@SignInActivity, MainActivity::class.java))
        finish()
        overridePendingTransition(0, 0)
    }
}