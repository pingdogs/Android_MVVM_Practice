package com.example.myapplication.newsapiclient.domain.usecase

import com.example.myapplication.newsapiclient.data.model.ApiResponse
import com.example.myapplication.newsapiclient.data.util.Resource
import com.example.myapplication.newsapiclient.domain.repository.NewsRepository

class GetSearchNewsUseCase(private val newsRepository: NewsRepository)  {

    suspend fun execute(country:String, searchQuery:String, page: Int): Resource<ApiResponse> {
        return newsRepository.getSearchedNews(country, searchQuery, page)
    }
}