package com.example.newsapp.viewmodels

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.models.NewsResponse
import com.example.newsapp.repositorties.NewsRepo
import com.example.newsapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject
@HiltViewModel
class NewsViewModel @Inject constructor(private val newsRepo: NewsRepo):ViewModel() {
    val breakingNews:MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    val allNews:MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var breakingNewsPageNo=1
    var allNews_pageNo=1
    init {
        breakingNews("in")
    }
    fun breakingNews(country:String)=viewModelScope.launch {
            breakingNews.postValue(Resource.Loading())
        val data=newsRepo.breakingNews(country,breakingNewsPageNo)
        if (data.isSuccessful){
            data.body()?.let { info->
                val news=info.articles
                Log.i("main","$news")
                breakingNews.postValue(handleBreakingNews(data))
            }
        }
    }
    fun searchNews(query:String)=viewModelScope.launch {
        allNews.postValue(Resource.Loading())
        val data=newsRepo.allNews(query,allNews_pageNo)
        if (data.isSuccessful){
            data.body()?.let { info->
                val news=info.articles
                allNews.postValue(handleSearchgNews(data))
            }
        }
    }
    fun handleBreakingNews(response: Response<NewsResponse>):Resource<NewsResponse>{
        if (response.isSuccessful){
            response.body()?.let { newsResponse->
                return Resource.Success(newsResponse)
            }
        }
        return Resource.Failed(response.message())
    }
    fun handleSearchgNews(response: Response<NewsResponse>):Resource<NewsResponse>{
        if (response.isSuccessful){
            response.body()?.let { newsResponse->
                return Resource.Success(newsResponse)
            }
        }
        return Resource.Failed(response.message())
    }
//    fun setCategory(value:String){
//        category=value
//        return breakingNews()
//    }
}