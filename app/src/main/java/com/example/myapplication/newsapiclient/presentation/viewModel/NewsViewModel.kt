package com.example.myapplication.newsapiclient.presentation.viewModel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.lifecycle.*
import com.example.myapplication.newsapiclient.data.model.ApiResponse
import com.example.myapplication.newsapiclient.data.model.Article
import com.example.myapplication.newsapiclient.data.util.Resource
import com.example.myapplication.newsapiclient.domain.usecase.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class NewsViewModel(
    private val app: Application,
    private val getNewsHeadlinesUseCase: GetNewsHeadlinesUseCase,
    private val getSearchedNewsHeadlinesUseCase: GetSearchedNewsHeadlinesUseCase,
    private val savedNewsUseCase: SavedNewsUseCase,
    private val getSavedNewsUseCase: GetSavedNewsUseCase,
    private val deleteSavedNewsUseCase: DeleteSavedNewsUseCase
) : AndroidViewModel(app){
    val newsHeadLines:MutableLiveData<Resource<ApiResponse>> = MutableLiveData()
    fun getNewsHeadLines(country:String, page:Int) = viewModelScope.launch(Dispatchers.IO){
        newsHeadLines.postValue(Resource.Loading())
        try {
            if (isNetworkAvaliable(app)) {
                val apiResult = getNewsHeadlinesUseCase.execute(country, page)
                newsHeadLines.postValue(apiResult)
            } else {
                newsHeadLines.postValue(Resource.Error("Internet is not avaliable"))
            }
        }catch (e:Exception){
            newsHeadLines.postValue(Resource.Error(e.message.toString()))
        }

    }

    val searchedNews : MutableLiveData<Resource<ApiResponse>> = MutableLiveData()
    fun getSearchedNewsHeadLines(country:String, query:String, page:Int) = viewModelScope.launch(Dispatchers.IO){
        newsHeadLines.postValue(Resource.Loading())
        try {
            if (isNetworkAvaliable(app)) {
                val apiResult = getSearchedNewsHeadlinesUseCase.execute(country,query, page)
                searchedNews.postValue(apiResult)
            } else {
                searchedNews.postValue(Resource.Error("Internet is not avaliable"))
            }
        }catch (e:Exception){
            searchedNews.postValue(Resource.Error(e.message.toString()))
        }

    }
    private fun isNetworkAvaliable(context: Context?):Boolean{
        val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo

        if(activeNetwork?.isConnected!=null){
            return activeNetwork.isConnected
        }
        else{
            return false
        }
    }

    fun saveArticle(article: Article) = viewModelScope.launch{
        savedNewsUseCase.execute(article)
    }

    fun getSavedNews() = liveData{
        getSavedNewsUseCase.execute().collect {
            emit(it)
        }
    }

    fun deleteArticle(article:Article) = viewModelScope.launch {
        deleteSavedNewsUseCase.execute(article)
    }



}