package com.glic.oceancompass

import android.content.Intent
import android.net.http.SslError
import android.os.Bundle
import android.util.Log
import android.webkit.*
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.recommend.*

class RecommendActivity : AppCompatActivity() {

    private var url = ""
    private var td1 = ""
    private var td2 = ""
    private var td3 = ""
    private var td4 = ""
    private var td5 = ""
    private var td6 = ""
    private var td7 = ""
    private var td8 = ""
    private var td9 = ""
    private var td10 = ""
    private var td11 = ""
    private var td12 = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recommend)

        val loca = intent.getStringExtra("location")!!

        Log.e("테스트", intent.toString())
        if (!intent.hasExtra("count")) {
            day.text = "1일차"
            complete.text = "1일차 완성"
        } else {
            day.text = intent.getStringExtra("count")!! + "일차"
            complete.text = intent.getStringExtra("count")!! + "일차"
        }
        if(loca.split(" ")[0] == loca.split(" ")[1]) {
            location.text = loca.split(" ")[0]
        } else {
            location.text = loca
        }

        map.settings.javaScriptEnabled = true
        map.settings.domStorageEnabled = true
        //map.loadUrl("https://175.206.239.109:8443/oceancompass/mobilemap.jsp")
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

        type1.setOnClickListener {
            startActivityForResult(Intent(this, RecommendSelectActivity::class.java).putExtra("play",intent.getStringExtra("play")).putExtra("location",loca), 1)
        }
        type2.setOnClickListener {
            startActivityForResult(Intent(this, RecommendSelectActivity::class.java).putExtra("play",intent.getStringExtra("play")).putExtra("location",loca), 2)
        }
        type3.setOnClickListener {
            startActivityForResult(Intent(this, RecommendSelectActivity::class.java).putExtra("play",intent.getStringExtra("play")).putExtra("location",loca), 3)
        }
        type4.setOnClickListener {
            startActivityForResult(Intent(this, RecommendSelectActivity::class.java).putExtra("play",intent.getStringExtra("play")).putExtra("location",loca), 4)
        }
        type5.setOnClickListener {
            startActivityForResult(Intent(this, RecommendSelectActivity::class.java).putExtra("play",intent.getStringExtra("play")).putExtra("location",loca), 5)
        }
        type6.setOnClickListener {
            startActivityForResult(Intent(this, RecommendSelectActivity::class.java).putExtra("play",intent.getStringExtra("play")).putExtra("location",loca), 6)
        }
        type7.setOnClickListener {
            startActivityForResult(Intent(this, RecommendSelectActivity::class.java).putExtra("play",intent.getStringExtra("play")).putExtra("location",loca), 7)
        }
        type8.setOnClickListener {
            startActivityForResult(Intent(this, RecommendSelectActivity::class.java).putExtra("play",intent.getStringExtra("play")).putExtra("location",loca), 8)
        }
        type9.setOnClickListener {
            startActivityForResult(Intent(this, RecommendSelectActivity::class.java).putExtra("play",intent.getStringExtra("play")).putExtra("location",loca), 9)
        }
        type10.setOnClickListener {
            startActivityForResult(Intent(this, RecommendSelectActivity::class.java).putExtra("play",intent.getStringExtra("play")).putExtra("location",loca), 10)
        }
        type11.setOnClickListener {
            startActivityForResult(Intent(this, RecommendSelectActivity::class.java).putExtra("play",intent.getStringExtra("play")).putExtra("location",loca), 11)
        }
        type12.setOnClickListener {
            startActivityForResult(Intent(this, RecommendSelectActivity::class.java).putExtra("play",intent.getStringExtra("play")).putExtra("location",loca), 12)
        }

        complete.setOnClickListener {

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == 1) {
            type1.text = data!!.extras!!.getString("type")!!.split("/")[1]
            td1 = data.extras!!.getString("type")!!.split("/")[2] + ","
            url = td1+td2+td3+td4+td5+td6+td7+td8+td9+td10+td11+td12
            map.loadUrl("https://175.206.239.109:8443/oceancompass/route.jsp?type=$url")
        } else if(resultCode == RESULT_OK && requestCode == 2) {
            type2.text = data!!.extras!!.getString("type")!!.split("/")[1]
            td2 = data.extras!!.getString("type")!!.split("/")[2] + ","
            url = td1+td2+td3+td4+td5+td6+td7+td8+td9+td10+td11+td12
            map.loadUrl("https://175.206.239.109:8443/oceancompass/route.jsp?type=$url")
        }else if(resultCode == RESULT_OK && requestCode == 3) {
            type3.text = data!!.extras!!.getString("type")!!.split("/")[1]
            td3 = data.extras!!.getString("type")!!.split("/")[2] + ","
            url = td1+td2+td3+td4+td5+td6+td7+td8+td9+td10+td11+td12
            map.loadUrl("https://175.206.239.109:8443/oceancompass/route.jsp?type=$url")
        }else if(resultCode == RESULT_OK && requestCode == 4) {
            type4.text = data!!.extras!!.getString("type")!!.split("/")[1]
            td4 = data.extras!!.getString("type")!!.split("/")[2] + ","
            url = td1+td2+td3+td4+td5+td6+td7+td8+td9+td10+td11+td12
            map.loadUrl("https://175.206.239.109:8443/oceancompass/route.jsp?type=$url")
        }else if(resultCode == RESULT_OK && requestCode == 5) {
            type5.text = data!!.extras!!.getString("type")!!.split("/")[1]
            td5 = data.extras!!.getString("type")!!.split("/")[2] + ","
            url = td1+td2+td3+td4+td5+td6+td7+td8+td9+td10+td11+td12
            map.loadUrl("https://175.206.239.109:8443/oceancompass/route.jsp?type=$url")
        }else if(resultCode == RESULT_OK && requestCode == 6) {
            type6.text = data!!.extras!!.getString("type")!!.split("/")[1]
            td6 = data.extras!!.getString("type")!!.split("/")[2] + ","
            url = td1+td2+td3+td4+td5+td6+td7+td8+td9+td10+td11+td12
            map.loadUrl("https://175.206.239.109:8443/oceancompass/route.jsp?type=$url")
        }else if(resultCode == RESULT_OK && requestCode == 7) {
            type7.text = data!!.extras!!.getString("type")!!.split("/")[1]
            td7 = data.extras!!.getString("type")!!.split("/")[2] + ","
            url = td1+td2+td3+td4+td5+td6+td7+td8+td9+td10+td11+td12
            map.loadUrl("https://175.206.239.109:8443/oceancompass/route.jsp?type=$url")
        }else if(resultCode == RESULT_OK && requestCode == 8) {
            type8.text = data!!.extras!!.getString("type")!!.split("/")[1]
            td8 = data.extras!!.getString("type")!!.split("/")[2] + ","
            url = td1+td2+td3+td4+td5+td6+td7+td8+td9+td10+td11+td12
            map.loadUrl("https://175.206.239.109:8443/oceancompass/route.jsp?type=$url")
        }else if(resultCode == RESULT_OK && requestCode == 9) {
            type9.text = data!!.extras!!.getString("type")!!.split("/")[1]
            td9 = data.extras!!.getString("type")!!.split("/")[2] + ","
            url = td1+td2+td3+td4+td5+td6+td7+td8+td9+td10+td11+td12
            map.loadUrl("https://175.206.239.109:8443/oceancompass/route.jsp?type=$url")
        }else if(resultCode == RESULT_OK && requestCode == 10) {
            type10.text = data!!.extras!!.getString("type")!!.split("/")[1]
            td10 = data.extras!!.getString("type")!!.split("/")[2] + ","
            url = td1+td2+td3+td4+td5+td6+td7+td8+td9+td10+td11+td12
            map.loadUrl("https://175.206.239.109:8443/oceancompass/route.jsp?type=$url")
        }else if(resultCode == RESULT_OK && requestCode == 11) {
            type11.text = data!!.extras!!.getString("type")!!.split("/")[1]
            td11 = data.extras!!.getString("type")!!.split("/")[2] + ","
            url = td1+td2+td3+td4+td5+td6+td7+td8+td9+td10+td11+td12
            map.loadUrl("https://175.206.239.109:8443/oceancompass/route.jsp?type=$url")
        }else if(resultCode == RESULT_OK && requestCode == 12) {
            type12.text = data!!.extras!!.getString("type")!!.split("/")[1]
            td12 = data.extras!!.getString("type")!!.split("/")[2] + ","
            url = td1+td2+td3+td4+td5+td6+td7+td8+td9+td10+td11+td12
            map.loadUrl("https://175.206.239.109:8443/oceancompass/route.jsp?type=$url")
        }
    }

    override fun onBackPressed() {
        startActivity(Intent(this,SearchActivity::class.java))
        finish()
    }
}
