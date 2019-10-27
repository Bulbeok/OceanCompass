package com.glic.oceancompass

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.NetworkResponse
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.signin.*
import java.util.*
import kotlin.collections.HashMap


class SignInActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signin)

        val pref = this.getSharedPreferences("sessionCookie", Context.MODE_PRIVATE)
        val edit = pref.edit()

        val bottomNavigationView  = findViewById<View>(R.id.main_bottom_navigation_view) as BottomNavigationView
        bottomNavigationView.menu.findItem(R.id.mypage).isChecked = true
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    startActivity(Intent(this,MainActivity::class.java))
                    finish()
                    overridePendingTransition(0, 0)
                }
                R.id.search -> {
                    startActivity(Intent(this,SearchActivity::class.java))
                    finish()
                    overridePendingTransition(0, 0)
                }
                R.id.share -> {
                    startActivity(Intent(this,ReviewActivity::class.java))
                    finish()
                    overridePendingTransition(0, 0)
                }
                R.id.mypage -> {
                }
            }
            true
        }

        NukeSSLCerts.nuke()

        login.setOnClickListener {
            val id = findViewById<EditText>(R.id.id).text.toString()
            val password = findViewById<EditText>(R.id.password).text.toString()

            if(login.text == "로그인") {
                if(id == "" || password == "") {
                    Toast.makeText(this, "아이디와 비밀번호를 입력해주세요", Toast.LENGTH_LONG).show()
                } else {
                    val request = object : StringRequest(
                        Method.POST, "https://175.206.239.109:8443/oceancompass/LoginServlet",
                        //요청 성공 시
                        Response.Listener {
                            if (it.toInt() != 1) {
                                Toast.makeText(this, "아이디와 비밀번호를 확인해주세요", Toast.LENGTH_LONG).show()
                            } else {
                                Toast.makeText(this, "로그인 성공", Toast.LENGTH_LONG).show()
                                findViewById<EditText>(R.id.id).isEnabled = false
                                findViewById<EditText>(R.id.password).isEnabled = false
                                login.text = "로그아웃"
                            }
                        },
                        // 에러 발생 시
                        Response.ErrorListener {
                            Log.e("에러", "[${it.message}]")
                        }) {
                        // request 시 key, value 보낼 때
                        override fun getParams(): Map<String, String> {
                            val params = HashMap<String, String>()
                            params["id"] = id
                            params["password"] = password
                            return params
                        }
                        override fun parseNetworkResponse(response: NetworkResponse?): Response<String> {
                            val cookiesInfo: TreeMap<String, String> = response?.headers as TreeMap<String, String>
                            edit.putString("sessionid", cookiesInfo["Set-Cookie"])
                            edit.apply()
                            return super.parseNetworkResponse(response)
                        }
                    }
                    val queue = Volley.newRequestQueue(this)
                    queue.add(request)
                }
            } else if(login.text == "로그아웃") {

            }
        }
        signup.setOnClickListener {
            startActivity(Intent(this,SignUpActivity::class.java))
            finish()
        }
    }

    override fun onBackPressed() {
        startActivity(Intent(this@SignInActivity, MainActivity::class.java))
        finish()
        overridePendingTransition(0, 0)
    }
/*

    var hurlStack: HurlStack = object : HurlStack() {
        override fun createConnection(url: java.net.URL): HttpURLConnection {
            val httpsURLConnection = super
                .createConnection(url) as HttpsURLConnection
            try {
                httpsURLConnection.sslSocketFactory = getSSLSocketFactory(this)
                // httpsURLConnection.setHostnameVerifier(getHostnameVerifier());
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return httpsURLConnection
        }
    }

    private fun getSSLSocketFactory(context: Context): SSLSocketFactory {

        // the certificate file will be stored in \app\src\main\res\raw folder path
        val cf = CertificateFactory.getInstance("X.509")
        val caInput = context.resources.openRawResource(
            R.raw.server.cer
        )

        val ca = cf.generateCertificate(caInput)
        caInput.close()

        val keyStore = KeyStore.getInstance("BKS")

        keyStore.load(null, null)
        keyStore.setCertificateEntry("ca", ca)

        val tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm()
        val tmf = TrustManagerFactory.getInstance(tmfAlgorithm)
        tmf.init(keyStore)

        val wrappedTrustManagers = getWrappedTrustManagers(
            tmf
                .trustManagers
        )
        val sslContext = SSLContext.getInstance("TLS")
        sslContext.init(null, wrappedTrustManagers, null)

        return sslContext.socketFactory
    }
    private TrustManager[] getWrappedTrustManagers(TrustManager[] trustManagers) {
        final X509TrustManager originalTrustManager = (X509TrustManager) trustManagers[0];
        return new TrustManager[] { new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {
                return originalTrustManager.getAcceptedIssuers();
            }

            public void checkClientTrusted(X509Certificate[] certs,
                    String authType) {
                try {
                    if (certs != null && certs.length > 0) {
                        certs[0].checkValidity();
                    } else {
                        originalTrustManager
                                .checkClientTrusted(certs, authType);
                    }
                } catch (CertificateException e) {
                    Log.w("checkClientTrusted", e.toString());
                }
            }

            public void checkServerTrusted(X509Certificate[] certs,
                    String authType) {
                try {
                    if (certs != null && certs.length > 0) {
                        certs[0].checkValidity();
                    } else {
                        originalTrustManager
                                .checkServerTrusted(certs, authType);
                    }
                } catch (CertificateException e) {
                    Log.w("checkServerTrusted", e.toString());
                }
            }
        } };
    }*/
}
