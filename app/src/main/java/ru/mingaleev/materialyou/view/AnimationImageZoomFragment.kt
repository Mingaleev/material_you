package ru.mingaleev.materialyou.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.transition.ChangeImageTransform
import androidx.transition.TransitionManager
import ru.mingaleev.materialyou.databinding.FragmentAnimationImageZoomBinding


class AnimationImageZoomFragment : Fragment() {

    private var _binding: FragmentAnimationImageZoomBinding? = null
    private val binding get() = _binding!!

    private var isFlag = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAnimationImageZoomBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imageViewAnimation.setOnClickListener(){
            isFlag = !isFlag

            val changeImageTransform = ChangeImageTransform()

            TransitionManager.beginDelayedTransition(binding.root, changeImageTransform)
            binding.imageViewAnimation.scaleType = if (isFlag) ImageView.ScaleType.CENTER_INSIDE else ImageView.ScaleType.CENTER_CROP
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}