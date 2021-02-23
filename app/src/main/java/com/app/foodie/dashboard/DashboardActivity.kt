package com.app.foodie.dashboard

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.core.data.Resource
import com.app.foodie.databinding.ActivityDashboardBinding
import org.koin.android.viewmodel.ext.android.viewModel

class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding
    private val viewModel: DashboardViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = null

        val adapter = CategoriesListAdapter()

        viewModel.getAllCategories().observe(this) { category ->
            when (category) {
                is Resource.Loading -> {
                }
                is Resource.Success -> {
                    if (category.data != null) {
                        adapter.setData(category.data!!)
                    }
                }
                is Resource.Error -> {
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                }
            }
        }
        binding.rvCategories.apply {
            layoutManager = LinearLayoutManager(this@DashboardActivity, LinearLayoutManager.HORIZONTAL, false)
            hasFixedSize()
            this.adapter = adapter
        }

    }
}