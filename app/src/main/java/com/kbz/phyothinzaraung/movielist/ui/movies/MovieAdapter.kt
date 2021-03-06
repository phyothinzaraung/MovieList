package com.kbz.phyothinzaraung.movielist.ui.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kbz.phyothinzaraung.movielist.data.model.Movie
import com.kbz.phyothinzaraung.movielist.databinding.ItemMovieBinding
import com.kbz.phyothinzaraung.movielist.ui.RecyclerViewItemClickListener
import com.kbz.phyothinzaraung.movielist.util.Constant

class MovieAdapter(
    private val listener: RecyclerViewItemClickListener
): PagingDataAdapter<Movie, MovieAdapter.MovieViewHolder>(MovieComparator()) {

//    private lateinit var movieList: PagingData<Movie>
//    private lateinit var listdataSearch: PagingData<Movie>
//
//    fun setSearchData(movieList: PagingData<Movie>){
//        this.movieList = movieList
//        listdataSearch = movieList
//    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(getItem(position)!!, listener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

//    override fun getFilter(): Filter {
//        return object : Filter() {
//            override fun performFiltering(query: CharSequence?): Filter.FilterResults {
//
//
//                var listFilter: PagingData<Movie>
//                if(query==null||query.isEmpty())
//                {
//                    listFilter =movieList
//                }else
//                {
//
//                    val filterPattern: String = query.toString().toLowerCase().trim()
//                    for (item in movieList) {
//                        if (item.title.toLowerCase().contains(filterPattern)) {
//                            listFilter.add(item)
//                        }
//                    }
//                }
//                val results = Filter.FilterResults()
//                results.values = listFilter
//                return  results;
//            }
//
//            override fun publishResults(p0: CharSequence?, p1: Filter.FilterResults?) {
//
//                listdataSearch.clear()
//                listdataSearch.addAll(p1?.values as Collection<Movie>)
//
//                notifyDataSetChanged()
//            }
//        }
//
//
//    }

    class MovieViewHolder(private val binding: ItemMovieBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(movie: Movie, listener: RecyclerViewItemClickListener){
            binding.apply {
                textTitle.text = movie.title
                Glide.with(binding.root)
                    .load("${Constant.IMAGE_URL}${movie.poster_path}")
                    .centerCrop()
                    .into(imageMoviePoster)
                binding.root.setOnClickListener{listener.onRecyclerViewItemClick(binding.root, movie)}
            }
        }
    }

    class MovieComparator: DiffUtil.ItemCallback<Movie>(){
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }

    }
}