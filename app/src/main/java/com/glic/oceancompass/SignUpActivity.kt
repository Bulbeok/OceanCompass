package com.glic.oceancompass

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request.Method.GET
import com.android.volley.Request.Method.POST
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.signup.*


class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup)

        NukeSSLCerts().nuke()

        val regExpId = Regex("^[a-zA-Z0-9]*$")
        val reqExpName = Regex("^[가-힣]*$")
        val regExpMail = Regex("""^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$""")

        var checkUserId = true

        id.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun afterTextChanged(p0: Editable?) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                checkUserId =  true
            }
        })

        checkId.setOnClickListener {
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(this.currentFocus?.windowToken, 0)

            if (id.text.toString() == "" ) {
                Toast.makeText(this, "아이디를 입력해주세요", Toast.LENGTH_LONG).show()
            } else if(!id.text.toString().matches(regExpId) || id.text.toString().length !in 6..16) {
                Toast.makeText(this, "아이디 형식에 맞게 입력해주세요", Toast.LENGTH_LONG).show()
            } else {
                val request = StringRequest(
                    GET, "https://175.206.239.109:8443/oceancompass/IdCheckForm.jsp?id="+id.text.toString(),
                    Response.Listener {
                        if(it.trim() == "true") {
                            Toast.makeText(this, "사용 불가능한 아이디 입니다", Toast.LENGTH_LONG).show()
                        } else {
                            Toast.makeText(this, "사용 가능한 아이디 입니다", Toast.LENGTH_LONG).show()
                            checkUserId = false
                        }
                    },
                    Response.ErrorListener {
                        Log.e("에러", "[${it.message}]")
                    })
                val queue = Volley.newRequestQueue(this)
                queue.add(request)
            }
        }

        signUp.setOnClickListener {
            if(checkUserId) {
                Toast.makeText(this, "아이디 중복확인 해주세요.", Toast.LENGTH_LONG).show()
            } else if(password.text.toString() == "" || password.text.toString().length !in 6..16) {
                Toast.makeText(this, "비밀번호 형식에 맞게 입력해주세요.", Toast.LENGTH_LONG).show()
            } else if(re_password.text.toString() == "" || re_password.text.toString().length !in 6..16) {
                Toast.makeText(this, "비밀번2호 형식에 맞게 입력해주세요.", Toast.LENGTH_LONG).show()
            } else if(re_password.text.toString() !=  password.text.toString()) {
                Toast.makeText(this, "비밀번호 일치하지 않습니다.", Toast.LENGTH_LONG).show()
            } else if(name.text.toString() == "" || !name.text.toString().matches(reqExpName)) {
                Toast.makeText(this, "이름 형식에 맞게 입력해주세요.", Toast.LENGTH_LONG).show()
            } else if(email.text.toString() == "" || !email.text.toString().matches(regExpMail)) {
                Toast.makeText(this, "이메일 형식에 맞게 입력해주세요.", Toast.LENGTH_LONG).show()
            } else {
                val request = object : StringRequest(
                    POST, "https://175.206.239.109:8443/oceancompass/AddUserServlet",
                    //요청 성공 시
                    Response.Listener {
                        if (it != "1") {
                            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
                        } else {
                            val builder = AlertDialog.Builder(this@SignUpActivity)
                            builder.setTitle("회원가입 성공")
                            builder.setMessage("이메일을 보냈습니다. 이메일 인증 확인을 해야 모든 기능을 사용 할 수 있습니다.")
                            builder.setPositiveButton("확인") { _, _ ->
                                startActivity(Intent(this@SignUpActivity, SignInActivity::class.java))
                                finish()
                            }
                            val dialog:AlertDialog = builder.create()
                            dialog.show()
                        }
                    },
                    // 에러 발생 시
                    Response.ErrorListener {
                        Log.e("에러", "[${it.message}]")
                    }) {

                    // request 시 key, value 보낼 때
                    override fun getParams(): Map<String, String> {
                        val params = HashMap<String, String>()
                        params["id"] = id.text.toString()
                        params["password"] = password.text.toString()
                        params["name"] = name.text.toString()
                        params["mail"] = email.text.toString()
                        return params
                    }
                }
                val queue = Volley.newRequestQueue(this)
                request.retryPolicy = DefaultRetryPolicy(30000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
                queue.add(request)
            }
        }
    }

    override fun onBackPressed() {
        startActivity(Intent(this@SignUpActivity, SignInActivity::class.java))
        finish()
    }

}
