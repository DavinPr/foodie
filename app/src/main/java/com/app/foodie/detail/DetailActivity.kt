package com.app.foodie.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.app.core.data.Resource
import com.app.core.domain.usecase.model.Detail
import com.app.foodie.R
import com.app.foodie.databinding.ActivityDetailBinding
import com.bumptech.glide.Glide
import org.koin.android.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {

    companion object {
        const val mealId = "meal_id"
    }

    private lateinit var binding : ActivityDetailBinding
    private val viewModel : DetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getStringExtra(mealId)

        if (id != null) {
            viewModel.getDetailMeal(id).observe(this){detail ->
                when(detail){
                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        val data = detail.data
                        if (data != null){
                            setData(data)
                        }
                    }
                    is Resource.Error -> {}
                }
            }
        }

        binding.btnFavorite.setOnClickListener {
            binding.btnFavorite.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(this, R.drawable.ic_favorite), null, null, null)
        }
    }

    private fun setData(detail : Detail){
        binding.detailVideo.apply {
            text = detail.video?.let { text.toString().append(it) }
        }
        binding.detailName.apply {
            text = detail.name?.let { text.toString().append(it) }
        }
        binding.detailArea.apply {
            text = detail.area?.let { text.toString().append(it) }
        }
        binding.detailCategory.apply {
            text = detail.category?.let { text.toString().append(it) }
        }
        binding.detailInstruction.apply {
            text = detail.instructions?.let { text.toString().append(it) }
        }
        binding.detailTags.apply {
            text = detail.tags?.let { text.toString().append(it) }
        }

        Glide.with(this)
            .load(detail.thumb)
            .into(binding.detailThumb)
    }

    private fun String.append(text : String) = StringBuilder(this).append(" : $text").toString()
}