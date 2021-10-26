package com.ex.dd_mini_asst.ui.orders.adapter

import android.content.Context
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ex.dd_mini_asst.R
import com.ex.dd_mini_asst.di.order_data.OrderItem
import com.ex.dd_mini_asst.ui.orders.MainActivity
import com.ex.dd_mini_asst.ui.orders.frag.IncomingFrag
import com.ex.dd_mini_asst.utils.SegmentProgressDrawable
import com.ex.dd_mini_asst.utils.TimeUtils
import kotlinx.android.synthetic.main.fragment_incoming.view.*
import java.util.concurrent.TimeUnit

class OrderItemAdapter(val mContext : Context, val list : ArrayList<OrderItem>,val  frag : IncomingFrag, val type : String): RecyclerView.Adapter<OrderItemAdapter.VH>() {

    inner class VH(itemView : View):RecyclerView.ViewHolder(itemView){
        val id_txt = itemView.findViewById<TextView>(R.id.oi_id_txt)
        val time_txt = itemView.findViewById<TextView>(R.id.oi_at_time_txt)
        val remainig_time_txt = itemView.findViewById<TextView>(R.id.oi_remaining_time_txt)
        val prograssBar = itemView.findViewById<ProgressBar>(R.id.oi_progress_bar)
        val auto_lay = itemView.findViewById<ConstraintLayout>(R.id.oi_auto_reject_lay)
        val okayBtn = itemView.findViewById<Button>(R.id.oi_okay_bnt)
        val accpetBtn = itemView.findViewById<Button>(R.id.oi_accept_bnt)
        val rv= itemView.findViewById<RecyclerView>(R.id.oi_rv)
        val autoRjectTxt = itemView.findViewById<TextView>(R.id.oi_auto_reject_txt)
        var timer : CountDownTimer? = null
        init {
            accpetBtn.setOnClickListener {
                frag.updateOrderStatus(adapterPosition)
                list.removeAt(adapterPosition)
                notifyItemRemoved(adapterPosition)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_order_item, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val data = list[position]
        holder.id_txt.text = "#${data.id}"

        TimeUtils.dateConvertTime(data.created_at).let {
            holder.time_txt.text = it
        }



        holder.prograssBar.visibility =View.GONE
        holder.remainig_time_txt.visibility =View.GONE
        holder.autoRjectTxt.visibility = View.GONE
        if (!type.equals(MainActivity.FragType.COLLECTION.type)){
            TimeUtils.remainingTime(data.expired_at)?.let {
                holder.prograssBar.visibility =View.VISIBLE
                holder.remainig_time_txt.visibility =View.VISIBLE
                holder.autoRjectTxt.visibility = View.VISIBLE
                holder.auto_lay.visibility = View.GONE
                holder.accpetBtn.visibility = View.VISIBLE

                println("Check::::::::::::::::::::: remainingTime ${it}")

                if (holder.timer != null) {
                    holder.timer!!.cancel();
                }


                updateTime(holder, it)


                //
                holder.timer = object: CountDownTimer(it, 1000) {
                    override fun onTick(millisUntilFinished: Long) {

                        updateTime(holder, millisUntilFinished)

                    }

                    override fun onFinish() {
                        holder.auto_lay.visibility = View.VISIBLE
                        holder.prograssBar.visibility =View.GONE
                        holder.remainig_time_txt.visibility =View.GONE
                        holder.autoRjectTxt.visibility = View.GONE
                        holder.accpetBtn.visibility = View.GONE
                    }
                }
                (holder.timer as CountDownTimer).start()


                holder.prograssBar.progressDrawable = SegmentProgressDrawable(ContextCompat.getColor(mContext, R.color.orange),
                    ContextCompat.getColor(mContext,R.color.gray))
            }?: run {
                holder.auto_lay.visibility = View.VISIBLE
                holder.prograssBar.visibility =View.GONE
                holder.remainig_time_txt.visibility =View.GONE
                holder.autoRjectTxt.visibility = View.GONE
                holder.accpetBtn.visibility = View.GONE
            }
        }else{
            holder.accpetBtn.text = mContext.getString(R.string.done)
        }


        val  adapter = AddonOrderAdapter(mContext, list)
        holder.rv.setHasFixedSize(true);
        holder.rv.setLayoutManager(LinearLayoutManager(mContext));
        holder.rv.adapter = adapter

    }

    fun updateTime(holder: VH, time: Long){
        val  sec = "${TimeUnit.MILLISECONDS.toSeconds(time )}"
        val  min = "${TimeUnit.MILLISECONDS.toMinutes(time )}"
        val  hours = "${TimeUnit.MILLISECONDS.toHours(time)}"
        val day = "${TimeUnit.MILLISECONDS.toDays(time)}"

        holder.remainig_time_txt.text = "${day} ${hours}:${min.toLong() % 60}:${sec.toLong() % 60}"


    }
    override fun getItemCount(): Int {
       return list.size
    }
}