package ru.mingaleev.materialyou.view.constraint

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.mingaleev.materialyou.R
import ru.mingaleev.materialyou.databinding.FragmentConstraintBinding
import ru.mingaleev.materialyou.databinding.FragmentSettingsBinding
import ru.mingaleev.materialyou.utils.KEY_APP_THEME
import ru.mingaleev.materialyou.utils.NAME_THEME_INDIGO
import ru.mingaleev.materialyou.utils.NAME_THEME_PINK

class ConstraintFragment : Fragment() {

    private var _binding: FragmentConstraintBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentConstraintBinding.inflate(inflater, container, false)
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
        fun newInstance() = ConstraintFragment()
    }
}
