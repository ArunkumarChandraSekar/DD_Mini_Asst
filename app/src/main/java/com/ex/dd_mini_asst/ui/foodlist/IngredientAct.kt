package com.ex.dd_mini_asst.ui.foodlist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import com.ex.dd_mini_asst.R
import android.R.menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.SearchView
import android.widget.Toast

import androidx.core.view.MenuItemCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.ex.dd_mini_asst.di.api_data.Status
import com.ex.dd_mini_asst.di.ingredient.IngredientData
import com.ex.dd_mini_asst.ui.base.BaseAct
import com.ex.dd_mini_asst.ui.orders.OrderViewModel
import kotlinx.android.synthetic.main.activity_ingredient.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class IngredientAct : BaseAct() {

    lateinit var viewModel : IngredientViewModel
    private var foodList:ArrayList<IngredientData> = ArrayList<IngredientData>()
    lateinit var adapterIngredient : IngredientAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ingredient)
        viewModel = ViewModelProvider(this).get(IngredientViewModel::class.java)
        viewModel.getFoodList()
        adapterIngredient = IngredientAdapter(this,foodList )
        ia_gridView.adapter = adapterIngredient

        lifecycleScope.launch{
            viewModel.commonState.collect {
                when(it.status){
                    Status.LOADING -> {
                        showProgress()
                    }
                    Status.SUCCESS -> {
                        hideProgress()
                        foodList.clear()
                        it.data?.let { it1 ->
                            foodList.addAll(it1)
                            adapterIngredient.addList(it1)
                        }
                    }
                    else -> {
                         hideProgress()
                        Toast.makeText(applicationContext, "Error", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val inflater = menuInflater
        inflater.inflate(R.menu.ingredient_search_menu, menu)

        val searchViewItem: MenuItem = menu!!.findItem(R.id.search_bar)
        val searchView: SearchView = searchViewItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    adapterIngredient.filter.filter(query)
                }

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    adapterIngredient.filter.filter(it)
                }
                return false
            }

        })

        return super.onCreateOptionsMenu(menu)
    }
}