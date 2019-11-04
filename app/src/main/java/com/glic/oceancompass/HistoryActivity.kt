package com.glic.oceancompass

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.history.*

class HistoryActivity : AppCompatActivity(), RecycleViewClick {

    private lateinit var pref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.history)

        pref = this.getSharedPreferences("history", Context.MODE_PRIVATE)!!

        val historyList = arrayListOf<String>()

        val count = pref.getInt("count", 1)

        for (i in 1 until count) {
            historyList.add(pref.getString("$i"+"address", "")!!)
        }
        history.adapter = RecycleViewAdapter(7, historyList, this,this)
        history.layoutManager = LinearLayoutManager(this@HistoryActivity)
        history.setHasFixedSize(true)
    }

    override fun stateClick(value: String) {
        startActivity(Intent(this, RecommendResultActivity::class.java)
            .putExtra("key", pref.getString(value, ""))
            .putExtra("count", pref.getInt(value+"count", 1)))
        finish()
    }

    override fun cityClick(value: String) {
    }

    override fun onBackPressed() {
        startActivity(Intent(this, MyPageActivity::class.java))
        finish()
    }
}
