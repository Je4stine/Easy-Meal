package com.msoftware.easyfood


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.msoftware.easyfood.adapters.CategoryAdapter
import com.msoftware.easyfood.adapters.PopularMealAdapter
import com.msoftware.easyfood.pojo.Meal
import com.msoftware.easyfood.viewModel.HomeViewModel


class Home : Fragment() {
    private lateinit var homeMvvm: HomeViewModel
    private lateinit var randomMeal: Meal
    private lateinit var popularAdapter: PopularMealAdapter
    private lateinit var popularRecycler: RecyclerView
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var categoryRecycler: RecyclerView

    companion object
    {
        const val MEAL_ID = "EasyID"
        const val MEAL_NAME = "EasyNAME"
        const val MEAL_THUMB = "EasyTHUMB"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        homeMvvm = ViewModelProvider(this@Home)[HomeViewModel::class.java]
        homeMvvm = (activity as MainActivity).viewModel


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_home, container, false)

        view.findViewById<CardView>(R.id.cardRandom)?.setOnClickListener {

            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(MEAL_ID, randomMeal.idMeal)
            intent.putExtra(MEAL_NAME, randomMeal.strMeal)
            intent.putExtra(MEAL_THUMB, randomMeal.strMealThumb)
            startActivity(intent)
        }

         popularRecycler = view.findViewById(R.id.recyclerPopular)
         popularRecycler.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
         popularAdapter = PopularMealAdapter(ArrayList())
         popularRecycler.adapter = popularAdapter

        categoryRecycler = view.findViewById(R.id.categoryRecycler)
        categoryRecycler.layoutManager = GridLayoutManager( context, 3, GridLayoutManager.VERTICAL, false)
        categoryAdapter = CategoryAdapter()
        categoryRecycler.adapter = categoryAdapter






        homeMvvm.getRandomMeal()
        observerRandomMeal()

        homeMvvm.getPopular()
        observerPopular()

        homeMvvm.getCategories()
        observerCategory()

        onPopularItemClick()

        onCategoryClicked()


        return view
    }

    private fun onCategoryClicked() {
        categoryAdapter.onItemClicked = {category ->

            val actions = HomeDirections.actionHome2ToMealDeatails2(category.strCategory)
            findNavController().navigate(actions)

        }
    }

    private fun observerCategory() {
        homeMvvm.observeCategories().observe(viewLifecycleOwner){
                category ->
                categoryAdapter.setData(category)

        }
    }

    private fun onPopularItemClick() {
        popularAdapter.onItemClick = {popular->
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(MEAL_ID, popular.idMeal)
            intent.putExtra(MEAL_NAME, popular.strMeal)
            intent.putExtra(MEAL_THUMB, popular.strMealThumb)
            startActivity(intent)

        }
    }


    private fun observerPopular() {
        homeMvvm.observePopular().observe(viewLifecycleOwner
        ) {
            popular ->
            popularAdapter.setData(ArrayList(popular))
        }
    }


    private fun observerRandomMeal() {
        homeMvvm.observeRandom().observe(viewLifecycleOwner
        ) { value ->
            view?.let {
                Glide.with(this@Home)
                    .load(value.strMealThumb)
                    .into(it.findViewById(R.id.imgRandomFood))
                this.randomMeal = value
            }
        }
    }


}