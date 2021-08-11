package com.example.myapplication.newsapiclient.presentation.di

import com.example.myapplication.newsapiclient.data.dataSource.NewsLocalDataSource
import com.example.myapplication.newsapiclient.data.dataSourceImpl.NewsLocalDataSourceImpl
import com.example.myapplication.newsapiclient.data.dataSourceImpl.NewsRemoteDataSourceImpl
import com.example.myapplication.newsapiclient.data.db.ArticleDAO
import com.example.myapplication.newsapiclient.presentation.adapter.NewsAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalDataModule {

    @Singleton
    @Provides
    fun provideLocalDataSource(articleDAO: ArticleDAO): NewsLocalDataSource{
        return NewsLocalDataSourceImpl(articleDAO)
    }

}