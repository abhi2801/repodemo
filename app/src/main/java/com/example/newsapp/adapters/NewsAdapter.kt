package com.example.newsapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bumptech.glide.Glide
import com.example.newsapp.R
import com.example.newsapp.databinding.RowItemBinding
import com.example.newsapp.fragments.NewsHomeFragmentDirections
import com.example.newsapp.models.Article

class NewsAdapter():RecyclerView.Adapter<NewsAdapter.NewsVH>() {
    inner class NewsVH(val binding: RowItemBinding) : RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }
        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, differCallback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsVH {
        return NewsVH(RowItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
    override fun onBindViewHolder(holder: NewsVH, position: Int) {
        val data = differ.currentList[position]
        holder.binding.apply {
            content.text = data.description
            publishedAt.text = data.publishedAt
            newsImage.load(data.urlToImage){
                crossfade(true)
                placeholder(R.drawable.place)
                error(R.drawable.error)
            }
        }
    }
    //        holder.itemView.setOnClickListener {
//           val direction=NewsHomeFragmentDirections.actionNewsHomeFragmentToNewsDetailsFragment(data)
//            it.findNavController().navigate(direction)
//
    private var onItemClickListener: ((Article) -> Unit)? = null
    fun onItemClick(listener: (Article) -> Unit) {
        onItemClickListener = listener
    }
}