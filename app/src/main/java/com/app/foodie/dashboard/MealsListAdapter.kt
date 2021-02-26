package com.app.foodie.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.core.domain.usecase.model.Meal
import com.app.foodie.databinding.MealsItemBinding
import com.bumptech.glide.Glide

class MealsListAdapter : RecyclerView.Adapter<MealsListAdapter.MealsViewHolder>() {

    private val listMeals = ArrayList<Meal>()
    private var category = "Category"
    var onClickItem : ((String) -> Unit)? = null

    fun setData(list: List<Meal>) {
        listMeals.clear()
        listMeals.addAll(list)
        notifyDataSetChanged()
    }

    fun setCategory(category: String) {
        this.category = category
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MealsListAdapter.MealsViewHolder =
        MealsViewHolder(
            MealsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: MealsListAdapter.MealsViewHolder, position: Int) {
        val meal = listMeals[position]
        holder.bind(meal)
    }

    override fun getItemCount(): Int = listMeals.size

    inner class MealsViewHolder(private val binding: MealsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(meal: Meal) {
            binding.mealsName.text = meal.name
            Glide.with(itemView.context)
                .load(meal.thumb)
                .into(binding.mealsThumb)
            binding.mealsCategory.text = category
        }

        init {
            itemView.setOnClickListener {
                listMeals[adapterPosition].idMeal?.let { id -> onClickItem?.invoke(id) }
            }
        }
    }
}