package com.glic.oceancompass

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request.Method.POST
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class RandomRecommendActivity : AppCompatActivity() {

    private var count = 0
    private var day = 0
    private var key: String? = null
    private var location: String? = null
    private var play: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.randomrecommend)

        count = intent.getIntExtra("count",1)
        day = intent.getIntExtra("day",1)
        key = intent.getStringExtra("key")!!
        location = intent.getStringExtra("location")!!
        play = intent.getStringExtra("play")!!

        val request = object : StringRequest(
            POST, "https://175.206.239.109:8443/oceancompass/RandomRecommendServlet",
            //요청 성공 시
            Response.Listener {
                moveActivity(it)
            },
            // 에러 발생 시
            Response.ErrorListener {
                moveActivity("1")
                Log.e("에러", "[${it.message}]")
            }) {
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params["type"] = play!!
                params["address"] = location!!.split(' ')[1]
                return params
            }
        }
        val queue = Volley.newRequestQueue(this)
        request.retryPolicy = DefaultRetryPolicy(30000,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        queue.add(request)
    }

    fun moveActivity(value: String) {
        if (value.trim() == "1") {
            startActivity(
                Intent(this, RecommendActivity::class.java)
                    .putExtra("key", key)
                    .putExtra("location", location)
                    .putExtra("play", play)
                    .putExtra("day", day)
                    .putExtra("count", count)
            )
        } else {
            startActivity(
                Intent(this, RecommendActivity::class.java)
                    .putExtra("key", key)
                    .putExtra("location", location)
                    .putExtra("play", play)
                    .putExtra("day", day)
                    .putExtra("count", count)
                    .putExtra("random", value)
            )
        }
    }

    override fun onBackPressed() {
    }
}
