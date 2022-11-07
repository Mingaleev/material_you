package ru.mingaleev.materialyou.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnticipateOvershootInterpolator
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import androidx.transition.ChangeBounds
import androidx.transition.TransitionManager
import ru.mingaleev.materialyou.R
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
        val constraintSet = ConstraintSet()
        constraintSet.clone(context, R.layout.fragment_animation_start)

        binding.tap.setOnClickListener() {
            isFlag = !isFlag
            val changeBounds = ChangeBounds()
            changeBounds.duration = 1000L
            changeBounds.interpolator = AnticipateOvershootInterpolator(3.0f)
            TransitionManager.beginDelayedTransition(binding.constraintContainer, changeBounds)

            if (isFlag) {
                constraintSet.connect(R.id.title, ConstraintSet.RIGHT, R.id.backgroundImage, ConstraintSet.RIGHT)
                constraintSet.clear(R.id.description, ConstraintSet.TOP)
                constraintSet.connect(R.id.description, ConstraintSet.BOTTOM, R.id.backgroundImage, ConstraintSet.BOTTOM
                )
            } else {
                constraintSet.connect(R.id.title, ConstraintSet.RIGHT, R.id.backgroundImage, ConstraintSet.LEFT)
                constraintSet.clear(R.id.description, ConstraintSet.BOTTOM)
                constraintSet.connect(R.id.description, ConstraintSet.TOP, R.id.backgroundImage, ConstraintSet.BOTTOM)
            }
            constraintSet.applyTo(binding.constraintContainer)

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}