package com.msoftware.easyfood.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msoftware.easyfood.db.MealDatabase
import com.msoftware.easyfood.pojo.CategoriesList
import com.msoftware.easyfood.pojo.Category
import com.msoftware.easyfood.pojo.Meal
import com.msoftware.easyfood.pojo.MealList
import com.msoftware.easyfood.pojo.Popular
import com.msoftware.easyfood.pojo.PopularList
import com.msoftware.easyfood.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(private val mealDatabase: MealDatabase) : ViewModel() {
    private var randomLiveData = MutableLiveData<Meal>()
    private var popularLiveData = MutableLiveData<List<Popular>>()
    private var categoryLiveData = MutableLiveData<List<Category>>()
    private var dbLiveData = mealDatabase.mealDao().getAll()
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

    fun getPopular ()
    {
        RetrofitInstance.api.getPopular("Seafood").enqueue( object: Callback<PopularList>{
            override fun onResponse(call: Call<PopularList>, response: Response<PopularList>) {
                if(response.body() != null)
                {
                    popularLiveData.value = response.body()!!.meals
                } else return
            }

            override fun onFailure(call: Call<PopularList>, t: Throwable) {
                Log.d("Main", t.message.toString())
            }

        })
    }

    fun getCategories()
    {
        RetrofitInstance.api.getCategories().enqueue( object : Callback<CategoriesList>{
            override fun onResponse(
                call: Call<CategoriesList>,
                response: Response<CategoriesList>
            ) {
                if(response.body() != null)
                {
                    categoryLiveData.value = response.body()!!.categories
                } else return
            }

            override fun onFailure(call: Call<CategoriesList>, t: Throwable) {
                Log.d("Main", t.message.toString())
            }

        })
    }


    fun observeRandom ():LiveData<Meal>
    {
        return randomLiveData
    }

    fun observePopular ():LiveData<List<Popular>>
    {
        return popularLiveData
    }

    fun observeCategories ():LiveData<List<Category>>
    {
        return categoryLiveData
    }

    fun observedbLiveData ():LiveData<List<Meal>>
    {
        return dbLiveData
    }

    fun deleteFromDb(meal: Meal)
    {
        viewModelScope.launch {
            mealDatabase.mealDao().deleteMeal(meal)
        }
    }

    fun addToDb (meal: Meal){
        viewModelScope.launch {
            mealDatabase.mealDao().insertMeal(meal)
        }
    }


}