package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.FragmentNewsBinding
import com.example.myapplication.newsapiclient.MainActivity
import com.example.myapplication.newsapiclient.data.util.Resource
import com.example.myapplication.newsapiclient.presentation.adapter.NewsAdapter
import com.example.myapplication.newsapiclient.presentation.viewModel.NewsViewModel

// TODO: Rename parameter arguments, choose names that match

class NewsFragment : Fragment() {

    private lateinit var viewModel: NewsViewModel
    private lateinit var fragmentNewsBinding: FragmentNewsBinding
    private lateinit var newsAdapter: NewsAdapter
    private var country = "us"
    private var page = 1
    private var isScrolling = false
    private var isLoading = false
    private var isLastPage = false
    private var pages = 0
    private var isSeachedResult = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentNewsBinding = FragmentNewsBinding.bind(view)
        viewModel = (activity as MainActivity).viewModel
        newsAdapter = (activity as MainActivity).newsAdapter
        newsAdapter.setOnItemClickListener{
            findNavController().navigate(NewsFragmentDirections.actionNewsFragmentToInfoFragment(it))
        }
        initRecyclerView()
        viewNewsList()
        setSearchView()

    }

    private fun viewNewsList() {
        viewModel.getNewsHeadLines(country, page)
        viewModel.newsHeadLines.observe(viewLifecycleOwner, {response->
            when(response){
                is Resource.Success ->{
                    hideProgressBar()
                    response.data?.let{
                        newsAdapter.differ.submitList((it.articles.toList()))
                        isSeachedResult = false
                        if(it.totalResults%20 == 0){
                            pages = it.totalResults/20
                        }else{
                            pages = it.totalResults/20+1
                        }
                        isLastPage = page == pages
                    }


                }
                is Resource.Error ->{
                    hideProgressBar()
                    response.message?.let{
                        Toast.makeText(activity, "An error occurred: $it", Toast.LENGTH_LONG).show()
                    }


                }
                is Resource.Loading ->{
                    showProgressBar()
                }
            }
        })
    }
    private fun setSearchView(){
        fragmentNewsBinding.svNews.setOnQueryTextListener(object:android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let{
                    viewSearchedNewsList(it)
                }
                return true
            }
        })
        fragmentNewsBinding.svNews.setOnCloseListener (object : android.widget.SearchView.OnCloseListener{
            override fun onClose():Boolean{
                initRecyclerView()
                viewNewsList()
                isSeachedResult = false
                return false
            }
        })
    }

    private fun viewSearchedNewsList(text: String) {
        viewModel.getSearchedNewsHeadLines(country, text, page)
        viewModel.searchedNews.observe(viewLifecycleOwner, {response->
            when(response){
                is Resource.Success ->{
                    hideProgressBar()
                    response.data?.let{
                        newsAdapter.differ.submitList((it.articles.toList()))
                        isSeachedResult = true
                    }

                }
                is Resource.Error ->{
                    hideProgressBar()
                    response.message?.let{
                        Toast.makeText(activity, "An error occurred: $it", Toast.LENGTH_LONG).show()
                    }

                }
                is Resource.Loading ->{
                    showProgressBar()
                }
            }
        })
    }


    private val onScrollListener = object : RecyclerView.OnScrollListener(){
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                isScrolling = true
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager = fragmentNewsBinding.rvNews.layoutManager as LinearLayoutManager
            val sizeOfTheCurrentList = layoutManager.itemCount
            val visibleItems = layoutManager.childCount
            val topPosition = layoutManager.findFirstVisibleItemPosition()

            val hasReachedToEnd = topPosition+visibleItems >= sizeOfTheCurrentList
            val shouldPaginate = !isLoading && !isLastPage && hasReachedToEnd && isScrolling && !isSeachedResult
            if(shouldPaginate){
                page++
                viewModel.getNewsHeadLines(country, page)
                isScrolling = false

            }
        }   }

    private fun initRecyclerView(){
        fragmentNewsBinding.rvNews.apply{
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
            addOnScrollListener(onScrollListener)
        }
    }
    private fun showProgressBar(){
        isLoading = true
        fragmentNewsBinding.progressBar.visibility = View.VISIBLE
    }
    private fun hideProgressBar(){
        isLoading = false
        fragmentNewsBinding.progressBar.visibility = View.INVISIBLE
    }

}