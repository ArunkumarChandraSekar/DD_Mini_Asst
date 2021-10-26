package com.ex.dd_mini_asst.ui.orders

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.ex.dd_mini_asst.R
import com.ex.dd_mini_asst.di.api_data.Status
import com.ex.dd_mini_asst.ui.base.BaseAct
import com.ex.dd_mini_asst.ui.foodlist.IngredientAct
import com.ex.dd_mini_asst.ui.orders.adapter.OrderTabAdapter
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : BaseAct() {
    lateinit var tabAdapter: OrderTabAdapter
    var tabTitles = ArrayList<String>()

    lateinit var viewModel: OrderViewModel

    enum class FragType(val type: String) {
        INCOMING("incoming"),
        PREPARING("preparing"),
        COLLECTION("collection")
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tabAdapter = OrderTabAdapter(this)
        main_pager.adapter = tabAdapter
        tabTitles.addAll(resources.getStringArray(R.array.order_tab_list))

        TabLayoutMediator(main_tab_layout, main_pager) { tab, position ->
            tab.text = tabTitles[position]
            main_pager.setCurrentItem(tab.position, true)
        }.attach()

        main_tab_layout.setSelectedTabIndicatorColor(ContextCompat.getColor(this,R.color.orange))
        main_tab_layout.setTabTextColors(
            ContextCompat.getColor(this,R.color.cardview_dark_background),
            ContextCompat.getColor(this,R.color.orange)
        )

        // initialize viewModel
        viewModel = ViewModelProvider(this).get(OrderViewModel::class.java)
        viewModel.getOrder()

        main_menu_img.setOnClickListener {

            startActivity(Intent(this, IngredientAct::class.java))
        }

    }
}