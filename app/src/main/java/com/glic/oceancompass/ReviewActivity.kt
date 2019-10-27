package com.glic.oceancompass

import android.content.Context
import android.content.Intent
import android.net.http.SslError
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request.Method.POST
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.review.*


class ReviewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.review)
        val bottomNavigationView  = findViewById<View>(R.id.main_bottom_navigation_view) as BottomNavigationView
        bottomNavigationView.menu.findItem(R.id.share).isChecked = true
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
                }
                R.id.mypage -> {
                    startActivity(Intent(this,SignInActivity::class.java))
                    finish()
                    overridePendingTransition(0, 0)
                }
            }
            true
        }

        NukeSSLCerts().nuke()

        addreview.setOnClickListener {
            val pref = this.getSharedPreferences("sessionCookie", Context.MODE_PRIVATE)
            val sessionId = pref.getString("sessionCookie", null)
            if (!sessionId.isNullOrEmpty() || sessionId != "") {
                val request = object : StringRequest(
                    POST, "https://175.206.239.109:8443/oceancompass/mailcheck.jsp",
                    //요청 성공 시
                    Response.Listener {
                        if(it.trim() == "0") {
                            Toast.makeText(this, "이메일 인증 후 리뷰 작성이 가능합니다.", Toast.LENGTH_LONG).show()
                        } else {
                            startActivity(Intent(this,ReviewAddActivity::class.java))
                            finish()
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
                val queue = Volley.newRequestQueue(this)
                queue.add(request)
            } else {
                Toast.makeText(this, "로그인 후 리뷰 작성이 가능합니다.", Toast.LENGTH_LONG).show()
            }
        }

        val myWebView = findViewById<View>(R.id.webView1) as WebView

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true)
        }
        val settings = myWebView.settings
        settings.javaScriptEnabled = true
        settings.domStorageEnabled = true
        myWebView.loadUrl("https://175.206.239.109:8443/oceancompass/map.jsp")
        myWebView.webViewClient = object : WebViewClient(){
            override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
                handler!!.proceed()
            }
        }
        myWebView.webChromeClient = object : WebChromeClient() {
            override fun onGeolocationPermissionsShowPrompt(
                origin: String,
                callback: GeolocationPermissions.Callback
            ) {
                super.onGeolocationPermissionsShowPrompt(origin, callback)
                callback.invoke(origin, true, false)
            }
        }
    }

    override fun onBackPressed() {
        startActivity(Intent(this@ReviewActivity,MainActivity::class.java))
        finish()
        overridePendingTransition(0, 0)
    }
}
