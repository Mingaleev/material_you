package ru.mingaleev.materialyou.view.picture

import android.content.Intent
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.BulletSpan
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.transition.TransitionManager
import coil.load
import ru.mingaleev.materialyou.R
import ru.mingaleev.materialyou.databinding.FragmentPictureOfTheDayBinding
import ru.mingaleev.materialyou.utils.toast
import ru.mingaleev.materialyou.viewmodel.PictureOfTheDayData
import ru.mingaleev.materialyou.viewmodel.PictureOfTheDayViewModel

class PictureOfTheDayFragment (date: String) : Fragment() {
    private var _binding: FragmentPictureOfTheDayBinding? = null
    private val binding get() = _binding!!
    private val setDate = date

    //Ленивая инициализация модели
    private val viewModel: PictureOfTheDayViewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(PictureOfTheDayViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        viewModel.getData(setDate)
            .observe(viewLifecycleOwner) { renderData(it) }
        _binding = FragmentPictureOfTheDayBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.inputLayout.setEndIconOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("https://ru.wikipedia.org/wiki/${binding.inputEditText.text}")
            })
        }
    }

    private fun renderData(data: PictureOfTheDayData) {
        when (data) {
            is PictureOfTheDayData.Success -> {
                val serverResponseData = data.serverResponseData
                val url = serverResponseData.url
                if (url.isNullOrEmpty()) {
                    toast("Link is empty")
                } else {
                    binding.imageView.load(url) {
                        lifecycle(this@PictureOfTheDayFragment)
                        error(R.drawable.ic_load_error_vector)
                        placeholder(R.drawable.ic_no_photo_vector)
                        crossfade(true)
                    }
                    TransitionManager.beginDelayedTransition(binding.root)
                    binding.imageView.visibility = View.VISIBLE
                }
                if (serverResponseData.title.isNullOrEmpty()){
                    toast(getString(R.string.error_no_title))
                } else {
                    binding.title.text = serverResponseData.title
                }

                if (serverResponseData.explanation.isNullOrEmpty()){
                    toast(getString(R.string.error_no_explanation))
                } else {
                    binding.explanation.text = serverResponseData.explanation
                    binding.explanation.typeface = Typeface.createFromAsset(activity?.assets, "font/roboto_slab.ttf")

                    val spanned: Spanned
                    val spannableString: SpannableString
                    val spannableStringBuilder: SpannableStringBuilder

                    val text = "My text \nbullet one \nbullet two"
                    spannableString = SpannableString(text)

                    val bulletSpanOne = BulletSpan(20, resources.getColor(R.color.colorAccent))
                    val bulletSpanTwo = BulletSpan(20, resources.getColor(R.color.colorAccent))
                    val colorSpan = ForegroundColorSpan(resources.getColor(R.color.colorAccent))

                    spannableString.setSpan(colorSpan, 9, 20, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

                    spannableString.setSpan(
                        bulletSpanOne, 9, 20, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )

                    spannableString.setSpan(
                        bulletSpanTwo, 21, spannableString.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )

                    for (i in text.indices) {
                        if (text[i] == 't') {
                            spannableString.setSpan(
                                resources.getColor(R.color.colorAccent),
                                i, i + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                            )
                        }
                    }
                    binding.explanation.text = spannableString
                }
            }
            is PictureOfTheDayData.Loading -> {
                //Отобразите загрузку
            }
            is PictureOfTheDayData.Error -> {
                toast(data.error.message)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(date: String) = PictureOfTheDayFragment(date)
    }
}