package com.kbz.phyothinzaraung.movielist.ui

import android.view.View
import com.kbz.phyothinzaraung.movielist.data.model.Movie

interface RecyclerViewItemClickListener {
    fun onRecyclerViewItemClick(view: View, movie: Movie)
}