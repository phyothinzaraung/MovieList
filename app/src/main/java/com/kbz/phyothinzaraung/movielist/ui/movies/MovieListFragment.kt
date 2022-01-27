package com.kbz.phyothinzaraung.movielist.ui.movies

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
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
    ): View {
        binding = FragmentMovieListBinding.inflate(inflater, container, false)

        setupToolbar()

        binding.apply {
            recyclerviewMovies.apply {
                adapter = movieAdapter
                layoutManager = GridLayoutManager(context, 2)
            }
        }
        fetchMovies()
        return binding.root
    }

    @ExperimentalPagingApi
    private fun fetchMovies() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.movie.collectLatest {
                movieAdapter.submitData(it)
                binding.progressBar.visibility = View.GONE
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

    private fun setupToolbar(){
        val toolbar = binding.appbar.toolbar
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        if ((activity as AppCompatActivity).supportActionBar != null) {
            (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_search, menu)

        // Associate searchable configuration with the SearchView
//        val searchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
//        (menu.findItem(R.id.action_search).actionView as SearchView).apply {
//            setSearchableInfo(searchManager.getSearchableInfo(activity?.componentName))
//        }

        val searchView = menu.findItem(R.id.action_search).actionView as SearchView
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                //movieAdapter.filter.filter(newText)
                return false
            }

        })

        super.onCreateOptionsMenu(menu, inflater)
    }
}

