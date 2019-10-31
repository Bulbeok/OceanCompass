package com.glic.oceancompass

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
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

        val test = arrayListOf<String>()

        NukeSSLCerts().nuke()

        house.setOnClickListener {
            test.clear()
            test.add("숙소")
            firstrecycleview.adapter = RecycleViewAdapter(4, test , this@RecommendSelectActivity,this@RecommendSelectActivity)
            firstrecycleview.layoutManager = LinearLayoutManager(this@RecommendSelectActivity)
            firstrecycleview.setHasFixedSize(true)
        }
        play.setOnClickListener {
            firstrecycleview.adapter = RecycleViewAdapter(4, ArrayList(intent.getStringExtra("play")!!.split(',')) , this@RecommendSelectActivity,this@RecommendSelectActivity)
            firstrecycleview.layoutManager = LinearLayoutManager(this@RecommendSelectActivity)
            firstrecycleview.setHasFixedSize(true)
        }
        food.setOnClickListener {
            test.clear()
            test.add("음식점")
            firstrecycleview.adapter = RecycleViewAdapter(4, test , this@RecommendSelectActivity,this@RecommendSelectActivity)
            firstrecycleview.layoutManager = LinearLayoutManager(this@RecommendSelectActivity)
            firstrecycleview.setHasFixedSize(true)
        }
    }

    /*fun firstClick(type : String) {

    }*/

    override fun stateClick(value: String) {
        state = value
        val request = object : StringRequest(
            POST, "https://175.206.239.109:8443/oceancompass/RecommendServlet",
            //요청 성공 시
            Response.Listener {
                Log.e(" 테스트", ArrayList(it.substring(1,it.length-1).split(',')).toString())
                seccndrecycleview.adapter = RecycleViewAdapter(5, ArrayList(it.substring(1,it.length-1).split(',')) , this@RecommendSelectActivity,this@RecommendSelectActivity)
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
        queue.add(request)
    }

    override fun cityClick(value: String) {
        setResult(Activity.RESULT_OK, intent.putExtra("type", "$state $value"))
        finish()
    }

    override fun onBackPressed() {
        finish()
    }
}
