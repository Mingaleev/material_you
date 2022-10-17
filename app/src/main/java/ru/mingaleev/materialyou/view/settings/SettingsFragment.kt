package ru.mingaleev.materialyou.view.settings

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.mingaleev.materialyou.R
import ru.mingaleev.materialyou.databinding.FragmentSettingsBinding
import ru.mingaleev.materialyou.utils.KEY_APP_THEME
import ru.mingaleev.materialyou.utils.NAME_THEME_INDIGO
import ru.mingaleev.materialyou.utils.NAME_THEME_PINK

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
        val editor = sharedPref?.edit()

        when (sharedPref?.getString(KEY_APP_THEME, "")) {
            NAME_THEME_PINK -> binding.chipPink.isChecked = true
            NAME_THEME_INDIGO -> binding.chipIndigo.isChecked = true
        }

        binding.chipIndigo.setOnClickListener {
            binding.chipPink.isChecked = false
            activity?.setTheme(R.style.IndigoTheme)
            editor?.putString(KEY_APP_THEME, NAME_THEME_INDIGO)?.commit()
            activity?.recreate()
        }

        binding.chipPink.setOnClickListener {
            binding.chipIndigo.isChecked = false
            activity?.setTheme(R.style.PinkTheme)
            editor?.putString(KEY_APP_THEME, NAME_THEME_PINK)?.commit()
            activity?.recreate()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = SettingsFragment()
    }
}
