package com.ex.dd_mini_asst.ui.orders.frag

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.ex.dd_mini_asst.R
import com.ex.dd_mini_asst.di.api_data.Status
import com.ex.dd_mini_asst.di.order_data.OrderItem
import com.ex.dd_mini_asst.ui.orders.MainActivity
import com.ex.dd_mini_asst.ui.orders.adapter.OrderItemAdapter
import kotlinx.android.synthetic.main.fragment_incoming.view.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match

private const val ARG_PARAM1 = "param1"


/**
 * A simple [Fragment] subclass.
 * Use the [IncomingFrag.newInstance] factory method to
 * create an instance of this fragment.
 */
class IncomingFrag : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null

    lateinit var act : MainActivity
    lateinit var orderListAdapter : OrderItemAdapter
    var orderlist : ArrayList<OrderItem> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_incoming, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        act =  (activity as MainActivity)
        view.incoming_frag_rv.setHasFixedSize(true);
        view.incoming_frag_rv.layoutManager = LinearLayoutManager(activity);
        orderListAdapter = OrderItemAdapter(act,orderlist, this, param1?:"")
        view.incoming_frag_rv.adapter = orderListAdapter

        lifecycleScope.launch{
           act.viewModel.commonState.collect {
                when(it.status){
                    Status.LOADING -> {
                        act.showProgress()
                    }
                    Status.SUCCESS -> {
                        act.hideProgress()
                        orderlist.clear()
                        it.data?.get(0)?.let { it1 -> orderlist.addAll(it1.data.filter { it.status.equals(param1) }) }
                        orderListAdapter.notifyDataSetChanged()

                    }
                    else -> {
                        act. hideProgress()
                        Toast.makeText(act.applicationContext, "Error", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

    }

    fun updateOrderStatus(position : Int){
        when(param1){
            MainActivity.FragType.INCOMING.type ->{
                orderlist[position].status = MainActivity.FragType.PREPARING.type
            }
            MainActivity.FragType.PREPARING.type ->{
                orderlist[position].status = MainActivity.FragType.COLLECTION.type
            }
            MainActivity.FragType.COLLECTION.type ->{
                orderlist[position].status =   MainActivity.FragType.COLLECTION.type
            }
        }

    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment IncomingFrag.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String) =
            IncomingFrag().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)

                }
            }
    }
}