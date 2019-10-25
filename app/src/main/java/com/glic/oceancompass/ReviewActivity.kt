package com.glic.oceancompass

import android.content.Intent
import android.net.http.SslError
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.SslErrorHandler
import android.webkit.WebView
import android.webkit.WebViewClient
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
            }
            true
        }
        sharing_button.setOnClickListener {
            startActivity(Intent(this,ReviewAddActivity::class.java))
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
    }

    override fun onBackPressed() {
        startActivity(Intent(this@ReviewActivity,MainActivity::class.java))
        finish()
        overridePendingTransition(0, 0)
    }
}
