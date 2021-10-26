package com.ex.dd_mini_asst.ui.orders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ex.dd_mini_asst.di.api_data.CommonApiState
import com.ex.dd_mini_asst.di.api_data.Status
import com.ex.dd_mini_asst.di.order_data.OrderResponse
import com.ex.dd_mini_asst.di.repo.ApiClient
import com.ex.dd_mini_asst.di.repo.orders.OrderRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class OrderViewModel :  ViewModel() {
    private val repository = OrderRepository(
        ApiClient.create()
    )

    val commonState = MutableStateFlow(
        CommonApiState(
            Status.LOADING,
            ArrayList<OrderResponse>(), ""
        )
    )


    fun getOrder() {

        // Since Network Calls takes time,Set the
        // initial value to loading state
        commonState.value = CommonApiState.loading()

        // ApiCalls takes some time, So it has to be
        // run and background thread. Using viewModelScope
        // to call the api
        viewModelScope.launch {

            // Collecting the data emitted
            // by the function in repository
            repository.getOrder()
                // If any errors occurs like 404 not found
                // or invalid query, set the state to error
                // State to show some info
                // on screen
                .catch {
                    commonState.value =
                        CommonApiState.error(it.message.toString())
                }
                // If Api call is succeeded, set the State to Success
                // and set the response data to data received from api
                .collect {
                    commonState.value = CommonApiState.success( it.data)
                }
        }
    }
}