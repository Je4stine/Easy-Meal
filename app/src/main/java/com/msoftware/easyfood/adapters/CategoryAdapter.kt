package com.msoftware.easyfood.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.msoftware.easyfood.R
import com.msoftware.easyfood.pojo.CategoriesList
import com.msoftware.easyfood.pojo.Category

class CategoryAdapter(): RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    var onItemClicked : ((Category)-> Unit)? = null

    var categoryList = ArrayList<Category>()
    fun setData (categoryList: List<Category>){
        this.categoryList = categoryList as ArrayList<Category>
        notifyDataSetChanged()
    }

    class CategoryViewHolder(val view: View): RecyclerView.ViewHolder(view) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.categotryitem, parent, false)

        return CategoryViewHolder(inflater)
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
       holder.itemView.findViewById<TextView>(R.id.tvCCategory).text = categoryList[position].strCategory
        Glide.with(holder.itemView.context)
            .load(categoryList[position].strCategoryThumb)
            .into(holder.itemView.findViewById(R.id.imageCategory))

        holder.itemView.setOnClickListener {
            onItemClicked!!.invoke(categoryList[position])
        }
    }
}