package com.example.myapplication.newsapiclient.domain.usecase

import com.example.myapplication.newsapiclient.data.model.ApiResponse
import com.example.myapplication.newsapiclient.data.model.Article
import com.example.myapplication.newsapiclient.data.util.Resource
import com.example.myapplication.newsapiclient.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class GetSavedNewsUseCase(private val newsRepository: NewsRepository)  {
    suspend fun execute(): Flow<List<Article>> {
        return newsRepository.getSavedNews()
    }
}