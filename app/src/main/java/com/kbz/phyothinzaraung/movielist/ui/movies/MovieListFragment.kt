package com.kbz.phyothinzaraung.movielist.ui.movies

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.GridLayoutManager
import com.kbz.phyothinzaraung.movielist.R
import com.kbz.phyothinzaraung.movielist.data.model.Movie
import com.kbz.phyothinzaraung.movielist.databinding.FragmentMovieListBinding
import com.kbz.phyothinzaraung.movielist.ui.RecyclerViewItemClickListener
import com.kbz.phyothinzaraung.movielist.util.Constant
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MovieListFragment : Fragment(), RecyclerViewItemClickListener {

    lateinit var binding: FragmentMovieListBinding
    private val movieAdapter = MovieAdapter(this)
    private val viewModel: MoviesViewModel by viewModels()

    @OptIn(ExperimentalPagingApi::class)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMovieListBinding.inflate(inflater, container, false)

        binding.apply {
            recyclerviewMovies.apply {
                adapter = movieAdapter
                layoutManager = GridLayoutManager(context, 2)
            }
            fetchMovies()
        }
        return binding.root
    }

    @ExperimentalPagingApi
    private fun fetchMovies() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.movie.collectLatest {
                movieAdapter.submitData(it)
            }
        }
    }

    override fun onRecyclerViewItemClick(view: View, movie: Movie) {
        val bundle = bundleOf(Constant.BundleKey to movie)
        Navigation.findNavController(view).navigate(R.id.action_movieListFragment_to_movieDetailsFragment, bundle)
    }

    override fun onStart() {
        super.onStart()
        viewModel.onStart()

    }
}