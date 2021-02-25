package com.app.foodie.dashboard.categoriesdesc

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.app.foodie.databinding.ActivityCategoriesDescBinding
import com.bumptech.glide.Glide

class CategoriesDescActivity : AppCompatActivity() {

    companion object {
        const val thumb = "thumb"
        const val name = "name"
        const val desc = "desc"
    }

    private lateinit var binding: ActivityCategoriesDescBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoriesDescBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val categoryName = intent.getStringExtra(name)
        val categoryThumb = intent.getStringExtra(thumb)
        val categoryDesc = intent.getStringExtra(desc)
        

        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = categoryName

        binding.categoryDesc.text = categoryDesc
        Glide.with(this)
            .load(categoryThumb)
            .into(binding.categoryThumb)
    }
}