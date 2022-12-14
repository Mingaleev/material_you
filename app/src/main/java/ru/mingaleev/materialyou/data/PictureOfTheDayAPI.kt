package ru.mingaleev.materialyou.data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PictureOfTheDayAPI {
    @GET("planetary/apod")
    fun getPictureOfTheDayByDate(
        @Query("api_key") apiKey: String,
        @Query("date") date: String
    ):
            Call<PODServerResponseData>
}