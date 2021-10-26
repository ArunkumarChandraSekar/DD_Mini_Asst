package com.ex.dd_mini_asst.ui.foodlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ex.dd_mini_asst.di.api_data.CommonApiState
import com.ex.dd_mini_asst.di.api_data.Status
import com.ex.dd_mini_asst.di.ingredient.IngredientData
import com.ex.dd_mini_asst.di.order_data.OrderResponse
import com.ex.dd_mini_asst.di.repo.ApiClient
import com.ex.dd_mini_asst.di.repo.ingredient.IngredientRepository
import com.ex.dd_mini_asst.di.repo.orders.OrderRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class IngredientViewModel :   ViewModel(){
    private val repository = IngredientRepository(
        ApiClient.create()
    )

 val commonState = MutableStateFlow(
        CommonApiState(
            Status.LOADING,
            ArrayList<IngredientData>(), ""
        )
    )

    fun getFoodList(){
        commonState.value = CommonApiState.loading()

        viewModelScope.launch {
            repository.getFoodList()
                .catch {
                    commonState.value =
                        CommonApiState.error(it.message.toString())
                }
                .collect {
                    commonState.value = CommonApiState.success(it.data)
                }
        }


    }
}