package ru.mingaleev.materialyou.view.picture

import android.content.Intent
import android.graphics.Typeface
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.BulletSpan
import android.text.style.ForegroundColorSpan
import android.text.style.TypefaceSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.provider.FontRequest
import androidx.core.provider.FontsContractCompat
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
    lateinit var spannableStringTitle: SpannableString

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
                    spannableStringTitle = SpannableString(serverResponseData.title)
                    rainbow()
                }

                if (serverResponseData.explanation.isNullOrEmpty()){
                    toast(getString(R.string.error_no_explanation))
                } else {
                    binding.explanation.text = serverResponseData.explanation
                    binding.explanation.typeface = Typeface.createFromAsset(activity?.assets, "font/roboto_slab.ttf")

                    val text = "My text \nbullet one \nbullet two \nbullet oneasf \nbullet two \nbullet oneasfscc " +
                            "\nbullet two \nbullet oneaf \nbullet twoasf \nbullet one \nbullet twofgerfds"

                    setSpanForExplanation(text)
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

    private fun rainbow () {
        binding.title.setText(spannableStringTitle, TextView.BufferType.SPANNABLE)
        spannableStringTitle = binding.title.text as SpannableString

        var currentCount = 0
        val x = object : CountDownTimer(2000, 200) {
            override fun onTick(millisUntilFinished: Long) {
                setSpanForTitle(currentCount)
                currentCount = if (++currentCount > 6) 0 else currentCount
            }

            override fun onFinish() {
                rainbow()
            }
        }
        x.start()
    }

    private fun setSpanForTitle (firstColor: Int) {
        val rainbowColors = arrayListOf(
            resources.getColor(R.color.rainbow_red),
            resources.getColor(R.color.rainbow_orange),
            resources.getColor(R.color.rainbow_yellow),
            resources.getColor(R.color.rainbow_green),
            resources.getColor(R.color.rainbow_blue),
            resources.getColor(R.color.rainbow_indigo),
            resources.getColor(R.color.rainbow_violet)
        )

        val spans = spannableStringTitle
            .getSpans(0, spannableStringTitle.length, ForegroundColorSpan::class.java)
        for (span in spans) {
            spannableStringTitle.removeSpan(span)
        }

        var colorNumber = firstColor
        for (i in spannableStringTitle.indices) {
            if (colorNumber > 5) colorNumber = 0 else colorNumber += 1
            spannableStringTitle.setSpan(ForegroundColorSpan(rainbowColors[colorNumber]),
                i, i + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
    }

    private fun setSpanForExplanation (text: String) {
        val spanned: Spanned
        val spannableString: SpannableString
        var spannableStringBuilder: SpannableStringBuilder

        spannableStringBuilder = SpannableStringBuilder(text)
        binding.explanation.setText(spannableStringBuilder, TextView.BufferType.EDITABLE)
        spannableStringBuilder = binding.explanation.text as SpannableStringBuilder

        val result = text.indexesOf("\n")
        var current = result.first()

        result.forEach {
            if (current != it) {
                spannableStringBuilder.setSpan(BulletSpan(20, resources.getColor(R.color.colorAccent)),
                    current + 1, it, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
            current = it
        }
        spannableStringBuilder.setSpan(BulletSpan(20, resources.getColor(R.color.colorAccent)),
            result.last() + 1, spannableStringBuilder.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        val colorSpan = ForegroundColorSpan(resources.getColor(R.color.colorAccent))
        spannableStringBuilder.setSpan(colorSpan, 9, 20, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        for (i in spannableStringBuilder.indices) {
            if (spannableStringBuilder[i] == 't') {
                spannableStringBuilder.setSpan(ForegroundColorSpan(
                    resources.getColor(R.color.colorAccent)),
                    i, i + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
        }

        val fontRequest = FontRequest("com.google.android.gms.fonts", "com.google.android.gms",
        "Cabin Sketch", R.array.com_google_android_gms_fonts_certs)

        val callbackFontRequest = object : FontsContractCompat.FontRequestCallback() {
            @RequiresApi(Build.VERSION_CODES.P)
            override fun onTypefaceRetrieved(typeface: Typeface?) {
                typeface?.let {
                    spannableStringBuilder.setSpan(
                        TypefaceSpan(it), 0, spannableStringBuilder.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                }
                super.onTypefaceRetrieved(typeface)
            }
        }

        this.context?.let { FontsContractCompat
            .requestFont(it, fontRequest, callbackFontRequest, Handler(Looper.getMainLooper())) }
    }

    private fun String.indexesOf(substr: String, ignoreCase: Boolean = true): List<Int> =
        (if (ignoreCase) Regex(substr, RegexOption.IGNORE_CASE) else Regex(substr))
            .findAll(this).map { it.range.first }.toList()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(date: String) = PictureOfTheDayFragment(date)
    }
}