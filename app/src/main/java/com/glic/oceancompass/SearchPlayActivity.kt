package com.glic.oceancompass

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.search_play.*

class SearchPlayActivity : AppCompatActivity(), RecycleViewClick {

    private var play: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_play)

        NukeSSLCerts().nuke()

        val request = object : StringRequest(
            Method.POST, "https://175.206.239.109:8443/oceancompass/SelectPlayServlet",
            //요청 성공 시
            Response.Listener {
                playrecycleview.adapter = RecycleViewAdapter(3, ArrayList(it.substring(1,it.length-1).split(',')) , this@SearchPlayActivity,this@SearchPlayActivity)
                playrecycleview.layoutManager = GridLayoutManager(this@SearchPlayActivity,2)
                playrecycleview.setHasFixedSize(true)
            },
            // 에러 발생 시
            Response.ErrorListener {
                Log.e("에러", "[${it.message}]")
            }) {
        }
        val queue = Volley.newRequestQueue(this)
        queue.add(request)

        playcheck.setOnClickListener {
            val intent = intent
            if(play == "") {
                intent.putExtra("play", "")
            } else {
                intent.putExtra("play", play.substring(1,play.length))
            }
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }

    override fun stateClick(value: String) {
        play = "$play,$value"
    }

    override fun cityClick(value: String) {
        play = play.replace(",$value", "")
    }

    override fun onBackPressed() {
        finish()
    }
}
