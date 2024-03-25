package com.msoftware.easyfood.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.msoftware.easyfood.R
import com.msoftware.easyfood.pojo.Popular

class ListOfMealsAdapter(private val categoryListMeals: ArrayList<Popular>) : RecyclerView.Adapter<ListOfMealsAdapter.ListOfMealViewHolder>() {

    fun setData(categoryList: List<Popular>) {
        categoryListMeals.clear()
        categoryListMeals.addAll(categoryList)
        notifyDataSetChanged()
    }

    class ListOfMealViewHolder(private val view: View): RecyclerView.ViewHolder(view){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListOfMealViewHolder {
        val myView = LayoutInflater.from(parent.context).inflate(R.layout.categorylistitems, parent, false)

        return ListOfMealViewHolder(myView)
    }

    override fun getItemCount(): Int {
        return categoryListMeals.size
    }

    override fun onBindViewHolder(holder: ListOfMealViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(categoryListMeals[position].strMealThumb)
            .into(holder.itemView.findViewById(R.id.imgCategoyList))

        holder.itemView.findViewById<TextView>(R.id.tvListName).text = categoryListMeals[position].strMeal
    }
}