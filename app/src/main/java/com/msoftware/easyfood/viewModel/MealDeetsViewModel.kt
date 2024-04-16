package com.msoftware.easyfood.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msoftware.easyfood.db.MealDatabase
import com.msoftware.easyfood.pojo.Meal
import com.msoftware.easyfood.pojo.MealList
import com.msoftware.easyfood.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealDeetsViewModel(val mealDatabase: MealDatabase): ViewModel() {
    private var mealDetailsLiveData : MutableLiveData<Meal> = MutableLiveData()

    fun getMealDetails(id: String)
    {
        RetrofitInstance.api.getMealById(id).enqueue( object : Callback<MealList>{
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if(response.body() != null)
                {
                    mealDetailsLiveData.value = response.body()!!.meals[0]
                } else return
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("DetailsCall", t.message.toString())
            }

        })
    }

    fun observeMealDetailsLiveData (): LiveData<Meal>
    {
        return mealDetailsLiveData
    }

    fun addToDb (meal: Meal){
        viewModelScope.launch {
            mealDatabase.mealDao().insertMeal(meal)
        }
    }

    fun deleteFromDb(meal: Meal)
    {
        viewModelScope.launch {
            mealDatabase.mealDao().deleteMeal(meal)
        }
    }



}