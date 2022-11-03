package ru.mingaleev.materialyou.view.behaviors

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.widget.NestedScrollView
import ru.mingaleev.materialyou.R

class TextViewTitleBehavior(context: Context, attrs: AttributeSet? = null) : CoordinatorLayout.Behavior<TextView>(context, attrs) {

    override fun layoutDependsOn(parent: CoordinatorLayout, child: TextView, dependency: View): Boolean {
        return (dependency.id == R.id.appBar)
    }

    override fun onDependentViewChanged(parent: CoordinatorLayout, child: TextView, dependency: View): Boolean {
        if (dependency.id == R.id.appBar) {
            child.y = (dependency.y + dependency.height) / 2
            child.x = (dependency.width - child.width)/2.toFloat()
            child.textSize = (dependency.y + dependency.height) / dependency.height * 34
        }
        return super.onDependentViewChanged(parent, child, dependency)
    }
}