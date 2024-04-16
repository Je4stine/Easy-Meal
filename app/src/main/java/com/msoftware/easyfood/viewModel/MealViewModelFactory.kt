package com.msoftware.easyfood.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.msoftware.easyfood.db.MealDatabase

class MealViewModelFactory
    ( private val mealDatabase: MealDatabase)
    : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MealDeetsViewModel(mealDatabase) as T
    }
}