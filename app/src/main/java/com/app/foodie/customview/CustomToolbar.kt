package com.app.foodie.customview

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.WindowInsets
import androidx.appcompat.widget.Toolbar
import androidx.core.view.size


class CustomToolbar : Toolbar {

    companion object {
        var heightSize: Int = 0
    }

    constructor(context: Context) : super(context) {
        init()
        postDelayed({
            changePadding(heightSize)
        }, 0)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
        postDelayed({
            changePadding(heightSize)
        }, 0)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
        postDelayed({
            changePadding(heightSize)
        }, 0)
    }

    private fun init() {
        if (heightSize != 0) {
            return
        }

        // listen to get the height
        (context as? Activity)?.window?.decorView?.setOnApplyWindowInsetsListener { _, windowInsets ->

            // get the size

            heightSize = if (android.os.Build.VERSION.SDK_INT >= 30) {
                windowInsets.getInsets(WindowInsets.Type.statusBars()).top
            } else {
                windowInsets.systemWindowInsetTop
            }

            // return insets
            windowInsets
        }
    }

    private fun changePadding(height: Int) {
        val lp = this.layoutParams
        lp.height = height + this.height
        this.layoutParams = lp
    }
}