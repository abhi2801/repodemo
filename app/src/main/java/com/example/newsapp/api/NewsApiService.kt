package com.example.newsapp.api

import com.example.newsapp.models.NewsResponse
import com.example.newsapp.utils.Constants.api_key
import com.example.newsapp.utils.Constants.breakingNews_endPoint
import com.example.newsapp.utils.Constants.searchNews_endPoint
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    @GET(searchNews_endPoint)
    suspend fun allNews(@Query("q")query:String,
                        @Query("page")page: Int=1,
                        @Query("apikey")apikey:String=api_key):Response<NewsResponse>
    @GET(breakingNews_endPoint)
    suspend fun breakingNews(@Query("country")country:String="in",
                         @Query("page")page:Int=1,
                        @Query("apikey")apikey:String=api_key):Response<NewsResponse>
}