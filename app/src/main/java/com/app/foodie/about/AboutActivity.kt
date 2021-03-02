package com.app.foodie.about

import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.app.foodie.barSetup
import com.app.foodie.databinding.ActivityAboutBinding


class AboutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val matrix = ColorMatrix()
        matrix.setSaturation(0f)
        binding.aboutProfile.colorFilter = ColorMatrixColorFilter(matrix)
    }
}