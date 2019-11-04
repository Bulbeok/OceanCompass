package com.glic.oceancompass

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recommed_recycleview.view.*
import kotlinx.android.synthetic.main.recycleviewitem.view.*

class RecycleViewAdapter(private val index:Int, private val urlList: ArrayList<String>, val context: Context, private val listener: RecycleViewClick?):
    RecyclerView.Adapter<RecycleViewAdapter.Holder>() {

    private lateinit var view:View

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        view = when(index) {
            6 -> LayoutInflater.from(context).inflate(R.layout.recommed_recycleview, parent, false)
            else -> LayoutInflater.from(context).inflate(R.layout.recycleviewitem, parent, false)
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
            when (index) {
                1 -> {
                    itemView.recycleview_text.text = str.trim()
                    itemView.setOnClickListener {
                        listener!!.stateClick(urlList[position].trim())
                    }
                }
                2 -> {
                    itemView.recycleview_text.text = str.trim()
                    itemView.setOnClickListener {
                        listener!!.cityClick(urlList[position].trim())
                    }
                }
                3 -> {
                    itemView.recycleview_text.text = str.trim()
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
                4 -> {
                    itemView.recycleview_text.text = str.trim().split(",")[0]
                    itemView.setOnClickListener {
                        listener!!.stateClick(urlList[position].trim())
                    }
                }
                5 -> {
                    if(str.trim() == "") {
                        itemView.recycleview_text.text = "검색결과가 없습니다."
                    } else {
                        itemView.recycleview_text.text = str.trim().split("/")[0]
                        itemView.setOnClickListener {
                            listener!!.cityClick(urlList[position].trim())
                        }
                    }
                }
                6 -> {
                    itemView.countday.text = (position + 1).toString() + "일차"
                    val temp = str.trim().split(",")
                    for (i in 1 until temp.size) {
                        itemView.findViewById<TextView>(itemView.resources.getIdentifier("type$i", "id", context.packageName)).text = temp[i-1]
                        if(i != 1) {
                            itemView.findViewById<ImageView>(itemView.resources.getIdentifier("imageView$i", "id", context.packageName)).visibility = View.VISIBLE
                        }
                    }
                    itemView.setOnClickListener {
                        listener!!.stateClick(str)
                    }
                }
                7 -> {
                    itemView.recycleview_text.text = str.trim()
                    itemView.setOnClickListener {
                        listener!!.stateClick((position+1).toString())
                    }
                }
                8 -> {
                    if(str.trim() == "") {
                        itemView.recycleview_text.text = "작성한 글이 없습니다."
                    } else {
                        itemView.recycleview_text.text = str.trim().split("/")[0]
                        itemView.setOnClickListener {
                            listener!!.stateClick(urlList[position].trim().split("/")[urlList[position].trim().split("/").size-1])
                        }
                    }
                }
            }
        }
    }
}