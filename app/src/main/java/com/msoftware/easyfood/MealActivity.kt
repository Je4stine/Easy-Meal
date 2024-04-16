package com.msoftware.easyfood

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.msoftware.easyfood.db.MealDatabase
import com.msoftware.easyfood.pojo.Meal
import com.msoftware.easyfood.viewModel.MealDeetsViewModel
import com.msoftware.easyfood.viewModel.MealViewModelFactory

class MealActivity : AppCompatActivity() {

    private lateinit var mealID : String
    private lateinit var mealName: String
    private lateinit var mealThumb: String
    private lateinit var mealVM: MealDeetsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_meal)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val mealDatabase = MealDatabase.getInstance(this)
        val viewModelFactory = MealViewModelFactory(mealDatabase)

        mealVM = ViewModelProvider(this, viewModelFactory).get(MealDeetsViewModel::class.java)

        //mealVM = ViewModelProviders.of(this)[MealDeetsViewModel::class.java]

        getIntentInfo()
        setInfoToView()

        val theName = findViewById<CollapsingToolbarLayout>(R.id.collapseToolBar)
        theName.title = mealName
        theName.setCollapsedTitleTextColor(resources.getColor(R.color.white))
        theName.setExpandedTitleColor(resources.getColor(R.color.white))

//        onLoading()

        mealVM.getMealDetails(mealID)

        observeTheData()

        favouriteClicked()

    }

    private var dbMeal: Meal ? = null

    private fun observeTheData() {
        mealVM.observeMealDetailsLiveData().observe(this, object : Observer<Meal>{
            override fun onChanged(value: Meal) {
                dbMeal = value
//                onLoaded()
                findViewById<TextView>(R.id.tvDCategory).text = value.strCategory
                findViewById<TextView>(R.id.tvDArea).text = value.strArea
                findViewById<TextView>(R.id.tvInstructions).text = value.strInstructions

            }

        })
    }

    private fun setInfoToView() {
        Glide.with(applicationContext)
            .load(mealThumb)
            .into(findViewById(R.id.imgRandomDeets))


    }

    private fun getIntentInfo() {
        val intent = intent
        mealID = intent.getStringExtra(Home.MEAL_ID)!!
        mealName = intent.getStringExtra(Home.MEAL_NAME)!!
        mealThumb = intent.getStringExtra(Home.MEAL_THUMB)!!
    }


    private fun onLoading()
    {
        findViewById<ProgressBar>(R.id.progressBar).visibility = View.VISIBLE
        findViewById<Button>(R.id.fabFavourite).visibility = View.INVISIBLE
        findViewById<Button>(R.id.tvDArea).visibility = View.INVISIBLE
        findViewById<Button>(R.id.tvDCategory).visibility = View.INVISIBLE
        findViewById<Button>(R.id.tvInstructions).visibility = View.INVISIBLE
        findViewById<Button>(R.id.tvInsta).visibility = View.INVISIBLE

    }

    private fun onLoaded()
    {
        findViewById<ProgressBar>(R.id.progressBar).visibility = View.INVISIBLE
        findViewById<Button>(R.id.fabFavourite).visibility = View.VISIBLE
        findViewById<Button>(R.id.tvDArea).visibility = View.VISIBLE
        findViewById<Button>(R.id.tvDCategory).visibility = View.VISIBLE
        findViewById<Button>(R.id.tvInstructions).visibility = View.VISIBLE
        findViewById<Button>(R.id.tvInsta).visibility = View.VISIBLE

    }
    private fun favouriteClicked(){
        val fab = findViewById<FloatingActionButton>(R.id.fabFavourite)
        fab.setOnClickListener {
            dbMeal?.let {
                mealVM.addToDb(it)
            }

            Toast.makeText(this, "Item added to favourites", Toast.LENGTH_LONG).show()
        }

    }
}