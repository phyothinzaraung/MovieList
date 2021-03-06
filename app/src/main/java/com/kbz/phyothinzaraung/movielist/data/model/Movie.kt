package com.kbz.phyothinzaraung.movielist.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "movie")
data class Movie(
    @PrimaryKey val id: Int,
    val title: String,
    val overview: String,
    val release_date: String,
    val poster_path: String,
    val original_language: String
): Serializable
