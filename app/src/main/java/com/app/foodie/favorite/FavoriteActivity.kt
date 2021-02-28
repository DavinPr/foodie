package com.app.foodie.favorite

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.core.data.Resource
import com.app.foodie.databinding.ActivityFavoriteBinding
import com.app.foodie.detail.DetailActivity
import org.koin.android.viewmodel.ext.android.viewModel

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private val viewModel: FavoriteViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val favoriteAdapter = FavoriteListAdapter()

        viewModel.getAllFavoriteData().observe(this) { list ->
            when (list) {
                is Resource.Loading -> {
                }
                is Resource.Success -> {
                    if (list.data != null) {
                        favoriteAdapter.setData(list.data!!)
                    }
                }
                is Resource.Error -> {
                }
            }
        }

        favoriteAdapter.onClickItem = { favorite ->
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(DetailActivity.favorite_key, favorite)
            intent.putExtra(DetailActivity.ACTIVITY_CODE, 102)
            startActivity(
                intent, ActivityOptions.makeSceneTransitionAnimation(
                    this
                ).toBundle()
            )
        }

        binding.rvFavorite.apply {
            layoutManager = LinearLayoutManager(this@FavoriteActivity)
            hasFixedSize()
            adapter = favoriteAdapter
        }
    }
}