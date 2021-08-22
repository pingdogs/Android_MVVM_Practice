package com.example.myapplication

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.navigation.fragment.navArgs
import com.example.myapplication.databinding.FragmentInfoBinding
import com.example.myapplication.newsapiclient.MainActivity
import com.example.myapplication.newsapiclient.presentation.viewModel.NewsViewModel
import com.google.android.material.snackbar.Snackbar

class InfoFragment : Fragment() {
    private lateinit var fragmentInfoBinding: FragmentInfoBinding
    private lateinit var viewModel: NewsViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentInfoBinding = FragmentInfoBinding.bind(view)
        val args : InfoFragmentArgs by navArgs()
        viewModel = (activity as MainActivity).viewModel
        args?.let{
            fragmentInfoBinding.wvInfo.apply{
                webViewClient = WebViewClient()
                if(it.selectArticle.url!= null) {
                    loadUrl(it.selectArticle.url!!)
                }
            }
        }
        fragmentInfoBinding.fabSave.setOnClickListener{
            viewModel.saveArticle(args.selectArticle)
            Snackbar.make(view, "Save Successfully!", Snackbar.LENGTH_LONG).show()
        }
    }


}