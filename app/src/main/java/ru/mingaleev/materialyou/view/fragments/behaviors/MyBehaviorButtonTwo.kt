package ru.mingaleev.materialyou.view.fragments.behaviors

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import androidx.coordinatorlayout.widget.CoordinatorLayout
import ru.mingaleev.materialyou.R

class MyBehaviorButtonTwo(context: Context, attrs: AttributeSet? = null) : CoordinatorLayout.Behavior<Button>(context, attrs) {

    override fun layoutDependsOn(parent: CoordinatorLayout, child: Button, dependency: View): Boolean {
        return (dependency.id == R.id.btn_one)
    }

    override fun onDependentViewChanged(parent: CoordinatorLayout, child: Button, dependency: View): Boolean {
        if (dependency.id == R.id.btn_one) {
            child.y = dependency.y
            child.x = dependency.x + child.width
        }
        return super.onDependentViewChanged(parent, child, dependency)
    }
}