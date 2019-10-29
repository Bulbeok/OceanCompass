package com.glic.oceancompass

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.main.*


open class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)

        NukeSSLCerts().nuke()

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

        tutlehint.setOnClickListener {
            location.visibility = View.VISIBLE
            tutlehint.visibility = View.INVISIBLE
            tutlehinttext.visibility = View.INVISIBLE
        }

        location.setOnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                if(location.text.toString() != "") {
                    val request = object : StringRequest(
                        Method.POST, "https://175.206.239.109:8443/oceancompass/WeatherServlet",
                        //요청 성공 시
                        Response.Listener {
                            if(it.trim() == "1") {
                                Toast.makeText(this,"위치를 다시 입력해 주세요",Toast.LENGTH_LONG).show()
                            } else {
                                when(Integer.parseInt(it.split(",")[3])) {
                                    1, 4 -> backgroundweather.setBackgroundResource(R.drawable.background_rain)
                                    2, 3 -> backgroundweather.setBackgroundResource(R.drawable.background_snow)
                                    else -> backgroundweather.setBackgroundResource(R.drawable.background1)
                                }
                                temperature.text = it.split(",")[0]
                                humidity.text = it.split(",")[1]
                            }
                        },
                        // 에러 발생 시
                        Response.ErrorListener {
                            Log.e("에러", "[${it.message}]")
                        }) {
                        override fun getParams(): Map<String, String> {
                            val params = HashMap<String, String>()
                            params["loca"] = location.text.toString()
                            return params
                        }
                    }
                    val queue = Volley.newRequestQueue(this)
                    queue.add(request)
                }
            }
            false
        }
    }
}
