package ru.mingaleev.materialyou.util

import android.content.Context
import android.view.Gravity
import android.widget.Toast

fun showToast(string: String?, context: Context) {
    Toast.makeText(context, string, Toast.LENGTH_SHORT).apply {
        setGravity(Gravity.BOTTOM, 0, 250)
        show()
    }
}