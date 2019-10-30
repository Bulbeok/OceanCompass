package com.glic.oceancompass

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.search_location_recycleview.view.*

class RecycleViewAdapter(private val index:Int, private val urlList: ArrayList<String>, val context: Context, private val listener: RecycleViewClick?):
    RecyclerView.Adapter<RecycleViewAdapter.Holder>() {

    private lateinit var view:View

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        view = LayoutInflater.from(context).inflate(R.layout.search_location_recycleview, parent, false)
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
            itemView.location_recycleview_text.text = str
        }
    }
}