package ru.mingaleev.materialyou.view

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
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
            animation()
        }
    }

    private fun animation() {
        isFlag = !isFlag
        if (isFlag) {
            ObjectAnimator.ofFloat(binding.plusImageview, View.ROTATION, 0f, 315f).setDuration(duration).start()
            ObjectAnimator.ofFloat(binding.optionOneContainer, View.TRANSLATION_Y, -150f).setDuration(duration)
                .start()
            ObjectAnimator.ofFloat(binding.optionTwoContainer, View.TRANSLATION_Y, -270f).setDuration(duration)
                .start()
            ObjectAnimator.ofFloat(binding.transitionBackground, View.ALPHA, 0.9f).setDuration(duration).start()

            setOptionContainer(1f, true, binding.optionOneContainer)
            setOptionContainer(1f, true, binding.optionTwoContainer)

        } else {
            ObjectAnimator.ofFloat(binding.plusImageview, View.ROTATION, 315f, 0f).setDuration(duration).start()
            ObjectAnimator.ofFloat(binding.optionOneContainer, View.TRANSLATION_Y, 0f).setDuration(duration).start()
            ObjectAnimator.ofFloat(binding.optionTwoContainer, View.TRANSLATION_Y, 0f).setDuration(duration).start()
            ObjectAnimator.ofFloat(binding.transitionBackground, View.ALPHA, 0f).setDuration(duration).start()

            setOptionContainer(0f, false, binding.optionOneContainer)
            setOptionContainer(0f, false, binding.optionTwoContainer)
        }
    }

    private fun setOptionContainer(alpha: Float, clickable: Boolean, linearLayout: LinearLayout ) {
        linearLayout.animate().alpha(alpha).setDuration(duration).setListener(
            object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator? ) {
                        linearLayout.isClickable = clickable
                }
            }
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}