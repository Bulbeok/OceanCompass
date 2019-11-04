package com.glic.oceancompass

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request.Method.POST
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.reviewadd.*
import kotlin.collections.HashMap
import kotlin.collections.Map
import kotlin.collections.set


class ReviewAddActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.reviewadd)

        var categorys = ""

        send.setOnClickListener {
            Log.e("테ㅡ트",categorys)
            when {
                titles.text.toString() == "" -> Toast.makeText(this, "제목을 입력해주세요", Toast.LENGTH_LONG).show()
                explanation.text.toString() == "" -> Toast.makeText(this, "제목을 입력해주세요", Toast.LENGTH_LONG).show()
                loca.text.toString() == "" -> Toast.makeText(this, "제목을 입력해주세요", Toast.LENGTH_LONG).show()
                categorys == "" -> Toast.makeText(this, "카테고리를 선택해주세요", Toast.LENGTH_LONG).show()
                else -> {
                    val pref = this.getSharedPreferences("sessionCookie", Context.MODE_PRIVATE)
                    val sessionId = pref.getString("sessionCookie", null)
                    val request = object : StringRequest(
                        POST, "https://175.206.239.109:8443/oceancompass/AddMobilReviewServlet",
                        Response.Listener {
                            Toast.makeText(this, "작성되었습니다.", Toast.LENGTH_LONG).show()
                            startActivity(Intent(this,ReviewActivity::class.java))
                            finish()
                        },
                        Response.ErrorListener {
                            Log.e("에러", "[${it.message}]")
                        }) {
                        // request 시 key, value 보낼 때
                        override fun getParams(): Map<String, String> {
                            val params = HashMap<String, String>()
                            params["title"] = titles.text.toString()
                            params["explanation"] = explanation.text.toString()
                            params["address"] = loca.text.toString()
                            params["category"] = categorys
                            return params
                        }

                        override fun getHeaders(): Map<String, String> {
                            val headers = HashMap<String, String>()
                            headers["Cookie"] = sessionId!!
                            return headers
                        }
                    }
                    val queue = Volley.newRequestQueue(this)
                    queue.add(request)
                }
            }
        }

        category.adapter = ArrayAdapter(this, R.layout.spinner_item, resources.getStringArray(R.array.category))
        category.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                when(position) {
                    0 -> categorys = "숙박"
                    1 -> categorys = "음식점"
                    2 -> categorys = "놀거리"
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }

        loca.setOnClickListener {
            startActivityForResult(Intent(this, ReviewLocationActivity::class.java), 1)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == 1) {
            loca.text = data!!.extras!!.getString("Date")
        }
    }

    override fun onBackPressed() {
        startActivity(Intent(this, ReviewActivity::class.java))
        finish()
    }
}