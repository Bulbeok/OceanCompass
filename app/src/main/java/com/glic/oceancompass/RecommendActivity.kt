package com.glic.oceancompass

import android.content.Context
import android.content.Intent
import android.net.http.SslError
import android.os.Bundle
import android.webkit.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.recommend.*
import kotlin.properties.Delegates

class RecommendActivity : AppCompatActivity() {

    private var count by Delegates.notNull<Int>()
    private var day by Delegates.notNull<Int>()
    private lateinit var key:String
    private lateinit var location:String
    private lateinit var play:String
    private var finalURL = ""
    private var url = ""
    private var url2 = ""
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

        count = intent.getIntExtra("count", 1)
        day = intent.getIntExtra("day",1)
        key = intent.getStringExtra("key")!!
        location = intent.getStringExtra("location")!!
        play = intent.getStringExtra("play")!!

        val pref = this.getSharedPreferences(key, Context.MODE_PRIVATE)
        val edit = pref.edit()
        var select1 = true
        var select2 = true

        day_textView.text = count.toString() +"일차"
        daycomplete.text = count.toString() +"일차"

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
            startActivityForResult(Intent(this, RecommendSelectActivity::class.java).putExtra("play",play).putExtra("location",location), 1)
        }
        type2.setOnClickListener {
            startActivityForResult(Intent(this, RecommendSelectActivity::class.java).putExtra("play",play).putExtra("location",location), 2)
        }
        type3.setOnClickListener {
            startActivityForResult(Intent(this, RecommendSelectActivity::class.java).putExtra("play",play).putExtra("location",location), 3)
        }
        type4.setOnClickListener {
            startActivityForResult(Intent(this, RecommendSelectActivity::class.java).putExtra("play",play).putExtra("location",location), 4)
        }
        type5.setOnClickListener {
            startActivityForResult(Intent(this, RecommendSelectActivity::class.java).putExtra("play",play).putExtra("location",location), 5)
        }
        type6.setOnClickListener {
            startActivityForResult(Intent(this, RecommendSelectActivity::class.java).putExtra("play",play).putExtra("location",location), 6)
        }
        type7.setOnClickListener {
            startActivityForResult(Intent(this, RecommendSelectActivity::class.java).putExtra("play",play).putExtra("location",location), 7)
        }
        type8.setOnClickListener {
            startActivityForResult(Intent(this, RecommendSelectActivity::class.java).putExtra("play",play).putExtra("location",location), 8)
        }
        type9.setOnClickListener {
            startActivityForResult(Intent(this, RecommendSelectActivity::class.java).putExtra("play",play).putExtra("location",location), 9)
        }
        type10.setOnClickListener {
            startActivityForResult(Intent(this, RecommendSelectActivity::class.java).putExtra("play",play).putExtra("location",location), 10)
        }
        type11.setOnClickListener {
            startActivityForResult(Intent(this, RecommendSelectActivity::class.java).putExtra("play",play).putExtra("location",location), 11)
        }
        type12.setOnClickListener {
            startActivityForResult(Intent(this, RecommendSelectActivity::class.java).putExtra("play",play).putExtra("location",location), 12)
        }

        option1.setOnClickListener {
            Toast.makeText(this,"첫번째를 클릭하셨습니다",Toast.LENGTH_LONG).show()
            map.loadUrl("https://175.206.239.109:8443/oceancompass/route.jsp?type=$url")
            select1 = false
            finalURL = url
            number1.setImageResource(R.drawable.number1_check)
            number2.setImageResource(R.drawable.number2)
        }

        option2.setOnClickListener {
            Toast.makeText(this,"두번째를 클릭하셨습니다",Toast.LENGTH_LONG).show()
            map.loadUrl("https://175.206.239.109:8443/oceancompass/route.jsp?type=$url2")
            select2 = false
            finalURL = url2
            number1.setImageResource(R.drawable.number1)
            number2.setImageResource(R.drawable.number2_check)
        }

        daycomplete.setOnClickListener {
            if(select1 && select2) {
                Toast.makeText(this,"경로를 선택 해주세요",Toast.LENGTH_LONG).show()
            } else {
                if(url == "" && !select1) {
                    Toast.makeText(this,"선택한 경로에 선택된 것이 없습니다",Toast.LENGTH_LONG).show()
                } else if(url2 == "" && !select2) {
                        Toast.makeText(this, "선택한 경로에 선택된 것이 없습니다", Toast.LENGTH_LONG).show()
                }else {
                    if(day == count) {
                        edit.putString("$count", finalURL)
                        edit.apply()
                        startActivity(Intent(this, RecommendResultActivity::class.java).putExtra("key",key).putExtra("count",count))
                        finish()
                    } else {
                        edit.putString("$count", finalURL)
                        edit.apply()
                        startActivity(
                            intent
                                .putExtra("key", key)
                                .putExtra("location", location)
                                .putExtra("play", play)
                                .putExtra("day", day)
                                .putExtra("count", count + 1)
                        )
                    }
                }
            }
        }

        complete.setOnClickListener {
            edit.putString("$count", finalURL)
            edit.apply()
            startActivity(Intent(this, RecommendResultActivity::class.java).putExtra("key",key).putExtra("count",count))
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val dataList = data!!.extras!!.getString("type")!!.split("/")
        if (resultCode == RESULT_OK && requestCode == 1) {
            type1.text = dataList[1]
            td1 = dataList[2] + ","
            url = td1+td2+td3+td4+td5+td6
            map.loadUrl("https://175.206.239.109:8443/oceancompass/route.jsp?type=$url")
        } else if(resultCode == RESULT_OK && requestCode == 2) {
            type2.text = dataList[1]
            td2 = dataList[2] + ","
            url = td1+td2+td3+td4+td5+td6
            map.loadUrl("https://175.206.239.109:8443/oceancompass/route.jsp?type=$url")
        }else if(resultCode == RESULT_OK && requestCode == 3) {
            type3.text = dataList[1]
            td3 = dataList[2] + ","
            url = td1+td2+td3+td4+td5+td6
            map.loadUrl("https://175.206.239.109:8443/oceancompass/route.jsp?type=$url")
        }else if(resultCode == RESULT_OK && requestCode == 4) {
            type4.text = dataList[1]
            td4 = dataList[2] + ","
            url = td1+td2+td3+td4+td5+td6
            map.loadUrl("https://175.206.239.109:8443/oceancompass/route.jsp?type=$url")
        }else if(resultCode == RESULT_OK && requestCode == 5) {
            type5.text = dataList[1]
            td5 = dataList[2] + ","
            url = td1+td2+td3+td4+td5+td6
            map.loadUrl("https://175.206.239.109:8443/oceancompass/route.jsp?type=$url")
        }else if(resultCode == RESULT_OK && requestCode == 6) {
            type6.text = dataList[1]
            td6 = dataList[2] + ","
            url = td1+td2+td3+td4+td5+td6
            map.loadUrl("https://175.206.239.109:8443/oceancompass/route.jsp?type=$url")
        }else if(resultCode == RESULT_OK && requestCode == 7) {
            type7.text = dataList[1]
            td7 = dataList[2] + ","
            url2 = td7+td8+td9+td10+td11+td12
            map.loadUrl("https://175.206.239.109:8443/oceancompass/route.jsp?type=$url2")
        }else if(resultCode == RESULT_OK && requestCode == 8) {
            type8.text = dataList[1]
            td8 = dataList[2] + ","
            url2 = td7+td8+td9+td10+td11+td12
            map.loadUrl("https://175.206.239.109:8443/oceancompass/route.jsp?type=$url2")
        }else if(resultCode == RESULT_OK && requestCode == 9) {
            type9.text = dataList[1]
            td9 = dataList[2] + ","
            url2 = td7+td8+td9+td10+td11+td12
            map.loadUrl("https://175.206.239.109:8443/oceancompass/route.jsp?type=$url2")
        }else if(resultCode == RESULT_OK && requestCode == 10) {
            type10.text = dataList[1]
            td10 = dataList[2] + ","
            url2 = td7+td8+td9+td10+td11+td12
            map.loadUrl("https://175.206.239.109:8443/oceancompass/route.jsp?type=$url2")
        }else if(resultCode == RESULT_OK && requestCode == 11) {
            type11.text = dataList[1]
            td11 = dataList[2] + ","
            url2 = td7+td8+td9+td10+td11+td12
            map.loadUrl("https://175.206.239.109:8443/oceancompass/route.jsp?type=$url2")
        }else if(resultCode == RESULT_OK && requestCode == 12) {
            type12.text = dataList[1]
            td12 = dataList[2] + ","
            url2 = td7+td8+td9+td10+td11+td12
            map.loadUrl("https://175.206.239.109:8443/oceancompass/route.jsp?type=$url2")
        }
    }

    override fun onBackPressed() {
        if(count != 1) {
            startActivity(intent
                .putExtra("key",key)
                .putExtra("location",location)
                .putExtra("play",play)
                .putExtra("day",day)
                .putExtra("count",count-1))
            finish()
        } else {
            startActivity(Intent(this, SearchActivity::class.java))
        }
    }
}
