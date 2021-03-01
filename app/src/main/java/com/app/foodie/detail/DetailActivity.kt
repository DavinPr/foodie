package com.app.foodie.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.app.core.data.Resource
import com.app.core.domain.usecase.model.Detail
import com.app.core.domain.usecase.model.Favorite
import com.app.foodie.R
import com.app.foodie.databinding.ActivityDetailBinding
import com.bumptech.glide.Glide
import org.koin.android.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {

    companion object {
        const val id_key = "id_key"
        const val favorite_key = "favorite_key"
        const val ACTIVITY_CODE = "activity_code"
    }

    private lateinit var binding: ActivityDetailBinding
    private val viewModel: DetailViewModel by viewModel()
    private var detailData = Detail()
    private var isFavorite = false
    private var favorite: Favorite? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val activityCode = intent.getIntExtra(ACTIVITY_CODE, 101)


        if (activityCode == 102) {
            favorite = intent.getParcelableExtra(favorite_key)
            isFavorite = true
            checkFavorite(true)
            setData(favorite = favorite)
        } else {
            val id = intent.getStringExtra(id_key)
            if (id != null) {
                viewModel.getDetailMeal(id).observe(this) { detail ->
                    when (detail) {
                        is Resource.Loading -> {
                        }
                        is Resource.Success -> {
                            val data = detail.data
                            if (data != null) {
                                setData(data)
                                detailData = data
                            }
                        }
                        is Resource.Error -> {
                        }
                    }
                }
                viewModel.checkFavorited(id).observe(this) { state ->
                    isFavorite = state
                    checkFavorite(state)
                }
            }
        }

        binding.btnFavorite.setOnClickListener {
            checkFavorite(!isFavorite)
            if (isFavorite) {
                if (activityCode == 102)
                    favorite?.let { favorite -> viewModel.insertFavorite(favorite) }
                else viewModel.insertFavorite(detailData)
            } else {
                if (activityCode == 102)
                    favorite?.let { favorite -> viewModel.deleteFavorite(favorite) }
                else viewModel.deleteFavorite(detailData)
            }
        }
    }

    private fun setData(detail: Detail? = null, favorite: Favorite? = null) {
        binding.detailVideo.apply {
            text = if (detail != null) {
                detail.video?.let { text.toString().append(it) }
            } else {
                favorite?.video?.let { text.toString().append(it) }
            }
        }
        binding.detailName.apply {
            text = if (detail != null) {
                detail.name?.let { text.toString().append(it) }
            } else {
                favorite?.name?.let { text.toString().append(it) }
            }
        }
        binding.detailArea.apply {
            text = if (detail != null) {
                detail.area?.let { text.toString().append(it) }
            } else {
                favorite?.area?.let { text.toString().append(it) }
            }
        }
        binding.detailCategory.apply {
            text = if (detail != null) {
                detail.category?.let { text.toString().append(it) }
            } else {
                favorite?.category?.let { text.toString().append(it) }
            }
        }
        binding.detailInstruction.apply {
            text = if (detail != null) {
                detail.instructions?.let { text.toString().append(it) }
            } else {
                favorite?.instructions?.let { text.toString().append(it) }
            }
        }
        binding.detailTags.apply {
            text = if (detail != null) {
                detail.tags?.let { text.toString().append(it) }
            } else {
                favorite?.tags?.let { text.toString().append(it) }
            }
        }

        Glide.with(this)
            .load(if (detail != null) detail.thumb else favorite?.thumb)
            .into(binding.detailThumb)
    }

    private fun checkFavorite(state: Boolean) {
        if (state) {
            isFavorite = true
            binding.btnFavorite.setCompoundDrawablesWithIntrinsicBounds(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_favorite
                ), null, null, null
            )
        } else {
            isFavorite = false
            binding.btnFavorite.setCompoundDrawablesWithIntrinsicBounds(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_favorite_border
                ), null, null, null
            )
        }
    }

    private fun String.append(text: String) = StringBuilder(this).append(" : $text").toString()
}