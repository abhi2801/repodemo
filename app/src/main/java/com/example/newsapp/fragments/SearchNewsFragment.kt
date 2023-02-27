package com.example.newsapp.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import com.example.newsapp.R
import com.example.newsapp.adapters.NewsAdapter
import com.example.newsapp.databinding.FragmentSearchNewsBinding
import com.example.newsapp.utils.Resource
import com.example.newsapp.viewmodels.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
@AndroidEntryPoint
class SearchNewsFragment : Fragment() {
    private val viewModel:NewsViewModel by viewModels()
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var binding: FragmentSearchNewsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding= FragmentSearchNewsBinding.inflate(inflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerview()
        var job: Job?=null
        binding.searchText.addTextChangedListener {editable->
            job?.cancel()
            job= MainScope().launch {
                delay(500)
                editable?.let {
                    if (editable.toString().isNotEmpty()){
                        viewModel.searchNews(editable.toString())
                    }
                }
            }
        }
        viewModel.allNews.observe(viewLifecycleOwner, Observer {response->
            when(response){
                is Resource.Failed->{
                    hideProgressBar()
                    response.message?.let {
                        Log.e("failed","an error occured $it")
                        Toast.makeText(requireContext(), "error$it", Toast.LENGTH_SHORT).show()
                    }
                }
                is Resource.Success->{
                    hideProgressBar()
                    response.data?.let {newsResponse->
                        newsAdapter.differ.submitList(newsResponse.articles)
                    }
                }
                is Resource.Loading->{
                showProgressBar()
                }
            }
        })
    }
    private fun setupRecyclerview() {
        newsAdapter= NewsAdapter()
        binding.searchRecyclerview.apply {
            adapter=newsAdapter
            setHasFixedSize(true)
        }
    }
    private fun showProgressBar(){
        binding.pb.visibility=View.VISIBLE
    }
    private fun hideProgressBar(){
        binding.pb.visibility=View.GONE
    }
}