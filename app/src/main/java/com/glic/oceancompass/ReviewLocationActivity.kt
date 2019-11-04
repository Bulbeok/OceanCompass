package com.glic.oceancompass

import android.app.Activity
import android.net.http.SslError
import android.os.Bundle
import android.os.Handler
import android.webkit.*
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.reviewloca.*


class ReviewLocationActivity : AppCompatActivity() {

    var handler : Handler? = Handler()
    var address : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.reviewloca)

        NukeSSLCerts().nuke()

        send.setOnClickListener {
            val intent = intent
            intent.putExtra("Date", address)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }

        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                map.loadUrl("https://175.206.239.109:8443/oceancompass/map.jsp?address=$query")
                return true
            }
            override fun onQueryTextChange(newText: String): Boolean {
                return true
            }
        })
        map.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
        }
        map.addJavascriptInterface(WebBrideg(),"address")
        map.webViewClient = WebViewClient()
        map.webViewClient = object : WebViewClient(){
            override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
                handler!!.proceed()
            }
        }
        map.webChromeClient = object : WebChromeClient() {
            override fun onGeolocationPermissionsShowPrompt(origin: String, callback: GeolocationPermissions.Callback) {
                callback.invoke(origin, true, true)
            }
        }
        map.loadUrl("https://175.206.239.109:8443/oceancompass/map.jsp?pages=address")
    }

    inner class WebBrideg{
        @JavascriptInterface
        fun getAddress(add : String){
            handler!!.post {
                address = add
                Toast.makeText(this@ReviewLocationActivity,"선택한 위치는 $add 입니다.",Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onBackPressed() {
        finish()
    }
}
