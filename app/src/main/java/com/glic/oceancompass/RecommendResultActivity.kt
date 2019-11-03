package com.glic.oceancompass

import android.content.Context
import android.content.Intent
import android.net.http.SslError
import android.os.Bundle
import android.webkit.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.recommedresult.*

class RecommendResultActivity : AppCompatActivity(), RecycleViewClick {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recommedresult)

        val key = intent.getStringExtra("key")!!
        val count = intent.getIntExtra("count",1)
        val pref = this.getSharedPreferences(key, Context.MODE_PRIVATE)

        val recommendList = arrayListOf<String>()

        for (i in 1..count) {
            recommendList.add(pref.getString("$count", null)!!)
        }

        result.adapter = RecycleViewAdapter(6, recommendList, this@RecommendResultActivity,this@RecommendResultActivity)
        result.layoutManager = LinearLayoutManager(this@RecommendResultActivity)
        result.setHasFixedSize(true)

        map.settings.javaScriptEnabled = true
        map.settings.domStorageEnabled = true
        map.webViewClient = object : WebViewClient(){
            override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
                handler!!.proceed()
            }
        }
        map.webChromeClient = object : WebChromeClient() {
            override fun onGeolocationPermissionsShowPrompt(origin: String, callback: GeolocationPermissions.Callback) {
                callback.invoke(origin, true, false)
            }
        }
    }

    override fun stateClick(value: String) {
        map.loadUrl("https://175.206.239.109:8443/oceancompass/route.jsp?type=$value")
    }

    override fun cityClick(value: String) {
    }

    override fun onBackPressed() {
        startActivity(Intent(this,SearchActivity::class.java))
        finish()
    }
}
