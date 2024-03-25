package com.msoftware.easyfood.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.msoftware.easyfood.pojo.Popular
import com.msoftware.easyfood.pojo.PopularList
import com.msoftware.easyfood.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryViewModel: ViewModel() {
    val mealByCategotyLiveData = MutableLiveData<List<Popular>>()
    fun getMealsByCategory(categoryName: String)
    {
        RetrofitInstance.api.getMealsByCategory(categoryName).enqueue( object : Callback<PopularList>{
            override fun onResponse(call: Call<PopularList>, response: Response<PopularList>) {
                response.body()?.let {mealList->
                    mealByCategotyLiveData.postValue(mealList.meals)

                }
            }

            override fun onFailure(call: Call<PopularList>, t: Throwable) {
                Log.d("Cateory List", t.message.toString())
            }

        })
    }

    fun observeMealsByCategory():LiveData<List<Popular>>{
        return mealByCategotyLiveData

    }
}