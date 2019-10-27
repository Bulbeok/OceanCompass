package com.glic.oceancompass

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request.Method.GET
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.signup.*


class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup)

        val id = findViewById<EditText>(R.id.id).text.toString()
        val password = findViewById<EditText>(R.id.password).text.toString()

        checkid.setOnClickListener {
            if (id == "") {
                Toast.makeText(this, "아이디를 입력해주세요", Toast.LENGTH_LONG).show()
            } else {
                val request = StringRequest(
                    GET, "https://175.206.239.109:8443/oceancompass/LoginServlet",
                    Response.Listener {
                        Log.d("성공test", ">>$it")
                    },
                    Response.ErrorListener {
                        Log.e("에러test", "[${it.message}]")
                    })
                val queue = Volley.newRequestQueue(this)
                queue.add(request)
            }
        }
    }

    override fun onBackPressed() {
        startActivity(Intent(this@SignUpActivity, SignInActivity::class.java))
        finish()
    }
}
