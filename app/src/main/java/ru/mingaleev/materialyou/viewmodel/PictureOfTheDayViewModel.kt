package ru.mingaleev.materialyou.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.mingaleev.materialyou.BuildConfig
import ru.mingaleev.materialyou.data.PODRetrofitImpl
import ru.mingaleev.materialyou.data.PODServerResponseData
import ru.mingaleev.materialyou.utils.SELECT_DAY_DAY_BEFORE_YESTERDAY
import ru.mingaleev.materialyou.utils.SELECT_DAY_TODAY
import ru.mingaleev.materialyou.utils.SELECT_DAY_YESTERDAY
import java.text.SimpleDateFormat
import java.util.*

class PictureOfTheDayViewModel(
    private val liveDataForViewToObserve: MutableLiveData<PictureOfTheDayData> =
        MutableLiveData(),
    private val retrofitImpl: PODRetrofitImpl = PODRetrofitImpl()
) :
    ViewModel() {
    fun getData(day: String): LiveData<PictureOfTheDayData> {
        sendServerRequest(day)
        return liveDataForViewToObserve
    }

    private fun sendServerRequest(day: String) {
        liveDataForViewToObserve.value = PictureOfTheDayData.Loading(null)
        val apiKey: String = BuildConfig.NASA_API_KEY

        if (apiKey.isBlank()) {
            PictureOfTheDayData.Error(Throwable("You need API key"))
        } else {
            retrofitImpl.getRetrofitImpl().getPictureOfTheDayByDate(apiKey, getDate(day)).enqueue(object :
                Callback<PODServerResponseData> {
                override fun onResponse(
                    call: Call<PODServerResponseData>,
                    response: Response<PODServerResponseData>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        liveDataForViewToObserve.value =
                            PictureOfTheDayData.Success(response.body()!!)
                    } else {
                        val message = response.message()
                        if (message.isNullOrEmpty()) {
                            liveDataForViewToObserve.value =
                                PictureOfTheDayData.Error(Throwable("Unidentified error"))
                        } else {
                            liveDataForViewToObserve.value =
                                PictureOfTheDayData.Error(Throwable(message))
                        }
                    }
                }

                override fun onFailure(call: Call<PODServerResponseData>, t: Throwable) {
                    liveDataForViewToObserve.value = PictureOfTheDayData.Error(Throwable(t))
                }
            })
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun getDate (day: String): String{
        val calendar = Calendar.getInstance()
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        when (day) {
            SELECT_DAY_TODAY -> calendar.add(Calendar.HOUR, -7)
            SELECT_DAY_YESTERDAY -> calendar.add(Calendar.HOUR, -31)
            SELECT_DAY_DAY_BEFORE_YESTERDAY -> calendar.add(Calendar.HOUR, -55)
        }
        return formatter.format(calendar.time)
    }
}