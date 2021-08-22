package com.example.myapplication.newsapiclient.data.dataSourceImpl

import com.example.myapplication.newsapiclient.data.NewsAPIService
import com.example.myapplication.newsapiclient.data.model.ApiResponse
import com.example.myapplication.newsapiclient.data.dataSource.NewsRemoteDataSource
import retrofit2.Response

class NewsRemoteDataSourceImpl(
    private val newsAPIService:NewsAPIService):
    NewsRemoteDataSource{
    override suspend fun getTopHeadlines(country:String, page:Int): Response<ApiResponse> {
        return newsAPIService.getTopHeadlines(country, page)
    }

    override suspend fun getSearchTopHeadlines(
        country: String,
        searchQuery: String,
        page: Int
    ): Response<ApiResponse> {
        return newsAPIService.getSearchedTopHeadlines(country, searchQuery, page)
    }
}