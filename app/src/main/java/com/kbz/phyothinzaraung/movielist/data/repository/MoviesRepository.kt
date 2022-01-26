package com.kbz.phyothinzaraung.movielist.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.kbz.phyothinzaraung.movielist.data.local.MovieDatabase
import com.kbz.phyothinzaraung.movielist.data.model.Movie
import com.kbz.phyothinzaraung.movielist.data.remote.MovieListApi
import com.kbz.phyothinzaraung.movielist.data.remote.MovieRemoteMediator
import kotlinx.coroutines.flow.Flow

class MoviesRepository @javax.inject.Inject constructor(
    private val api: MovieListApi,
    private val db: MovieDatabase
) {

    @ExperimentalPagingApi
    fun getMovies(
    ): Flow<PagingData<Movie>> = Pager(
        config = PagingConfig(pageSize = 20, enablePlaceholders = false),
        remoteMediator = MovieRemoteMediator(api, db),
        pagingSourceFactory = { db.movieDao().getMoviePageSource() }
    ).flow



}