package com.glic.oceancompass

import android.content.Context
import android.content.Intent
import android.net.http.SslError
import android.os.Bundle
import android.webkit.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.recommend.*

class RecommendActivity : AppCompatActivity() {

    private var url = ""
    private var url2 = ""
    private lateinit var finalurl:String
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

        val key = intent.getStringExtra("key")!!
        val location = intent.getStringExtra("location")!!
        val day = intent.getIntExtra("count", 0)
        val pref = this.getSharedPreferences(key, Context.MODE_PRIVATE)
        val edit = pref.edit()

        day_textView.text = "$day 일차"
        daycomplete.text = "$day 일차"
        if(location.split(" ")[0] == location.split(" ")[1]) {
            location_textView.text = location.split(" ")[0]
        } else {
            location_textView.text = location
        }

        map.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
        }
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
            startActivityForResult(Intent(this, RecommendSelectActivity::class.java).putExtra("play",intent.getStringExtra("play")).putExtra("location",location), 1)
        }
        type2.setOnClickListener {
            startActivityForResult(Intent(this, RecommendSelectActivity::class.java).putExtra("play",intent.getStringExtra("play")).putExtra("location",location), 2)
        }
        type3.setOnClickListener {
            startActivityForResult(Intent(this, RecommendSelectActivity::class.java).putExtra("play",intent.getStringExtra("play")).putExtra("location",location), 3)
        }
        type4.setOnClickListener {
            startActivityForResult(Intent(this, RecommendSelectActivity::class.java).putExtra("play",intent.getStringExtra("play")).putExtra("location",location), 4)
        }
        type5.setOnClickListener {
            startActivityForResult(Intent(this, RecommendSelectActivity::class.java).putExtra("play",intent.getStringExtra("play")).putExtra("location",location), 5)
        }
        type6.setOnClickListener {
            startActivityForResult(Intent(this, RecommendSelectActivity::class.java).putExtra("play",intent.getStringExtra("play")).putExtra("location",location), 6)
        }
        type7.setOnClickListener {
            startActivityForResult(Intent(this, RecommendSelectActivity::class.java).putExtra("play",intent.getStringExtra("play")).putExtra("location",location), 7)
        }
        type8.setOnClickListener {
            startActivityForResult(Intent(this, RecommendSelectActivity::class.java).putExtra("play",intent.getStringExtra("play")).putExtra("location",location), 8)
        }
        type9.setOnClickListener {
            startActivityForResult(Intent(this, RecommendSelectActivity::class.java).putExtra("play",intent.getStringExtra("play")).putExtra("location",location), 9)
        }
        type10.setOnClickListener {
            startActivityForResult(Intent(this, RecommendSelectActivity::class.java).putExtra("play",intent.getStringExtra("play")).putExtra("location",location), 10)
        }
        type11.setOnClickListener {
            startActivityForResult(Intent(this, RecommendSelectActivity::class.java).putExtra("play",intent.getStringExtra("play")).putExtra("location",location), 11)
        }
        type12.setOnClickListener {
            startActivityForResult(Intent(this, RecommendSelectActivity::class.java).putExtra("play",intent.getStringExtra("play")).putExtra("location",location), 12)
        }

        option1.setOnClickListener {
            Toast.makeText(this,"첫번째를 클릭하셨습니다",Toast.LENGTH_LONG).show()
            map.loadUrl("https://175.206.239.109:8443/oceancompass/route.jsp?type=$url")
            number1.setImageResource(R.drawable.number1_check)
            number2.setImageResource(R.drawable.number2)
            finalurl = url
        }

        option2.setOnClickListener {
            Toast.makeText(this,"두번째를 클릭하셨습니다",Toast.LENGTH_LONG).show()
            map.loadUrl("https://175.206.239.109:8443/oceancompass/route.jsp?type=$url2")
            number1.setImageResource(R.drawable.number1)
            number2.setImageResource(R.drawable.number2_check)
            finalurl = url2
        }
        daycomplete.setOnClickListener {
            edit.putString("$day", finalurl)
            edit.apply()
        }
        complete.setOnClickListener {
            edit.putString("$day", finalurl)
            edit.apply()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == 1) {
            type1.text = data!!.extras!!.getString("type")!!.split("/")[1]
            td1 = data.extras!!.getString("type")!!.split("/")[2] + ","
            url = td1+td2+td3+td4+td5+td6
            map.loadUrl("https://175.206.239.109:8443/oceancompass/route.jsp?type=$url")
        } else if(resultCode == RESULT_OK && requestCode == 2) {
            type2.text = data!!.extras!!.getString("type")!!.split("/")[1]
            td2 = data.extras!!.getString("type")!!.split("/")[2] + ","
            url = td1+td2+td3+td4+td5+td6
            map.loadUrl("https://175.206.239.109:8443/oceancompass/route.jsp?type=$url")
        }else if(resultCode == RESULT_OK && requestCode == 3) {
            type3.text = data!!.extras!!.getString("type")!!.split("/")[1]
            td3 = data.extras!!.getString("type")!!.split("/")[2] + ","
            url = td1+td2+td3+td4+td5+td6
            map.loadUrl("https://175.206.239.109:8443/oceancompass/route.jsp?type=$url")
        }else if(resultCode == RESULT_OK && requestCode == 4) {
            type4.text = data!!.extras!!.getString("type")!!.split("/")[1]
            td4 = data.extras!!.getString("type")!!.split("/")[2] + ","
            url = td1+td2+td3+td4+td5+td6
            map.loadUrl("https://175.206.239.109:8443/oceancompass/route.jsp?type=$url")
        }else if(resultCode == RESULT_OK && requestCode == 5) {
            type5.text = data!!.extras!!.getString("type")!!.split("/")[1]
            td5 = data.extras!!.getString("type")!!.split("/")[2] + ","
            url = td1+td2+td3+td4+td5+td6
            map.loadUrl("https://175.206.239.109:8443/oceancompass/route.jsp?type=$url")
        }else if(resultCode == RESULT_OK && requestCode == 6) {
            type6.text = data!!.extras!!.getString("type")!!.split("/")[1]
            td6 = data.extras!!.getString("type")!!.split("/")[2] + ","
            url = td1+td2+td3+td4+td5+td6
            map.loadUrl("https://175.206.239.109:8443/oceancompass/route.jsp?type=$url")
        }else if(resultCode == RESULT_OK && requestCode == 7) {
            type7.text = data!!.extras!!.getString("type")!!.split("/")[1]
            td7 = data.extras!!.getString("type")!!.split("/")[2] + ","
            url2 = td7+td8+td9+td10+td11+td12
            map.loadUrl("https://175.206.239.109:8443/oceancompass/route.jsp?type=$url2")
        }else if(resultCode == RESULT_OK && requestCode == 8) {
            type8.text = data!!.extras!!.getString("type")!!.split("/")[1]
            td8 = data.extras!!.getString("type")!!.split("/")[2] + ","
            url2 = td7+td8+td9+td10+td11+td12
            map.loadUrl("https://175.206.239.109:8443/oceancompass/route.jsp?type=$url2")
        }else if(resultCode == RESULT_OK && requestCode == 9) {
            type9.text = data!!.extras!!.getString("type")!!.split("/")[1]
            td9 = data.extras!!.getString("type")!!.split("/")[2] + ","
            url2 = td7+td8+td9+td10+td11+td12
            map.loadUrl("https://175.206.239.109:8443/oceancompass/route.jsp?type=$url2")
        }else if(resultCode == RESULT_OK && requestCode == 10) {
            type10.text = data!!.extras!!.getString("type")!!.split("/")[1]
            td10 = data.extras!!.getString("type")!!.split("/")[2] + ","
            url2 = td7+td8+td9+td10+td11+td12
            map.loadUrl("https://175.206.239.109:8443/oceancompass/route.jsp?type=$url2")
        }else if(resultCode == RESULT_OK && requestCode == 11) {
            type11.text = data!!.extras!!.getString("type")!!.split("/")[1]
            td11 = data.extras!!.getString("type")!!.split("/")[2] + ","
            url2 = td7+td8+td9+td10+td11+td12
            map.loadUrl("https://175.206.239.109:8443/oceancompass/route.jsp?type=$url2")
        }else if(resultCode == RESULT_OK && requestCode == 12) {
            type12.text = data!!.extras!!.getString("type")!!.split("/")[1]
            td12 = data.extras!!.getString("type")!!.split("/")[2] + ","
            url2 = td7+td8+td9+td10+td11+td12
            map.loadUrl("https://175.206.239.109:8443/oceancompass/route.jsp?type=$url2")
        }
    }

    override fun onBackPressed() {
        startActivity(Intent(this,SearchActivity::class.java))
        finish()
    }
}
