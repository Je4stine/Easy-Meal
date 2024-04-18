package com.msoftware.easyfood.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.msoftware.easyfood.R
import com.msoftware.easyfood.pojo.Meal

class FavouritesAdapter : RecyclerView.Adapter<FavouritesAdapter.FavouriteViewHolder>() {
    inner class FavouriteViewHolder(view: View): RecyclerView.ViewHolder(view)

    private val diffUtil = object : DiffUtil.ItemCallback<Meal>(){
        override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem.idMeal == newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
           return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteViewHolder {
        val myView = LayoutInflater.from(parent.context).inflate(R.layout.categorylistitems, parent, false)
        return FavouriteViewHolder(myView)
    }

    override fun getItemCount(): Int {
        Log.d("Item Size", differ.currentList.size.toString())
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: FavouriteViewHolder, position: Int) {
        val meal = differ.currentList[position]
        Glide.with(holder.itemView).load(meal.strMealThumb).into(holder.itemView.findViewById(R.id.imgCategoyList))
        holder.itemView.findViewById<TextView>(R.id.tvListName).text = meal.strMeal
    }
}