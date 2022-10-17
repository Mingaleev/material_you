package ru.mingaleev.materialyou

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import ru.mingaleev.materialyou.databinding.ActivityMainBinding
import ru.mingaleev.materialyou.utils.KEY_APP_THEME
import ru.mingaleev.materialyou.view.navigation.EarthFragment
import ru.mingaleev.materialyou.view.navigation.MarsFragment
import ru.mingaleev.materialyou.view.navigation.ViewPagerFragment
import ru.mingaleev.materialyou.view.settings.SettingsFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        val sharedPref = getPreferences(Context.MODE_PRIVATE)
        val themeApp = sharedPref?.getString(KEY_APP_THEME, "")
        if (themeApp == "Pink") {
            setTheme(R.style.PinkTheme)
        }
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