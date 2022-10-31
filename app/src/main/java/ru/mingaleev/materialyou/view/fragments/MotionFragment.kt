package ru.mingaleev.materialyou.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.mingaleev.materialyou.databinding.FragmentConstraintBinding
import ru.mingaleev.materialyou.databinding.FragmentCoordinatorBinding
import ru.mingaleev.materialyou.databinding.FragmentMotionStartBinding

class MotionFragment : Fragment() {

    private var _binding: FragmentMotionStartBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMotionStartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = MotionFragment()
    }
}
