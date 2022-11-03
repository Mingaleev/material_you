package ru.mingaleev.materialyou.view.behaviors

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import androidx.coordinatorlayout.widget.CoordinatorLayout
import ru.mingaleev.materialyou.R
import java.lang.Math.abs

class MyBehaviorButtonOne(context: Context, attrs: AttributeSet? = null) : CoordinatorLayout.Behavior<Button>(context, attrs) {

    override fun layoutDependsOn(parent: CoordinatorLayout, child: Button, dependency: View): Boolean {
        return (dependency.id == R.id.appBar)
    }

    override fun onDependentViewChanged(parent: CoordinatorLayout, child: Button, dependency: View): Boolean {
        if (dependency.id == R.id.appBar) {
            child.y = dependency.y + dependency.height - child.height / 2
            child.x = dependency.width - child.width.toFloat()
            child.alpha = 1 - (abs(dependency.y) / (dependency.height/2))
        }
        return super.onDependentViewChanged(parent, child, dependency)
    }
}