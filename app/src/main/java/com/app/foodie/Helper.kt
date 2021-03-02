package com.app.foodie

import android.os.Build
import android.view.View
import android.view.Window
import android.view.WindowInsets
import android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
import android.view.WindowManager

fun barSetup(window: Window) {
    window.apply {
        addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)

        @Suppress("DEPRECATION")
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.R -> {
                setDecorFitsSystemWindows(false)
                window.insetsController?.let {
                    it.show(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
                    it.setSystemBarsAppearance(
                        APPEARANCE_LIGHT_STATUS_BARS,
                        APPEARANCE_LIGHT_STATUS_BARS
                    )
                }
            }
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> {
                window.decorView.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
            else -> {
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            }
        }
    }
}
