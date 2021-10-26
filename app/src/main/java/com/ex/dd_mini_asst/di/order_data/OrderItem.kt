package com.ex.dd_mini_asst.di.order_data

import java.io.Serializable

data class  OrderItem(
    val id : Int,
    val title : String,
    val addon : ArrayList<Addon>,
    val quantity : Int,
    var status : String,
    val created_at : String,
    val alerted_at : String,
    val expired_at : String

): Serializable{
    constructor(): this(0, "", ArrayList(), 0,"","","","")
}

data class Addon(
    val id: Int,
    val title: String,
    val quantity : Int

) : Serializable

data class OrderResponse(
    val status : StatusData,
    val data : ArrayList<OrderItem>

): Serializable


data class StatusData(
    val success : Boolean,
    val statusCode : Int,
    val message : String
): Serializable
//[{"status":{"success":true,"statusCode":200,"message":"success"},
// "data":[{"id":10,"title":"Special extra large fried rice","addon":[{"id":21,"title":"Fried Egg","quantity":3}],"quantity":1,"status":"pending","created_at":"2021-06-10T15:00+00Z","alerted_at":"2021-06-10T15:03+00Z","expired_at":"2021-06-10T15:05+00Z"},{"id":11,"title":"Chicken Noodle","addon":[{"id":26,"title":"Extra chicken","quantity":2},{"id":27,"title":"Sambal","quantity":1}],"quantity":1,"status":"pending","created_at":"2021-06-10T15:10+00Z","alerted_at":"2021-06-10T15:13+00Z","expired_at":"2021-06-10T15:15+00Z"}]}]