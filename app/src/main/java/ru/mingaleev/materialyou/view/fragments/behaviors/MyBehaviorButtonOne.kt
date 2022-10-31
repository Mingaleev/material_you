package ru.mingaleev.materialyou.view.fragments.behaviors

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import androidx.coordinatorlayout.widget.CoordinatorLayout
import ru.mingaleev.materialyou.R

class MyBehaviorButtonOne(context: Context, attrs: AttributeSet? = null) : CoordinatorLayout.Behavior<Button>(context, attrs) {

    override fun layoutDependsOn(parent: CoordinatorLayout, child: Button, dependency: View): Boolean {
        return (dependency.id == R.id.bottomSheetContainer)
    }

    override fun onDependentViewChanged(parent: CoordinatorLayout, child: Button, dependency: View): Boolean {
        if (dependency.id == R.id.bottomSheetContainer) {
            child.y = dependency.y - 150
        }
        return super.onDependentViewChanged(parent, child, dependency)
    }
}