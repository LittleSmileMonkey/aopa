package com.sleep.aopa

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.allen.library.SuperTextView

/**
 * author：xingkong on 2019/3/2
 * e-mail：xingkong@changjinglu.net
 *
 */
class MainAdapter(private var activityList:MutableList<Class<out Activity>>): RecyclerView.Adapter<MainAdapter.MainHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): MainHolder {
        return MainHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.item_main, viewGroup, false))
    }

    override fun getItemCount(): Int {
        return activityList.size
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val item = activityList[position]
        holder.mStv.setCenterString(item.simpleName)
        holder.mStv.setOnClickListener {
            val intent = Intent(holder.mStv.context, item)
            holder.mStv.context.startActivity(intent)
        }
    }

    class MainHolder(view:View): RecyclerView.ViewHolder(view) {
        val mStv = view.findViewById<SuperTextView>(R.id.stv_item)
    }
}