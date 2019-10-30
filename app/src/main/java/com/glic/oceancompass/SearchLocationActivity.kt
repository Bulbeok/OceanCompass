package com.glic.oceancompass

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_location)

        NukeSSLCerts().nuke()

        val request = object : StringRequest(
            POST, "https://175.206.239.109:8443/oceancompass/LocationServlet",
            //요청 성공 시
            Response.Listener {

                val sdasdf = ArrayList(it.substring(1,it.length-1).split(','))
                createRv(sdasdf)
            },
            // 에러 발생 시
            Response.ErrorListener {
                Log.e("에러", "[${it.message}]")
            }) {
        }
        val queue = Volley.newRequestQueue(this)
        queue.add(request)

    }

    private fun createRv(history: ArrayList<String>) {
        staterecycleview.adapter = RecycleViewAdapter(1, history , this@SearchLocationActivity,this@SearchLocationActivity)
        staterecycleview.layoutManager = LinearLayoutManager(this@SearchLocationActivity)
        staterecycleview.setHasFixedSize(true)
    }

    override fun viewClick(value: String) {

    }

    override fun onBackPressed() {
        startActivity(Intent(this,SearchActivity::class.java))
        finish()
    }
}
