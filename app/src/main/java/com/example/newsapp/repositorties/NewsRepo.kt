package com.example.newsapp.repositorties

import com.example.newsapp.api.NewsApiService
import javax.inject.Inject

class NewsRepo @Inject constructor(private val newsApiService: NewsApiService){
    suspend fun allNews(q:String,page:Int)=newsApiService.allNews(q,page)
    suspend fun breakingNews(country:String,page: Int)=
        newsApiService.breakingNews(country,page)
}