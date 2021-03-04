package com.app.foodie.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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
    private var video: String? = null

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
                    val loading = binding.detailLoading
                    when (detail) {
                        is Resource.Loading -> loading.root.visibility = View.VISIBLE
                        is Resource.Success -> {
                            loading.root.visibility = View.GONE
                            val data = detail.data
                            if (data != null) {
                                setData(data)
                                detailData = data
                            }
                        }
                        is Resource.Error -> {
                            loading.root.visibility = View.GONE
                            loading.loadingAnimation.visibility = View.GONE
                            loading.errorText.visibility = View.VISIBLE
                            loading.errorText.text = resources.getString(R.string.error_text)
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
                Toast.makeText(this, "Data berhasil ditambahkan !", Toast.LENGTH_SHORT).show()
            } else {
                if (activityCode == 102)
                    favorite?.let { favorite -> viewModel.deleteFavorite(favorite) }
                else viewModel.deleteFavorite(detailData)
                Toast.makeText(this, "Data berhasil dihapus !", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnVideo.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(video))
            startActivity(intent)
        }

        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun setData(detail: Detail? = null, favorite: Favorite? = null) {
        video = detail?.video ?: favorite?.video

        binding.detailName.apply {
            val newText = "Tags : ${detail?.name ?: favorite?.name}"
            text = newText
        }

        binding.detailArea.apply {
            text = detail?.area ?: favorite?.area
        }
        binding.detailCategory.apply {
            text = detail?.category ?: favorite?.category
        }
        binding.detailInstruction.apply {
            text = detail?.instructions ?: favorite?.instructions
        }
        binding.detailTags.apply {
            text = detail?.tags ?: favorite?.tags
        }

        Glide.with(this)
            .load(detail?.thumb ?: favorite?.thumb)
            .into(binding.detailThumb)
    }

    private fun checkFavorite(state: Boolean) {
        if (state) {
            isFavorite = true
            binding.btnFavorite.setIconResource(R.drawable.ic_favorite)
        } else {
            isFavorite = false
            binding.btnFavorite.setIconResource(R.drawable.ic_favorite_border)
        }
    }
}