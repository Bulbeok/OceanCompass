package com.glic.oceancompass

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request.Method.POST
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.recommendselect.*

class RecommendSelectActivity : AppCompatActivity(), RecycleViewClick {

    private lateinit var state: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recommendselect)

        val recommendList = arrayListOf<String>()

        NukeSSLCerts().nuke()

        house.setOnClickListener {
            recommendList.clear()
            recommendList.add("숙박업소")
            recommendList.add("캠핑장")
            recommendList.add("휴양림")
            recommendList.add("사용자추천,숙박")
            firstrecycleview.adapter = RecycleViewAdapter(5, recommendList , this@RecommendSelectActivity,this@RecommendSelectActivity)
            firstrecycleview.layoutManager = LinearLayoutManager(this@RecommendSelectActivity)
            firstrecycleview.setHasFixedSize(true)
        }
        play.setOnClickListener {
            val playList = ArrayList(intent.getStringExtra("play")!!.split(','))
            playList.add("사용자추천,놀거리")
            firstrecycleview.adapter = RecycleViewAdapter(4, playList, this@RecommendSelectActivity,this@RecommendSelectActivity)
            firstrecycleview.layoutManager = LinearLayoutManager(this@RecommendSelectActivity)
            firstrecycleview.setHasFixedSize(true)
        }
        food.setOnClickListener {
            recommendList.clear()
            recommendList.add("음식점")
            recommendList.add("사용자추천,음식점")
            firstrecycleview.adapter = RecycleViewAdapter(5, recommendList , this@RecommendSelectActivity,this@RecommendSelectActivity)
            firstrecycleview.layoutManager = LinearLayoutManager(this@RecommendSelectActivity)
            firstrecycleview.setHasFixedSize(true)
        }
    }

    override fun stateClick(value: String) {
        state = value
        val request = object : StringRequest(
            POST, "https://175.206.239.109:8443/oceancompass/RecommendServlet",
            //요청 성공 시
            Response.Listener {
                seccndrecycleview.adapter = RecycleViewAdapter(6, ArrayList(it.substring(1,it.length-1).split(',')) , this@RecommendSelectActivity,this@RecommendSelectActivity)
                seccndrecycleview.layoutManager = LinearLayoutManager(this@RecommendSelectActivity)
                seccndrecycleview.setHasFixedSize(true)
            },
            // 에러 발생 시
            Response.ErrorListener {
                Log.e("에러", "[${it.message}]")
            }) {
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params["type"] = value
                params["address"] = intent.getStringExtra("location")!!.split(' ')[1]
                return params
            }
        }
        val queue = Volley.newRequestQueue(this)
        request.retryPolicy = DefaultRetryPolicy(30000,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        queue.add(request)
    }

    override fun cityClick(value: String) {
        setResult(Activity.RESULT_OK, intent.putExtra("type", "$state/$value"))
        finish()
    }

    override fun onBackPressed() {
        finish()
    }
}
