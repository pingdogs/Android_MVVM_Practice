package com.example.myapplication.newsapiclient.domain.usecase

import com.example.myapplication.newsapiclient.data.model.ApiResponse
import com.example.myapplication.newsapiclient.data.util.Resource
import com.example.myapplication.newsapiclient.domain.repository.NewsRepository

class GetNewsHeadlinesUseCase(private val newsRepository: NewsRepository) {
    suspend fun execute(country:String, page:Int): Resource<ApiResponse>{
        return newsRepository.getNewsHeadlines(country, page)
    }

}