package com.msoftware.easyfood.retrofit

import com.msoftware.easyfood.pojo.CategoriesList
import com.msoftware.easyfood.pojo.MealList
import com.msoftware.easyfood.pojo.PopularList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {

    @GET("random.php")
    fun getRandomMeal(): Call<MealList>

    @GET("lookup.php?")
    fun getMealById(@Query("i") id:String) : Call<MealList>

    @GET("filter.php?")
    fun getPopular(@Query("c") pname: String) : Call<PopularList>

    @GET("categories.php")
    fun getCategories(): Call<CategoriesList>

    @GET("filter.php")
    fun getMealsByCategory(@Query("c") categoryName: String): Call<PopularList>
}