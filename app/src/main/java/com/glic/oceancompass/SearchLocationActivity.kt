package com.glic.oceancompass

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request.Method.POST
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.search_location.*

class SearchLocationActivity : AppCompatActivity(), RecycleViewClick {

    private lateinit var state: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_location)

        NukeSSLCerts().nuke()

        val request = object : StringRequest(
            POST, "https://175.206.239.109:8443/oceancompass/LocationServlet",
            //요청 성공 시
            Response.Listener {
                staterecycleview.adapter = RecycleViewAdapter(1, ArrayList(it.substring(1,it.length-1).split(',')) , this@SearchLocationActivity,this@SearchLocationActivity)
                staterecycleview.layoutManager = LinearLayoutManager(this@SearchLocationActivity)
                staterecycleview.setHasFixedSize(true)
            },
            // 에러 발생 시
            Response.ErrorListener {
                Log.e("에러", "[${it.message}]")
            }) {
        }
        val queue = Volley.newRequestQueue(this)
        queue.add(request)

    }

    override fun stateClick(value: String) {
        state = value
        val request = object : StringRequest(
            POST, "https://175.206.239.109:8443/oceancompass/LocationServlet",
            //요청 성공 시
            Response.Listener {
                cityrecycleview.adapter = RecycleViewAdapter(2, ArrayList(it.substring(1,it.length-1).split(',')) , this@SearchLocationActivity,this@SearchLocationActivity)
                cityrecycleview.layoutManager = LinearLayoutManager(this@SearchLocationActivity)
                cityrecycleview.setHasFixedSize(true)
            },
            // 에러 발생 시
            Response.ErrorListener {
                Log.e("에러", "[${it.message}]")
            }) {
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params["city"] = value
                return params
            }
        }
        val queue = Volley.newRequestQueue(this)
        queue.add(request)
    }

    override fun cityClick(value: String) {
        val intent = intent
        if(state == value) {
            intent.putExtra("city", value)
        } else {
            intent.putExtra("city", "$state $value")
        }
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    override fun onBackPressed() {
        startActivity(Intent(this,SearchActivity::class.java))
        finish()
    }
}
