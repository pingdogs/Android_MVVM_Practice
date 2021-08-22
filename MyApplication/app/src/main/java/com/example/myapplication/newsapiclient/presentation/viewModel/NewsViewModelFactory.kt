package com.example.myapplication.newsapiclient.presentation.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.newsapiclient.domain.usecase.*

class NewsViewModelFactory(
    private val app: Application,
    private val getNewsHeadlinesUseCase: GetNewsHeadlinesUseCase,
    private val getSearchedNewsHeadlinesUseCase: GetSearchedNewsHeadlinesUseCase,
    private val savedNewsUseCase: SavedNewsUseCase,
    private val getSavedNewsUseCase: GetSavedNewsUseCase,
    private val deleteSavedNewsUseCase: DeleteSavedNewsUseCase
) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NewsViewModel(app, getNewsHeadlinesUseCase, getSearchedNewsHeadlinesUseCase, savedNewsUseCase, getSavedNewsUseCase, deleteSavedNewsUseCase) as T
    }

}