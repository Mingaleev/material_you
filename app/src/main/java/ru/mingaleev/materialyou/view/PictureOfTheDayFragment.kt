package ru.mingaleev.materialyou.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.google.android.material.bottomsheet.BottomSheetBehavior
import ru.mingaleev.materialyou.R
import ru.mingaleev.materialyou.databinding.FragmentPictureOfTheDayBinding
import ru.mingaleev.materialyou.utils.showToast
import ru.mingaleev.materialyou.viewmodel.PictureOfTheDayData
import ru.mingaleev.materialyou.viewmodel.PictureOfTheDayViewModel

class PictureOfTheDayFragment : Fragment() {
    private var _binding: FragmentPictureOfTheDayBinding? = null
    private val binding get() = _binding!!

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    //Ленивая инициализация модели
    private val viewModel: PictureOfTheDayViewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(PictureOfTheDayViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        viewModel.getData()
            .observe(viewLifecycleOwner) { renderData(it) }
        _binding = FragmentPictureOfTheDayBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.inputLayout.setEndIconOnClickListener {
            setBottomSheetBehavior(view.findViewById(R.id.bottom_sheet_container))
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
                    context?.let { showToast("Link is empty", it) }
                } else {
                    binding.imageView.load(url) {
                        lifecycle(this@PictureOfTheDayFragment)
                        error (R.drawable.ic_load_error_vector)
                        placeholder (R.drawable.ic_no_photo_vector)
                        crossfade (true)
                    }

                }
            }
            is PictureOfTheDayData.Loading -> {
                //Отобразите загрузку
                //showLoading()
            }
            is PictureOfTheDayData.Error -> {
                context?.let { showToast(data.error.message, it) }
            }
        }
    }

    private fun setBottomSheetBehavior(bottomSheet: ConstraintLayout) {
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = PictureOfTheDayFragment()
    }
}