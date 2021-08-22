package com.example.myapplication.newsapiclient.data.dataSource

import com.example.myapplication.newsapiclient.data.model.ApiResponse
import retrofit2.Response

interface NewsRemoteDataSource {
    suspend fun  getTopHeadlines(country:String, page:Int): Response<ApiResponse>
    suspend fun  getSearchTopHeadlines(country:String, searchQuery: String,  page:Int): Response<ApiResponse>
}