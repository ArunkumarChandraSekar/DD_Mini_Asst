package com.ex.dd_mini_asst.di.repo.orders

import com.ex.dd_mini_asst.di.api_data.CommonApiState
import com.ex.dd_mini_asst.di.order_data.OrderResponse
import com.ex.dd_mini_asst.di.repo.ApiInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class OrderRepository(private val apiService: ApiInterface) {
    suspend fun getOrder(): Flow<CommonApiState< ArrayList<OrderResponse>>> {
        return flow {

            // get the comment Data from the api
            val comment=apiService.getOrderList()

            // Emit this data wrapped in
            // the helper class [CommentApiState]
            emit(CommonApiState.success(comment))
        }.flowOn(Dispatchers.IO)
    }

}