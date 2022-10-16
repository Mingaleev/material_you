package ru.mingaleev.materialyou.view.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import ru.mingaleev.materialyou.databinding.FragmentViewPagerBinding


class ViewPagerFragment : Fragment() {

    private var _binding: FragmentViewPagerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentViewPagerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewPager.adapter = activity?.let { ViewPager2Adapter(it) }

        bindTabLayout()
    }

    private fun bindTabLayout() {
        TabLayoutMediator(binding.tabLayout, binding.viewPager, object : TabLayoutMediator.TabConfigurationStrategy {
            override fun onConfigureTab(tab: TabLayout.Tab, position: Int) {
                tab.text = position.toString()
                return when (position) {
                    0 -> tab.text = "Земля"
                    1 -> tab.text = "Марс"
                    2 -> tab.text = "Система"
                    else -> tab.text = "Земля"
                }
            }
        }).attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = ViewPagerFragment()
    }
}