package com.app.foodie.dashboard

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.app.core.domain.usecase.model.Category
import com.app.foodie.R
import com.app.foodie.databinding.CategoriesItemBinding
import com.bumptech.glide.Glide

class CategoriesListAdapter : RecyclerView.Adapter<CategoriesListAdapter.CategoriesViewHolder>() {

    private val listItem = ArrayList<Category>()
    private var currPos : Int = 0

    fun setData(list : List<Category>){
        listItem.clear()
        listItem.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoriesListAdapter.CategoriesViewHolder =
        CategoriesViewHolder(
            CategoriesItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(
        holder: CategoriesListAdapter.CategoriesViewHolder,
        position: Int
    ) {
        val item = listItem[position]
        if (position == currPos){
            holder.setBackgroundColor(R.color.yellow)
        } else{
            holder.setBackgroundColor(R.color.inactive_item)
        }
        holder.bind(item)
    }

    override fun getItemCount(): Int = listItem.size

    inner class CategoriesViewHolder(private val binding: CategoriesItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(category: Category){
            binding.categoriesName.text = category.category
            Glide.with(itemView.context)
                .load(category.thumb)
                .into(binding.categoriesThumb)
        }
        fun setBackgroundColor(color: Int){
            binding.categoriesItemContainer.setBackgroundColor(ContextCompat.getColor(itemView.context, color))
        }
    }
}