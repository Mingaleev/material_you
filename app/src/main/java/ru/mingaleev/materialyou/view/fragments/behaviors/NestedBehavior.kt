package ru.mingaleev.materialyou.view.fragments.behaviors

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.widget.NestedScrollView
import ru.mingaleev.materialyou.R

class NestedBehavior(context: Context, attrs: AttributeSet? = null) : CoordinatorLayout.Behavior<NestedScrollView>(context, attrs) {

    override fun layoutDependsOn(parent: CoordinatorLayout, child: NestedScrollView, dependency: View): Boolean {
        return (dependency.id == R.id.appBar)
    }

    override fun onDependentViewChanged(parent: CoordinatorLayout, child: NestedScrollView, dependency: View): Boolean {
        if (dependency.id == R.id.appBar) {
            child.y = dependency.y + dependency.height
        }
        return super.onDependentViewChanged(parent, child, dependency)
    }
}