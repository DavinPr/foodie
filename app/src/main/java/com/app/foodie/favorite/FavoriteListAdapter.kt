package com.app.foodie.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.core.domain.usecase.model.Favorite
import com.app.foodie.databinding.MealsItemBinding
import com.bumptech.glide.Glide

class FavoriteListAdapter : RecyclerView.Adapter<FavoriteListAdapter.FavoritesViewHolder>() {

    private val listFavorites = ArrayList<Favorite>()
    var onClickItem: ((Favorite) -> Unit)? = null

    fun setData(list: List<Favorite>?) {
        if (list == null) return
        listFavorites.clear()
        listFavorites.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoritesViewHolder =
        FavoritesViewHolder(
            MealsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        val meal = listFavorites[position]
        holder.bind(meal)
    }

    override fun getItemCount(): Int = listFavorites.size

    inner class FavoritesViewHolder(private val binding: MealsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(favorite: Favorite) {
            binding.mealsName.text = favorite.name
            Glide.with(itemView.context)
                .load(favorite.thumb)
                .into(binding.mealsThumb)
            binding.mealsCategory.text = favorite.category
        }

        init {
            itemView.setOnClickListener {
                onClickItem?.invoke(listFavorites[adapterPosition])
            }
        }
    }
}