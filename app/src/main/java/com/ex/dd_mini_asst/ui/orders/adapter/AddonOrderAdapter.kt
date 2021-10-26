package com.ex.dd_mini_asst.ui.orders.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ex.dd_mini_asst.R
import com.ex.dd_mini_asst.di.order_data.OrderItem

class AddonOrderAdapter( private val list : ArrayList<OrderItem> ) : RecyclerView.Adapter<AddonOrderAdapter.VH>() {

    inner class VH(v : View) : RecyclerView.ViewHolder(v){
        val foodNameTxt: TextView = v.findViewById(R.id.food_name_txt)
        val subNameTxt: TextView = v.findViewById(R.id.food_sub_name_txt)
        val quantityTxt: TextView =  v.findViewById (R.id.food_count_txt)
        val subQuantityTxt: TextView = v.findViewById (R.id.food_sub_count_txt)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_food_item, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val data = list[position]
        holder.foodNameTxt.text = data.title
        holder.subNameTxt.text = data.addon[0].title
        holder.quantityTxt.text = data.quantity.toString()
        holder.subQuantityTxt.text = data.addon[0].quantity.toString()
    }

    override fun getItemCount(): Int {
        return list.size
    }
}