package ru.mingaleev.materialyou.view

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.view.ViewCompat
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

        val title: MutableList<String> = ArrayList()
        for (i in 0..4) {
            title.add("Item $i")
        }

        binding.button.setOnClickListener() {
            isFlag = !isFlag
            TransitionManager.beginDelayedTransition(binding.root)
            binding.transitionContainer.removeAllViews()
            title.shuffle()
            title.forEach{
                binding.transitionContainer.addView(TextView(context).apply {
                    text = it
                    ViewCompat.setTransitionName(this, it) //Задали псевдоним
                })
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}