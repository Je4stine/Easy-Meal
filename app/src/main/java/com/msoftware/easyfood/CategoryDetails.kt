package com.msoftware.easyfood

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.msoftware.easyfood.adapters.ListOfMealsAdapter
import com.msoftware.easyfood.viewModel.CategoryViewModel


class CategoryDetails : Fragment() {
    private lateinit var categoryViewModel: CategoryViewModel

    lateinit var categoryListAdapter: ListOfMealsAdapter

    lateinit var categoryRecycler: RecyclerView

    private val args : CategoryDetailsArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        categoryViewModel = ViewModelProvider(this@CategoryDetails)[CategoryViewModel::class.java]

        categoryViewModel.getMealsByCategory(args.categoryName)

        categoryViewModel.observeMealsByCategory().observe(this, Observer {mealList ->

                categoryListAdapter.setData(mealList)
        })


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_category_deatails2, container, false)

        categoryListAdapter = ListOfMealsAdapter(ArrayList())

        categoryRecycler = view?.findViewById(R.id.categoryListRecycler)!!
        categoryRecycler.layoutManager = GridLayoutManager(activity,  2, GridLayoutManager.VERTICAL, false)
        categoryRecycler.adapter = categoryListAdapter

        return view
    }


}