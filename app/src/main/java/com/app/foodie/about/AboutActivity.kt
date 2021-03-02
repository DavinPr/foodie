package com.app.foodie.about

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.foodie.R
import com.app.foodie.barSetup
import com.app.foodie.databinding.ActivityAboutBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import jp.wasabeef.glide.transformations.GrayscaleTransformation

class AboutActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        barSetup(window)

        Glide.with(this)
            .load(R.drawable.profile)
            .apply(RequestOptions.bitmapTransform(GrayscaleTransformation()))
            .into(binding.aboutProfile)

    }
}