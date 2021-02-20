package com.app.foodie.dashboard

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.app.core.data.Resource
import com.app.core.domain.usecase.model.Category
import com.app.foodie.R
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

        val items = listOf("abc", "def", "ghi")
        val adapter = ArrayAdapter(this, R.layout.list_menu, items)
        binding.toolbarContent.menuExpand.apply {
            setText(adapter.getItem(0).toString(), false)
            setAdapter(adapter)
        }

        viewModel.getAllCategories().observe(this) { category ->
            when (category) {
                is Resource.Loading -> {
                    binding.tvTest.text = "loading"
                }
                is Resource.Success -> {
                    if (category.data != null) {
//                        val items = category.data!!.map { c -> c.category }.toCollection(
//                            arrayListOf()
//                        )
//                        val adapter = ArrayAdapter(this, R.layout.list_menu, items)
//                        binding.toolbarContent.menuExpand.apply {
//                            setAdapter(adapter)
//                            setText(adapter.getItem(1).toString(), false)
//                        }

                    }
                }
                is Resource.Error -> {
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}