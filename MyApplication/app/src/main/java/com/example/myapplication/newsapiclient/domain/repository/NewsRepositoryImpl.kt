package com.example.myapplication.newsapiclient.domain.repository

import com.example.myapplication.newsapiclient.data.dataSource.NewsLocalDataSource
import com.example.myapplication.newsapiclient.data.model.ApiResponse
import com.example.myapplication.newsapiclient.data.model.Article
import com.example.myapplication.newsapiclient.data.util.Resource
import com.example.myapplication.newsapiclient.data.dataSource.NewsRemoteDataSource
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class NewsRepositoryImpl(
    private val newsRemoteDataSource:NewsRemoteDataSource,
    private val newsLocalDataSource: NewsLocalDataSource
    ): NewsRepository{
    override suspend fun getNewsHeadlines(country:String, page:Int): Resource<ApiResponse> {
        return responseToResource(newsRemoteDataSource.getTopHeadlines(country, page))
    }

    override suspend fun getSearchedNews(country:String, searchQuery: String, page:Int): Resource<ApiResponse> {
        return responseToResource(newsRemoteDataSource.getSearchTopHeadlines(country, searchQuery, page))
    }
    private  fun responseToResource(response: Response<ApiResponse>):Resource<ApiResponse>{
        if(response.isSuccessful){
            response.body()?.let{result->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }

    override suspend fun saveNews(article: Article) {
        newsLocalDataSource.saveArticleToDB(article)
    }

    override suspend fun deleteNews(article: Article) {
        newsLocalDataSource.deleteArticlesFromDB(article)
    }

    override suspend fun getSavedNews(): Flow<List<Article>> {
        return newsLocalDataSource.getSavedArticles()
    }
}