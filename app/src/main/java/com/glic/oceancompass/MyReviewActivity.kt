package com.glic.oceancompass

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request.Method.POST
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.myreview.*

class MyReviewActivity : AppCompatActivity(), RecycleViewClick {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.myreview)

        val pref = this.getSharedPreferences("sessionCookie", Context.MODE_PRIVATE)
        val sessionId = pref.getString("sessionId", null)

        val request = object : StringRequest(
            POST, "https://175.206.239.109:8443/oceancompass/MobileMyPageServlet",
            //요청 성공 시
            Response.Listener {
                review.adapter = RecycleViewAdapter(8, ArrayList(it.substring(1,it.length-1).split(',')) , this@MyReviewActivity,this@MyReviewActivity)
                review.layoutManager = LinearLayoutManager(this@MyReviewActivity)
                review.setHasFixedSize(true)
            },
            // 에러 발생 시
            Response.ErrorListener {
                Log.e("에러", "[${it.message}]")
            }) {
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params["id"] = sessionId!!
                return params
            }
        }
        val queue = Volley.newRequestQueue(this)
        queue.add(request)
    }

    override fun stateClick(value: String) {
        val alert = AlertDialog.Builder(this@MyReviewActivity)
        alert.setMessage("리뷰를 수정 하시겠습니까?")
        alert.setPositiveButton("확인") { _, _ ->
            startActivity(Intent(this, ReviewAddActivity::class.java).putExtra("id",value))
            finish()
        }
        alert.setNegativeButton("취소") { _, _ ->
        }
        val dialog: AlertDialog = alert.create()
        dialog.show()
    }

    override fun cityClick(value: String) {
    }

    override fun onBackPressed() {
        startActivity(Intent(this, MyPageActivity::class.java))
        finish()
    }
}
