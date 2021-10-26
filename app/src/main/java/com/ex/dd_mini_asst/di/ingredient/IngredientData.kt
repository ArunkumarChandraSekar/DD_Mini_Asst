package com.ex.dd_mini_asst.di.ingredient

import java.io.Serializable

data class IngredientData(
    val id : String,
    val img : String,
    val name : String,
    val dsc : String,
    val price : Float,
    val rate : Int,
    val country : String
): Serializable
