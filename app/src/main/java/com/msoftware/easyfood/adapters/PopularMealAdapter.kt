package com.msoftware.easyfood.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.msoftware.easyfood.R
import com.msoftware.easyfood.pojo.Popular

class PopularMealAdapter(private val popularMeals: ArrayList<Popular>): RecyclerView.Adapter<PopularMealAdapter.PopularViewHolder>() {

    lateinit var onItemClick: ((Popular)-> Unit)
    fun setData(popularList: ArrayList<Popular>) {
        popularMeals.clear()
        popularMeals.addAll(popularList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularViewHolder {
        val myView = LayoutInflater.from(parent.context).inflate(R.layout.popularmealitem, parent,false)

        return PopularViewHolder(myView)
    }

    override fun getItemCount(): Int {
       return popularMeals.size
    }

    override fun onBindViewHolder(holder: PopularViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(popularMeals[position].strMealThumb)
            .into(holder.view.findViewById(R.id.imgPopular))

            holder.itemView.setOnClickListener {
                onItemClick.invoke(popularMeals[position])
            }


    }

    class PopularViewHolder(val view: View):RecyclerView.ViewHolder(view)
    {


    }
}