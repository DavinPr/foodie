package com.app.foodie.dashboard

import android.app.ActivityOptions
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.core.data.Resource
import com.app.core.domain.usecase.model.Category
import com.app.foodie.R
import com.app.foodie.dashboard.categoriesdesc.CategoriesDescActivity
import com.app.foodie.databinding.ActivityDashboardBinding
import com.bumptech.glide.Glide
import org.koin.android.viewmodel.ext.android.viewModel

class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding
    private val viewModel: DashboardViewModel by viewModel()
    private lateinit var mealsAdapter: MealsListAdapter
    private var category: Category? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "Foodie"

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
                            this@DashboardActivity.category = this

                        }
                    }
                }
                is Resource.Error -> {
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                }
            }
        }


        categoriesAdapter.onClickItem = { item ->
            setPrevMeal(item)
            item.category?.let { loadMeals(it) }
            this.category = item
        }

        binding.rvMeals.apply {
            layoutManager = LinearLayoutManager(this@DashboardActivity)
            hasFixedSize()
            adapter = mealsAdapter
        }

        binding.rvCategories.apply {
            layoutManager =
                LinearLayoutManager(this@DashboardActivity, LinearLayoutManager.HORIZONTAL, false)
            hasFixedSize()
            adapter = categoriesAdapter
        }

        binding.categoriesDescContainer.setOnClickListener {
            val intent = Intent(this, CategoriesDescActivity::class.java)
            category?.let {
                intent.putExtra(CategoriesDescActivity.name, it.category)
                intent.putExtra(CategoriesDescActivity.thumb, it.thumb)
                intent.putExtra(CategoriesDescActivity.desc, it.description)

            }

            startActivity(
                intent,
                ActivityOptions.makeSceneTransitionAnimation(
                    this,
                    binding.categoryThumb, resources.getString(R.string.categories_thumb)
                ).toBundle()
            )
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_dashboard, menu)

        if (menu != null) {
            val favorite: Drawable = menu.findItem(R.id.menu_favorite).icon
            favorite.mutate()
            favorite.colorFilter = BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
                ContextCompat.getColor(this, R.color.tomato_red),
                BlendModeCompat.SRC_ATOP
            )
        }
        return true
    }

    private fun loadMeals(category: String) {
        viewModel.getAllMeals(category).observe(this) { meals ->
            when (meals) {
                is Resource.Loading -> {
                }
                is Resource.Success -> {
                    if (meals.data != null) {
                        mealsAdapter.setData(meals.data!!)
                        mealsAdapter.setCategory(category)
                    }
                }
                is Resource.Error -> {
                }
            }
        }
    }

    private fun setPrevMeal(category: Category) {
        Glide.with(this)
            .load(category.thumb)
            .into(binding.categoryThumb)

        binding.categoryName.text = category.category
        binding.categoryDesc.text = category.description
    }
}