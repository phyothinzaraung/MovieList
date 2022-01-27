package com.kbz.phyothinzaraung.movielist.ui.moviedetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.google.android.material.appbar.AppBarLayout
import com.kbz.phyothinzaraung.movielist.R
import com.kbz.phyothinzaraung.movielist.data.model.Movie
import com.kbz.phyothinzaraung.movielist.databinding.FragmentMovieDetailsBinding
import com.kbz.phyothinzaraung.movielist.util.Constant

class MovieDetailsFragment : Fragment() {

    lateinit var binding: FragmentMovieDetailsBinding
    private lateinit var movie: Movie

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)

        movie = arguments?.getSerializable(Constant.BundleKey) as Movie
        setupToolbar()

        binding.apply {
            Glide.with(activity as AppCompatActivity)
                .load("${Constant.IMAGE_URL}${movie.poster_path}")
                .centerInside()
                .into(imageMovieBackdrop)

            Glide.with(activity as AppCompatActivity)
                .load("${Constant.IMAGE_URL}${movie.poster_path}")
                .centerCrop()
                .into(movieDetailsInfo.imagePoster)

            movieDetailsInfo.textTitle.text = movie.title
            movieDetailsInfo.textLanguage.text = movie.original_language
            movieDetailsInfo.textReleaseDate.text = movie.release_date
            movieDetailsInfo.textOverview.text = movie.overview
        }

        return binding.root
    }

    private fun setupToolbar() {
        val toolbar: Toolbar = binding.toolbar
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        if ((activity as AppCompatActivity).supportActionBar != null) {
            (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            handleCollapsedToolbarTitle()
        }

        toolbar.setNavigationOnClickListener{
            navigateToMovieList()
        }
    }

    private fun handleCollapsedToolbarTitle() {
        binding.appbar.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            var isShow = true
            var scrollRange = -1
            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.totalScrollRange
                }
                // verify if the toolbar is completely collapsed and set the movie name as the title
                if (scrollRange + verticalOffset == 0) {
                    binding.collapsingToolbar.title = movie.title
                    isShow = true
                } else if (isShow) {
                    // display an empty string when toolbar is expanded
                    binding.collapsingToolbar.title = " "
                    isShow = false
                }
            }
        })
    }

    private fun navigateToMovieList(){
        Navigation.findNavController(binding.root).navigate(R.id.action_movieDetailsFragment_to_movieListFragment)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(this){
            navigateToMovieList()
        }
    }
}