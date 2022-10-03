package ru.mingaleev.materialyou

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.mingaleev.materialyou.databinding.ActivityMainBinding
import ru.mingaleev.materialyou.view.PictureOfTheDayFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView (binding.root)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, PictureOfTheDayFragment.newInstance())
                .commitNow()
        }
    }
}