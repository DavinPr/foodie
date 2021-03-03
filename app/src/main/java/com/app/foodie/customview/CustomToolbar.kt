package com.app.foodie.customview

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.WindowInsets
import androidx.appcompat.widget.Toolbar


class CustomToolbar : Toolbar {

    companion object {
        var heightSize: Int = 0
    }

    constructor(context: Context) : super(context) {
        init()
        postDelayed({
            applyHeight(heightSize)
        }, 0)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
        postDelayed({
            applyHeight(heightSize)
        }, 0)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
        postDelayed({
            applyHeight(heightSize)
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

    private fun applyHeight(height: Int) {
        val lp = this.layoutParams
        lp.height = height + this.height
        this.layoutParams = lp
        this.setPadding(0, height, 0, 0)
    }
}