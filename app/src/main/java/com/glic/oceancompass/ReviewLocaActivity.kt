package com.glic.oceancompass

import android.content.Context
import android.content.Intent
import android.net.http.SslError
import android.os.Bundle
import android.os.Handler
import android.webkit.*
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.review.*

class ReviewLocaActivity : AppCompatActivity() {

    companion object {
        var context : Context? = null
        var handler : Handler? = Handler()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review_loca)

        NukeSSLCerts().nuke()

        context = this

        search.setOnClickListener {
            search.isIconified = false
        }

        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                map.loadUrl("https://175.206.239.109:8443/oceancompass/mobilemap2.jsp?address=$query")
                return true
            }
            override fun onQueryTextChange(newText: String): Boolean {
                return true
            }
        })

        map.settings.javaScriptEnabled = true
        map.settings.domStorageEnabled = true
        map.addJavascriptInterface(WebBrideg(),"address")
        map.loadUrl("https://175.206.239.109:8443/oceancompass/mobilemap2.jsp")
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

    class WebBrideg{
        @JavascriptInterface
        fun getAddress(num : String){
            handler?.post {
                Toast.makeText(context,"선택한 위치는 $num 입니다.",Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onBackPressed() {
        startActivity(Intent(this,ReviewAddActivity::class.java))
        finish()
        overridePendingTransition(0, 0)
    }
}