package ru.mingaleev.materialyou

import android.animation.Animator
import android.content.Intent
import android.os.Bundle
import android.view.animation.LinearInterpolator
import androidx.appcompat.app.AppCompatActivity
import ru.mingaleev.materialyou.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    private val rotateValue = 750f
    private val interpolatorDuration = 3000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imageView.animate().rotationBy(rotateValue).setInterpolator(LinearInterpolator())
            .setDuration(interpolatorDuration).setListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator?) {
                    //Nothing to do
                }

                override fun onAnimationEnd(animation: Animator?) {
                    startActivity(Intent(applicationContext, MainActivity::class.java))
                    finish()
                }

                override fun onAnimationCancel(animation: Animator?) {
                    //Nothing to do
                }

                override fun onAnimationRepeat(animation: Animator?) {
                    //Nothing to do
                }
            })
    }
}