package com.example.myapplication.newsapiclient.domain.repository

import com.example.myapplication.newsapiclient.data.model.ApiResponse
import com.example.myapplication.newsapiclient.data.model.Article
import com.example.myapplication.newsapiclient.data.util.Resource
import kotlinx.coroutines.flow.Flow

interface NewsRepository{

    suspend fun getNewsHeadlines(country:String, page:Int):Resource<ApiResponse>
    suspend fun  getSearchedNews(country:String,searchQuery:String, page:Int):Resource<ApiResponse>
    suspend fun saveNews(article: Article)
    suspend fun deleteNews(article:Article)
    suspend fun getSavedNews(): Flow<List<Article>>
}