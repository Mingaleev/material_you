package ru.mingaleev.materialyou

import android.animation.ObjectAnimator
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.view.ViewTreeObserver
import android.view.animation.AnticipateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import androidx.fragment.app.Fragment
import ru.mingaleev.materialyou.databinding.ActivityMainBinding
import ru.mingaleev.materialyou.utils.COUNT_DOWN_INTERVAL
import ru.mingaleev.materialyou.utils.MILLIS_IN_FUTURE
import ru.mingaleev.materialyou.view.AnimationFragment
import ru.mingaleev.materialyou.view.CoordinatorFragment
import ru.mingaleev.materialyou.view.EarthFragment
import ru.mingaleev.materialyou.view.picture.ViewPagerFragment
import ru.mingaleev.materialyou.view.recycler.RecyclerFragment

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
                R.id.bottom_view_animation_image -> {
                    navigateTo(RecyclerFragment()); true
                }
                R.id.bottom_view_constraint -> {
                    navigateTo(CoordinatorFragment()); true
                }
                R.id.bottom_view_animation -> {
                    navigateTo(AnimationFragment()); true
                }
                else -> true
            }
        }
        binding.bottomNavigationView.selectedItemId = R.id.bottom_view_pictures

        val badge = binding.bottomNavigationView.getOrCreateBadge(R.id.bottom_view_pictures)
        badge.number = 3

        setSplashScreen()
    }

    private fun setSplashScreen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            var isHideSplashScreen = false

            object : CountDownTimer(MILLIS_IN_FUTURE, COUNT_DOWN_INTERVAL) {
                override fun onTick(millisUntilFinished: Long) {
                    //Nothing to do
                }

                override fun onFinish() {
                    isHideSplashScreen = true
                }
            }.start()

            val content: View = findViewById(android.R.id.content)
            content.viewTreeObserver.addOnPreDrawListener(
                object : ViewTreeObserver.OnPreDrawListener {
                    override fun onPreDraw(): Boolean {
                        return if (isHideSplashScreen) {
                            content.viewTreeObserver.removeOnPreDrawListener(this)
                            true
                        } else {
                            false
                        }
                    }
                }
            )

            splashScreen.setOnExitAnimationListener { splashScreenView ->
                val slideLeft = ObjectAnimator.ofFloat(
                    splashScreenView, View.TRANSLATION_X, 0f, -splashScreenView.height.toFloat()
                )
                slideLeft.interpolator = AnticipateInterpolator()
                slideLeft.duration = 1000L

                slideLeft.doOnEnd { splashScreenView.remove() }
                slideLeft.start()
            }
        }
    }

    private fun navigateTo(fragment: Fragment) {
        val commit = supportFragmentManager.beginTransaction().setCustomAnimations(
            R.anim.slide_in,
            R.anim.fade_out
        ).replace(R.id.container, fragment).commit()
    }
}