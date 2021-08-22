package com.example.myapplication.newsapiclient.presentation.di

import android.app.Application
import com.example.myapplication.newsapiclient.domain.usecase.*
import com.example.myapplication.newsapiclient.presentation.viewModel.NewsViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class FactoryModule {

    @Provides
    @Singleton
    fun provideNewsViewModelFactory(application: Application, getNewsHeadlinesUseCase: GetNewsHeadlinesUseCase,
                                    getSearchedNewsHeadlinesUseCase: GetSearchedNewsHeadlinesUseCase,
                                    savedNewsUseCase: SavedNewsUseCase, getSavedNewsUseCase: GetSavedNewsUseCase, deleteSavedNewsUseCase:DeleteSavedNewsUseCase
    ):NewsViewModelFactory{
        return NewsViewModelFactory(application, getNewsHeadlinesUseCase, getSearchedNewsHeadlinesUseCase, savedNewsUseCase, getSavedNewsUseCase, deleteSavedNewsUseCase)
    }
}