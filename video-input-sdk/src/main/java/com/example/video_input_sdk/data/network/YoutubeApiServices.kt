package com.example.video_input_sdk.data.network

import com.example.ytinfoprocessing.data.network.models.YoutubeVideoDataResponse
import retrofit2.http.GET
import retrofit2.http.Query

private const val API_KEY = "AIzaSyCOglrP5mdvf0s5oasFxBW5-zGLNVle-pE"

interface YoutubeApiServices {
    @GET("videos")
    suspend fun getVideoDetails(
        @Query("id") videoId: String,
        @Query("key") apiKey: String = API_KEY,
        @Query("part") part: String = "snippet",
    ): YoutubeVideoDataResponse
}
