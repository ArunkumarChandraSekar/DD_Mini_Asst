package com.ex.dd_mini_asst.ui.foodlist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.bumptech.glide.Glide
import com.ex.dd_mini_asst.R
import com.ex.dd_mini_asst.di.ingredient.IngredientData

class IngredientAdapter(private val mContext : Context, var list : ArrayList<IngredientData>) : BaseAdapter(),   Filterable {
    private var layoutInflater: LayoutInflater? = null
    private var foodImg : ImageView? = null
    private var foodName : TextView? = null
    private var priceTxt : TextView? = null
    private var searchFilter : SearchFilter? = null
    private var filterList : ArrayList<IngredientData> = ArrayList()

    init {
        filterList.addAll(list)
    }


    override fun getCount(): Int {
        return filterList.size
    }

    override fun getItem(position: Int): Any {
       return filterList[position]
    }

    override fun getItemId(position: Int): Long {
       return position.toLong()
    }
    fun addList(  list : ArrayList<IngredientData>){
        this.list = list
        filterList = list
        notifyDataSetChanged()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var convertView = convertView
        if (layoutInflater == null) {
            layoutInflater =
                mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        }
        if (convertView == null) {
            convertView = layoutInflater!!.inflate(R.layout.adapter_ingredient_item, null)
        }
        convertView?.let {
            val data = filterList.get(position)
            foodImg = convertView.findViewById(R.id.adapter_ing_img)
            foodName = convertView.findViewById(R.id.adapter_ing_name_txt)
            priceTxt = convertView.findViewById(R.id.adapter_ing_price_value_txt)
            foodImg?.let {
                Glide.with(mContext).load(data.img).into(it)
            }
            foodName?.let {
                it.text = data.name
            }
            priceTxt?.let {
                it.text = "S$ ${data.price}"
            }

        }

        return convertView!!
    }

    override fun getFilter(): Filter {
        if (searchFilter == null) {
            searchFilter = SearchFilter()
        }

        return searchFilter!!

    }
    inner class SearchFilter() : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val results = FilterResults()
            val originalData: List<IngredientData> = list
            if(constraint!=null && constraint.isNotEmpty()){
                val tempList: ArrayList<IngredientData> = ArrayList<IngredientData>()
                for(i in originalData.indices){
                    if(originalData[i].name.contains(constraint)){
                        tempList.add(originalData[i])
                    }
                }

                results.count=tempList.size
                results.values=tempList;
            }else{
                results.count=list.size
                results.values=list
            }
            return  results

        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            results?.let {

                filterList = it.values as ArrayList<IngredientData>
                notifyDataSetChanged()
            }

        }
    }

}