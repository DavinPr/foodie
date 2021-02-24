package com.app.foodie.dashboard

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.core.data.Resource
import com.app.core.domain.usecase.model.Category
import com.app.foodie.R
import com.app.foodie.databinding.ActivityDashboardBinding
import com.bumptech.glide.Glide
import org.koin.android.viewmodel.ext.android.viewModel

class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding
    private val viewModel: DashboardViewModel by viewModel()
    private lateinit var mealsAdapter : MealsListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "The Meal DB"

        val categoriesAdapter = CategoriesListAdapter()
        mealsAdapter = MealsListAdapter()

        viewModel.getAllCategories().observe(this) { category ->
            when (category) {
                is Resource.Loading -> {
                }
                is Resource.Success -> {
                    if (category.data != null) {
                        categoriesAdapter.setData(category.data!!)
                        category.data!!.first().apply {
                            setPrevMeal(this)
                            this.category?.let { loadMeals(it) }
                        }
                    }
                }
                is Resource.Error -> {
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                }
            }
        }

        categoriesAdapter.onClickItem = {category ->
            setPrevMeal(category)
            category.category?.let { loadMeals(it) }
        }

        binding.rvMeals.apply {
            layoutManager = LinearLayoutManager(this@DashboardActivity)
            hasFixedSize()
            adapter = mealsAdapter
        }

        binding.rvCategories.apply {
            layoutManager = LinearLayoutManager(this@DashboardActivity, LinearLayoutManager.HORIZONTAL, false)
            hasFixedSize()
            adapter = categoriesAdapter
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater : MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_dashboard, menu)
        return true
    }

    private fun loadMeals(category: String){
        viewModel.getAllMeals(category).observe(this){meals ->
            when(meals){
                is Resource.Loading -> {}
                is Resource.Success -> {
                    if (meals.data != null){
                        mealsAdapter.setData(meals.data!!)
                    }
                }
                is Resource.Error -> {}
            }
        }
    }

    private fun setPrevMeal(category : Category){
        Glide.with(this)
            .load(category.thumb)
            .into(binding.categoryThumb)

        binding.categoryName.text = category.category
        binding.categoryDesc.text = category.description
    }
}