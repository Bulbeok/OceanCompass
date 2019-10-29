package com.glic.oceancompass

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.NetworkResponse
import com.android.volley.Request.Method.POST
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
        val sessionId = pref.getString("sessionCookie", null)

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

        var temp = true
        val request2 = object : StringRequest(
            POST, "https://175.206.239.109:8443/oceancompass/mailcheck.jsp",
            //요청 성공 시
            Response.Listener {
                if(it.trim() == "2") {
                    temp = true
                } else {
                    id.isEnabled = false
                    password.isEnabled = false
                    temp = false
                    id.setText(pref.getString("sessionId", null))
                    login.text = "로그아웃"
                }
            },
            // 에러 발생 시
            Response.ErrorListener {
                Log.e("에러", "[${it.message}]")
            }) {
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers["Cookie"] = sessionId!!
                return headers
            }
        }
        val queue2 = Volley.newRequestQueue(this)
        queue2.add(request2)

        login.setOnClickListener {
            if(temp) {
                if(id.text.toString() == "" || password.text.toString() == "") {
                    Toast.makeText(this, "아이디와 비밀번호를 입력해주세요", Toast.LENGTH_LONG).show()
                } else {
                    val request = object : StringRequest(
                        POST, "https://175.206.239.109:8443/oceancompass/LoginServlet",
                        //요청 성공 시
                        Response.Listener {
                            if (it != "1") {
                                Toast.makeText(this, "아이디와 비밀번호를 확인해주세요", Toast.LENGTH_LONG).show()
                                edit.putString("sessionCookie", "")
                                edit.apply()
                            } else {
                                Toast.makeText(this, "로그인 되었습니다.", Toast.LENGTH_LONG).show()
                                id.isEnabled = false
                                password.isEnabled = false
                                temp = false
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
                            edit.putString("sessionCookie", cookiesInfo["Set-Cookie"]!!.split(";")[0])
                            edit.putString("sessionId", id.text.toString())
                            edit.apply()
                            return super.parseNetworkResponse(response)
                        }
                    }
                    val queue = Volley.newRequestQueue(this)
                    queue.add(request)
                }
            } else {
                val request = object : StringRequest(
                    POST, "https://175.206.239.109:8443/oceancompass/logout.jsp",
                    //요청 성공 시
                    Response.Listener {
                        edit.putString("sessionCookie", "")
                        edit.putString("sessionId", "")
                        edit.apply()
                        Toast.makeText(this, "로그아웃 되었습니다.", Toast.LENGTH_LONG).show()
                        id.isEnabled = true
                        password.isEnabled = true
                        temp = true
                        login.text = "로그인"
                    },
                    // 에러 발생 시
                    Response.ErrorListener {
                        Log.e("에러", "[${it.message}]")
                    }) {
                    override fun getHeaders() : Map<String, String> {
                        val headers = HashMap<String, String>()
                        headers["Cookie"] = sessionId!!
                        return headers
                    }
                }
                val queue = Volley.newRequestQueue(this)
                queue.add(request)

                id.setText("")
                password.setText("")
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