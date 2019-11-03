package com.glic.oceancompass

import android.content.Context
import android.net.http.SslError
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.*
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.recommedresult.*
import kotlinx.android.synthetic.main.recommed_recycleview.*

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
        //map.loadUrl("https://175.206.239.109:8443/oceancompass/route.jsp")
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
        Log.e("테스트",value)
        val temp = value.split(",")
        Log.e("테스트사이즈", temp.size.toString())
        for (i in 1 until temp.size) {
            Log.e("테스트배열", temp[i-1])
            layoutInflater.inflate(R.layout.recommed_recycleview, findViewById(R.id.recommedresult), false)
                .findViewById<TextView>(resources.getIdentifier("type$i", "id", packageName)).text = temp[i-1]
            if(i != 1) {
                layoutInflater.inflate(R.layout.recommed_recycleview, findViewById(R.id.recommedresult), false)
                    .findViewById<ImageView>(resources.getIdentifier("imageView$i", "id", packageName)).visibility = View.VISIBLE
            }
        }
    }
}
