package com.app.foodie.dashboard

import android.app.ActivityOptions
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.core.data.Resource
import com.app.core.domain.usecase.model.Category
import com.app.foodie.R
import com.app.foodie.about.AboutActivity
import com.app.foodie.dashboard.categoriesdesc.CategoriesDescActivity
import com.app.foodie.databinding.ActivityDashboardBinding
import com.app.foodie.detail.DetailActivity
import com.app.foodie.favorite.FavoriteActivity
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

        val categoriesAdapter = CategoriesListAdapter()
        mealsAdapter = MealsListAdapter()

        viewModel.getAllCategories().observe(this) { category ->
            val loading = binding.loadingCategory
            when (category) {
                is Resource.Loading -> loading.loadingAnimation.visibility = View.VISIBLE
                is Resource.Success -> {
                    if (category.data != null) {
                        loading.root.visibility = View.GONE
                        categoriesAdapter.setData(category.data!!)
                        category.data!!.first().apply {
                            setPrevMeal(this)
                            this.category?.let { loadMeals(it) }
                            this@DashboardActivity.category = this

                        }
                    } else {
                        loading.loadingAnimation.visibility = View.GONE
                        loading.errorText.visibility = View.VISIBLE
                        loading.errorText.text = resources.getString(R.string.empty_text)
                    }
                }
                is Resource.Error -> {
                    loading.loadingAnimation.visibility = View.GONE
                    loading.errorText.visibility = View.VISIBLE
                    loading.errorText.text = resources.getString(R.string.error_text)
                }
            }
        }


        categoriesAdapter.onClickItem = { item ->
            setPrevMeal(item)
            item.category?.let { loadMeals(it) }
            this.category = item
        }

        mealsAdapter.onClickItem = { id ->
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(DetailActivity.id_key, id)
            intent.putExtra(DetailActivity.ACTIVITY_CODE, 101)
            startActivity(
                intent,
                ActivityOptionsCompat.makeSceneTransitionAnimation(this).toBundle()
            )
        }

        binding.rvMeals.apply {
            layoutManager = LinearLayoutManager(this@DashboardActivity)
            hasFixedSize()
            adapter = mealsAdapter
        }

        binding.dashboardCategoryLayout.rvCategories.apply {
            layoutManager =
                LinearLayoutManager(this@DashboardActivity, LinearLayoutManager.HORIZONTAL, false)
            hasFixedSize()
            adapter = categoriesAdapter
        }

        binding.dashboardCategoryLayout.categoriesDescContainer.setOnClickListener {
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
                    binding.dashboardCategoryLayout.categoryThumb,
                    resources.getString(R.string.categories_thumb)
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_favorite -> {
                val intent = Intent(this, FavoriteActivity::class.java)
                startActivity(intent)
            }
            R.id.menu_about -> {
                val intent = Intent(this, AboutActivity::class.java)
                startActivity(intent)
            }
        }
        return true
    }

    private fun loadMeals(category: String) {
        viewModel.getAllMeals(category).observe(this) { meals ->
            val loading = binding.loadingMeals
            when (meals) {
                is Resource.Loading -> {
                    loading.loadingAnimation.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    loading.root.visibility = View.GONE
                    loading.loadingAnimation.visibility = View.GONE
                    if (meals.data != null) {
                        mealsAdapter.setData(meals.data!!)
                        mealsAdapter.setCategory(category)
                    } else {
                        loading.errorText.visibility = View.VISIBLE
                        loading.errorText.text = resources.getString(R.string.empty_text)
                    }
                }
                is Resource.Error -> {
                    loading.loadingAnimation.visibility = View.GONE
                    loading.errorText.visibility = View.VISIBLE
                    loading.errorText.text = resources.getString(R.string.error_text)
                }
            }
        }
    }

    private fun setPrevMeal(category: Category) {
        Glide.with(this)
            .load(category.thumb)
            .into(binding.dashboardCategoryLayout.categoryThumb)

        binding.dashboardCategoryLayout.categoryName.text = category.category
        binding.dashboardCategoryLayout.categoryDesc.text = category.description
    }
}