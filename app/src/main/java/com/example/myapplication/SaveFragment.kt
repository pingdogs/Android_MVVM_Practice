package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.FragmentSaveBinding
import com.example.myapplication.newsapiclient.MainActivity
import com.example.myapplication.newsapiclient.presentation.adapter.NewsAdapter
import com.example.myapplication.newsapiclient.presentation.viewModel.NewsViewModel
import com.example.myapplication.newsapiclient.presentation.viewModel.NewsViewModelFactory
import com.google.android.material.snackbar.Snackbar


class SaveFragment : Fragment() {
    private lateinit var fragmentSaveBinding: FragmentSaveBinding
    private lateinit var viewModel: NewsViewModel
    private lateinit var newsAdapter: NewsAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_save, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentSaveBinding = FragmentSaveBinding.bind(view)
        viewModel = (activity as MainActivity).viewModel
        newsAdapter =  (activity as MainActivity).newsAdapter
        newsAdapter.setOnItemClickListener{
            findNavController().navigate(SaveFragmentDirections.actionSaveFragmentToInfoFragment(it))
        }
        initRecyclerView()
        viewModel.getSavedNews().observe(viewLifecycleOwner,{
            newsAdapter.differ.submitList(it)
        })

        val itemTouchHelperCallback = object: ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                val position = viewHolder.adapterPosition
                val article = newsAdapter.differ.currentList[position]
                viewModel.deleteArticle(article)
                Snackbar.make(view, "Deleted Successfully", Snackbar.LENGTH_LONG)
                    .apply {
                        setAction("Undo"){
                            viewModel.saveArticle(article)
                        }
                        show()
                    }
            }

        }

        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(fragmentSaveBinding.rvSaved)
        }


    }

    private fun initRecyclerView(){
        fragmentSaveBinding.rvSaved.apply{
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}