package com.app.foodie.about

import android.animation.ObjectAnimator
import android.content.Intent
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.app.foodie.databinding.ActivityAboutBinding


class AboutActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val matrix = ColorMatrix()
        matrix.setSaturation(0f)
        binding.aboutProfile.colorFilter = ColorMatrixColorFilter(matrix)

        binding.cardContainer.apply {
            ObjectAnimator.ofFloat(this, "translationY", 400f, -50f,  0f).apply {
                duration = 1000
                start()
            }
        }

        binding.btnGithub.setOnClickListener(this)
        binding.btnInstagram.setOnClickListener(this)
        binding.btnLinkedin.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            binding.btnGithub.id -> {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/DavinPr"))
                startActivity(intent)
            }
            binding.btnInstagram.id -> {
                val intent =
                    Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/aditya_davin/"))
                startActivity(intent)
            }
            binding.btnLinkedin.id -> {
                val intent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://www.linkedin.com/in/aditya-davin-pradana-148453197/")
                )
                startActivity(intent)
            }
        }
    }
}