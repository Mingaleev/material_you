package ru.mingaleev.materialyou.view

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.mingaleev.materialyou.databinding.FragmentEarthBinding

class EarthFragment: Fragment() {

    private var _binding: FragmentEarthBinding? = null
    private val binding get() = _binding!!

    private var isFlag = false
    private val duration = 500L

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEarthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fab.setOnClickListener() {
            isFlag = !isFlag

            if (isFlag) {
                ObjectAnimator.ofFloat(binding.plusImageview, View.ROTATION, 0f, 315f).setDuration(duration).start()
                ObjectAnimator.ofFloat(binding.optionOneContainer, View.TRANSLATION_Y, -150f).setDuration(duration)
                    .start()
                ObjectAnimator.ofFloat(binding.optionTwoContainer, View.TRANSLATION_Y, -270f).setDuration(duration)
                    .start()
                ObjectAnimator.ofFloat(binding.transitionBackground, View.ALPHA, 0.9f).setDuration(duration).start()

                binding.optionOneContainer.animate().alpha(1f).setDuration(duration).setListener(
                    object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator?) {
                            binding.optionOneContainer.isClickable = true
                        }
                    }
                )
                binding.optionTwoContainer.animate().alpha(1f).setDuration(duration).setListener(
                    object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator?) {
                            binding.optionTwoContainer.isClickable = true
                        }
                    }
                )

            } else {
                ObjectAnimator.ofFloat(binding.plusImageview, View.ROTATION, 315f, 0f).setDuration(duration).start()
                ObjectAnimator.ofFloat(binding.optionOneContainer, View.TRANSLATION_Y, 0f).setDuration(duration).start()
                ObjectAnimator.ofFloat(binding.optionTwoContainer, View.TRANSLATION_Y, 0f).setDuration(duration).start()
                ObjectAnimator.ofFloat(binding.transitionBackground, View.ALPHA, 0f).setDuration(duration).start()

                binding.optionOneContainer.animate().alpha(0f).setDuration(duration).setListener(
                    object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator?) {
                            binding.optionOneContainer.isClickable = false
                        }
                    }
                )
                binding.optionTwoContainer.animate().alpha(0f).setDuration(duration).setListener(
                    object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator?) {
                            binding.optionTwoContainer.isClickable = false
                        }
                    }
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}