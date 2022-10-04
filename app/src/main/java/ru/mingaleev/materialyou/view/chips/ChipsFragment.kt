package ru.mingaleev.materialyou.view.chips

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.chip.Chip
import ru.mingaleev.materialyou.databinding.FragmentChipsBinding
import ru.mingaleev.materialyou.utils.showToast

class ChipsFragment : Fragment() {

    private var _binding: FragmentChipsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChipsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.chipGroup.setOnCheckedChangeListener { chipGroup, position ->
            chipGroup.findViewById<Chip>(position)?.let { chipId ->
                context?.let { showToast("Выбран ${chipId.text}", it) }
            }
        }

        binding.chipClose.setOnCloseIconClickListener {
            context?.let { showToast("Close is Clicked", it) }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = ChipsFragment()
    }
}
