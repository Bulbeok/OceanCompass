package com.glic.oceancompass

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.search_recycleview.view.*

class RecycleViewAdapter(private val index:Int, private val urlList: ArrayList<String>, val context: Context, private val listener: RecycleViewClick?):
    RecyclerView.Adapter<RecycleViewAdapter.Holder>() {

    private lateinit var view:View

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        view = when(index) {
            1,2,3 -> LayoutInflater.from(context).inflate(R.layout.search_recycleview, parent, false)
            else -> {
                LayoutInflater.from(context).inflate(R.layout.search_recycleview, parent, false)
            }
        }
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(urlList[position],index, position)
    }

    override fun getItemCount(): Int {
        return urlList.size
    }

    inner class Holder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        fun bind(str: String, index: Int, position: Int) {
            when(index) {
                1 -> {
                    itemView.location_recycleview_text.text = str.trim()
                    itemView.setOnClickListener {
                        listener!!.stateClick(urlList[position].trim())
                    }
                }
                2 -> {
                    itemView.location_recycleview_text.text = str.trim()
                    itemView.setOnClickListener {
                        listener!!.cityClick(urlList[position].trim())
                    }
                }
                3 -> {
                    itemView.location_recycleview_text.text = str.trim()
                    itemView.setOnClickListener {
                        if(itemView.truefalse.text == null || itemView.truefalse.text == "") {
                            itemView.truefalse.text = "1"
                            itemView.recycleview.setBackgroundColor(Color.parseColor("#008577"))
                            listener!!.stateClick(urlList[position].trim())
                        } else {
                            itemView.truefalse.text = null
                            itemView.recycleview.setBackgroundColor(Color.parseColor("#00000000"))
                            listener!!.cityClick(urlList[position].trim())
                        }
                    }
                }


            }
        }
    }
}