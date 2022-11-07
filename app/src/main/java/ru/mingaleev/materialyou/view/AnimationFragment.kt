package ru.mingaleev.materialyou.view

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnticipateOvershootInterpolator
import android.widget.FrameLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.transition.ArcMotion
import androidx.transition.ChangeBounds
import androidx.transition.TransitionManager
import ru.mingaleev.materialyou.R
import ru.mingaleev.materialyou.databinding.FragmentAnimationBinding
import ru.mingaleev.materialyou.databinding.FragmentAnimationStartBinding

class AnimationFragment : Fragment() {

    private var _binding: FragmentAnimationStartBinding? = null
    private val binding get() = _binding!!

    private var isFlag = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAnimationStartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val constraintSetStart = ConstraintSet()
        val constraintSetEnd = ConstraintSet()
        constraintSetStart.clone(context, R.layout.fragment_animation_start)
        constraintSetEnd.clone(context, R.layout.fragment_animation_end)

        binding.tap.setOnClickListener() {
            isFlag = !isFlag

            val changeBounds = ChangeBounds()
            changeBounds.duration = 1000L
            changeBounds.interpolator = AnticipateOvershootInterpolator(3.0f)
            TransitionManager.beginDelayedTransition(binding.constraintContainer, changeBounds)
            if (isFlag) {
                constraintSetEnd.applyTo(binding.constraintContainer)
            } else {
                constraintSetStart.applyTo(binding.constraintContainer)
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}