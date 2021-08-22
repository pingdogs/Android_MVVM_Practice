package com.example.myapplication.newsapiclient.data.dataSourceImpl

import com.example.myapplication.newsapiclient.data.dataSource.NewsLocalDataSource
import com.example.myapplication.newsapiclient.data.db.ArticleDAO
import com.example.myapplication.newsapiclient.data.model.Article
import kotlinx.coroutines.flow.Flow

class NewsLocalDataSourceImpl(
    private val articleDAO: ArticleDAO
): NewsLocalDataSource {
    override suspend fun saveArticleToDB(article: Article) {
        articleDAO.insert(article)
    }

    override suspend fun getSavedArticles(): Flow<List<Article>> {
        return articleDAO.getAllArticles()
    }

    override suspend fun deleteArticlesFromDB(article: Article) {
        articleDAO.delete(article)
    }
}