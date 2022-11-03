package ru.mingaleev.materialyou

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import ru.mingaleev.materialyou.databinding.ActivityMainBinding
import ru.mingaleev.materialyou.view.*
import ru.mingaleev.materialyou.view.picture.ViewPagerFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.bottom_view_pictures -> {
                    navigateTo(ViewPagerFragment()); true
                }
                R.id.bottom_view_earth -> {
                    navigateTo(EarthFragment()); true
                }
                R.id.bottom_view_mars -> {
                    navigateTo(MarsFragment()); true
                }
                R.id.bottom_view_constraint -> {
                    navigateTo(CoordinatorFragment()); true
                }
                R.id.bottom_view_motion -> {
                    navigateTo(AnimationFragment()); true
                }
                else -> true
            }
        }
        binding.bottomNavigationView.selectedItemId = R.id.bottom_view_pictures

        val badge = binding.bottomNavigationView.getOrCreateBadge(R.id.bottom_view_pictures)
        badge.number = 3
    }

    private fun navigateTo(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.container, fragment).commit()
    }
}