package com.ex.dd_mini_asst.di.repo

import com.ex.dd_mini_asst.di.ingredient.IngredientData
import com.ex.dd_mini_asst.di.order_data.OrderItem
import com.ex.dd_mini_asst.di.order_data.OrderResponse
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("api/v1/orderList")
    suspend fun getOrderList(): ArrayList<OrderResponse>

    @GET("api/v1/food_list")
    suspend fun getFoodList(): ArrayList<IngredientData>
}