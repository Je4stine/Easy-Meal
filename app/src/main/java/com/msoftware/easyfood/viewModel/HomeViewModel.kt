package com.msoftware.easyfood.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.msoftware.easyfood.pojo.Meal
import com.msoftware.easyfood.pojo.MealList
import com.msoftware.easyfood.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {
    private var randomLiveData = MutableLiveData<Meal>()
    fun getRandomMeal()
    {
        RetrofitInstance.api.getRandomMeal().enqueue(object: Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if(response.body() != null)
                {
                    val rendomMeal: Meal = response.body()!!.meals[0]
                    randomLiveData.value = rendomMeal

                } else {
                    return
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("Main", t.message.toString())
            }
        })
    }


    fun observeRandom ():LiveData<Meal>
    {
        return randomLiveData
    }
}