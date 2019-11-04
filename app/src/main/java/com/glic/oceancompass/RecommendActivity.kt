package com.glic.oceancompass

import android.content.Context
import android.content.Intent
import android.net.http.SslError
import android.os.Bundle
import android.view.View
import android.webkit.*
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.recommend.*
import kotlin.properties.Delegates


class RecommendActivity : AppCompatActivity(), View.OnClickListener {

    private var historyCount by Delegates.notNull<Int>()
    private var count by Delegates.notNull<Int>()
    private var day by Delegates.notNull<Int>()
    private lateinit var key:String
    private lateinit var location:String
    private lateinit var play:String

    private val selectList = Array(12) {""}
    private val urlList = arrayOf("","","")
    private val textViewList = arrayListOf<TextView>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recommend)

        count = intent.getIntExtra("count", 1)
        day = intent.getIntExtra("day",1)
        key = intent.getStringExtra("key")!!
        location = intent.getStringExtra("location")!!
        play = intent.getStringExtra("play")!!
        historyCount = intent.getIntExtra("historyCount", 1)

        val pref = this.getSharedPreferences(key, Context.MODE_PRIVATE)
        val historyPref = this.getSharedPreferences("history", Context.MODE_PRIVATE)
        val edit = pref.edit()
        val historyEdit = historyPref.edit()
        var select1 = true
        var select2 = true

        for(i in 1..12) {
            textViewList.add(findViewById(resources.getIdentifier("type$i","id",packageName)))
            textViewList[i-1].setOnClickListener(this)
        }

        if(intent.hasExtra("random")) {
            val randomList = intent.getStringExtra("random")!!.substring(1,intent.getStringExtra("random")!!.length-1).split(",")
            for (i in 1..randomList.size) {
                if(randomList[i-1] == "" || randomList[i-1] == " " ) {
                    continue
                }
                selectList[i-1] = randomList[i - 1].split("/")[1]
                if(i<=6) {
                    urlList[0] = urlList[0] + randomList[i - 1].split("/")[1] + ","
                } else {
                    urlList[1] = urlList[1] + randomList[i - 1].split("/")[1] + ","
                }
                findViewById<TextView>(resources.getIdentifier("type$i", "id", packageName)).text = randomList[i-1].split("/")[0]
            }
        }

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
/*        map.setBackgroundColor(0)
        map.setBackgroundResource(R.drawable.splash)*/
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

        option1.setOnClickListener {
            Toast.makeText(this,"첫번째 경로를 클릭하셨습니다",Toast.LENGTH_LONG).show()
            map.loadUrl("https://175.206.239.109:8443/oceancompass/route.jsp?type=" + urlList[0])
            select1 = false
            urlList[2] = urlList[0]
            number1.setImageResource(R.drawable.number1_check)
            number2.setImageResource(R.drawable.number2)
        }

        option2.setOnClickListener {
            Toast.makeText(this,"두번째 경로를 클릭하셨습니다",Toast.LENGTH_LONG).show()
            map.loadUrl("https://175.206.239.109:8443/oceancompass/route.jsp?type=" + urlList[1])
            select2 = false
            urlList[2] = urlList[0]
            number1.setImageResource(R.drawable.number1)
            number2.setImageResource(R.drawable.number2_check)
        }

        daycomplete.setOnClickListener {
            if(select1 && select2) {
                Toast.makeText(this,"경로를 선택 해주세요",Toast.LENGTH_LONG).show()
            } else {
                if(urlList[0] == "" && !select1) {
                    Toast.makeText(this,"선택한 경로에 선택된 것이 없습니다",Toast.LENGTH_LONG).show()
                } else if(urlList[1] == "" && !select2) {
                        Toast.makeText(this, "선택한 경로에 선택된 것이 없습니다", Toast.LENGTH_LONG).show()
                }else {
                    if(day == count) {
                        historyEdit.putInt("$historyCount" +"count",count)
                        historyEdit.apply()
                        edit.putString("$count", urlList[2])
                        edit.apply()
                        startActivity(Intent(this, RecommendResultActivity::class.java).putExtra("key",key).putExtra("count",count))
                        finish()
                    } else {
                        edit.putString("$count", urlList[2])
                        edit.apply()
                        startActivity(Intent(this, RandomRecommendActivity::class.java)
                                .putExtra("key", key)
                                .putExtra("location", location)
                                .putExtra("play", play)
                                .putExtra("day", day)
                                .putExtra("count", count + 1)
                                .putExtra("historyCount",historyCount)
                        )
                    }
                }
            }
        }

        complete.setOnClickListener {
            historyEdit.putInt("$historyCount" +"count",count)
            historyEdit.apply()
            edit.putString("$count", urlList[2])
            edit.apply()
            startActivity(Intent(this, RecommendResultActivity::class.java).putExtra("key",key).putExtra("count",count))
            finish()
        }
    }

    override fun onClick(view: View?) {
        startActivityForResult(Intent(this, RecommendSelectActivity::class.java)
            .putExtra("play",play)
            .putExtra("location",location)
            , Integer.parseInt(view!!.resources.getResourceName(view.id)
                .substring(29,view.resources.getResourceName(view.id).length)))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        var dataList:List<String> = emptyList()

        if(resultCode == RESULT_OK) {
            dataList = data!!.extras!!.getString("type")!!.split("/")
        }
        if (resultCode == RESULT_OK && requestCode <= 6) {
            findViewById<TextView>(resources.getIdentifier("type$requestCode", "id", packageName)).text = dataList[1]
            selectList[requestCode] = dataList[2] + ","
            for(i in 0 until 6) urlList[0] + selectList[i]
            map.loadUrl("https://175.206.239.109:8443/oceancompass/route.jsp?type=" + urlList[0])
        } else if(resultCode == RESULT_OK && requestCode > 6) {
            findViewById<TextView>(resources.getIdentifier("type$requestCode", "id", packageName)).text = dataList[1]
            selectList[requestCode] = dataList[2] + ","
            for(i in 6 until 12) urlList[0] + selectList[i]
            map.loadUrl("https://175.206.239.109:8443/oceancompass/route.jsp?type=" + urlList[1])
        }
    }

    override fun onBackPressed() {
        if(count != 1) {
            startActivity(intent
                .putExtra("key",key)
                .putExtra("location",location)
                .putExtra("play",play)
                .putExtra("day",day)
                .putExtra("count",count-1)
                .putExtra("historyCount",historyCount))
            finish()
        } else {
            val pref = this.getSharedPreferences(key, Context.MODE_PRIVATE)
            val edit = pref.edit()
            val pref2 = this.getSharedPreferences("history", Context.MODE_PRIVATE)
            val edit2 = pref2.edit()
            edit.clear()
            edit.apply()
            edit2.putInt("count", pref2.getInt("count", 1)-1)
            edit2.apply()
            startActivity(Intent(this, SearchActivity::class.java))
        }
    }
}
