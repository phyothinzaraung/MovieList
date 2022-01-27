package com.kbz.phyothinzaraung.movielist.ui.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.kbz.phyothinzaraung.movielist.data.repository.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    repository: MoviesRepository
): ViewModel() {

    private val loadMovieChannel = Channel<String>() {}
    private val loadMovieTrigger = loadMovieChannel.receiveAsFlow()
    var onprogress = false
    @ExperimentalCoroutinesApi
    @ExperimentalPagingApi
    val movie = loadMovieTrigger.flatMapLatest {
        repository.getMovies().cachedIn(viewModelScope)
    }.cachedIn(viewModelScope)

    fun onStart() {
        viewModelScope.launch {
            loadMovieChannel.send("_")
            onprogress=true
        }

    }
}
