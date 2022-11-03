package ru.mingaleev.materialyou.view

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.transition.*
import ru.mingaleev.materialyou.databinding.FragmentAnimationBinding

class AnimationFragment : Fragment() {

    private var _binding: FragmentAnimationBinding? = null
    private val binding get() = _binding!!

    private var flagVisibilityTextView = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAnimationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonAnimation.setOnClickListener() {
            flagVisibilityTextView = !flagVisibilityTextView
            val myAutoTransition = TransitionSet()
            myAutoTransition.ordering = TransitionSet.ORDERING_TOGETHER
            val slide = Slide(Gravity.START)
            val changeBounds = ChangeBounds()

            myAutoTransition.duration = 800L
            myAutoTransition.addTransition(changeBounds)
            myAutoTransition.addTransition(slide)

            TransitionManager.beginDelayedTransition(binding.transitionContainer, myAutoTransition)
            binding.textViewAnimation.visibility = if (flagVisibilityTextView) View.VISIBLE else View.GONE
        }
    }
}