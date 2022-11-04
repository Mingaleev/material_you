package ru.mingaleev.materialyou.view

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.transition.ArcMotion
import androidx.transition.ChangeBounds
import androidx.transition.TransitionManager
import ru.mingaleev.materialyou.databinding.FragmentAnimationBinding

class AnimationFragment : Fragment() {

    private var _binding: FragmentAnimationBinding? = null
    private val binding get() = _binding!!

    private var isFlag = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAnimationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.button.setOnClickListener() {
            isFlag = !isFlag

            val params = it.layoutParams as FrameLayout.LayoutParams

            val changeBounds = ChangeBounds()
            changeBounds.duration = 1000L
            changeBounds.setPathMotion(ArcMotion())
            TransitionManager.beginDelayedTransition(binding.root, changeBounds)
            if (isFlag) {
                params.gravity = Gravity.BOTTOM or Gravity.END
            } else {
                params.gravity = Gravity.TOP or Gravity.START
            }
            binding.button.layoutParams = params
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}