package ru.mingaleev.materialyou.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.transition.ChangeBounds
import androidx.transition.Fade
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet
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
            val fade = Fade()
            val changeBounds = ChangeBounds()

            myAutoTransition.addTransition(fade)
            myAutoTransition.addTransition(changeBounds)

            TransitionManager.beginDelayedTransition(binding.transitionContainer, myAutoTransition)
            binding.textViewAnimation.visibility = if (flagVisibilityTextView) View.VISIBLE else View.GONE
        }
    }
}