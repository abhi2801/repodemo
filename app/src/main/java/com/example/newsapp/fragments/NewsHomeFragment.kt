package com.example.newsapp.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.R
import com.example.newsapp.adapters.NewsAdapter
import com.example.newsapp.databinding.FragmentNewsHomeBinding
import com.example.newsapp.models.Article
import com.example.newsapp.utils.Resource
import com.example.newsapp.viewmodels.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsHomeFragment : Fragment() {
   lateinit var binding: FragmentNewsHomeBinding
   lateinit var newsAdapter: NewsAdapter
   private val viewModel:NewsViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true)
        binding= FragmentNewsHomeBinding.inflate(inflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerview()
        newsAdapter.onItemClick {
            Toast.makeText(requireContext(), "Clicked ${it.author}", Toast.LENGTH_SHORT).show()
            val action=NewsHomeFragmentDirections.actionNewsHomeFragmentToNewsDetailsFragment(it)
            findNavController().navigate(action)
        }
        viewModel.breakingNews.observe(viewLifecycleOwner, Observer {response->
            when(response){
                is Resource.Loading->{
                    showProgressbar()
                }
                is Resource.Success->{
                    hideProgressbar()
                    response.data?.let {
                        Log.d("data",it.articles.toString())
                        Toast.makeText(requireContext(), "${it.articles}", Toast.LENGTH_LONG).show()
                        newsAdapter.differ.submitList(it.articles)
                    }
                }
                is Resource.Failed->{
                    hideProgressbar()
                    response.message?.let {
                        Log.e("erroe","an error occured $it")
                        Toast.makeText(requireContext(), "failed", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }
    private fun setupRecyclerview(){
        newsAdapter= NewsAdapter()
        binding.mainRecView.apply {
            adapter=newsAdapter
            setHasFixedSize(true)
        }
    }
    private fun showProgressbar(){
        binding.pb.visibility=View.VISIBLE
    }
    private fun hideProgressbar(){
        binding.pb.visibility=View.GONE
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.top_menu,menu)
        return super.onCreateOptionsMenu(menu, inflater)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.savedNews->{
                Toast.makeText(requireContext(), "Saved News", Toast.LENGTH_SHORT).show()
                findNavController().navigate(NewsHomeFragmentDirections.actionNewsHomeFragmentToFavouriteNewsFragment())
            }
                R.id.search-> {
                    Toast.makeText(requireContext(), "Search News", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(NewsHomeFragmentDirections.actionNewsHomeFragmentToSearchNewsFragment())
                }
        }
        return super.onOptionsItemSelected(item)
    }
}