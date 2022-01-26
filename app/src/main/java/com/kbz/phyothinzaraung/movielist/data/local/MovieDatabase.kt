package com.kbz.phyothinzaraung.movielist.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kbz.phyothinzaraung.movielist.data.model.Movie
import com.kbz.phyothinzaraung.movielist.data.model.RemoteKey
import com.kbz.phyothinzaraung.movielist.util.Constant

@Database(entities = [Movie::class, RemoteKey::class], version = Constant.DB_VERSION)
abstract class MovieDatabase: RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun remoteKeyDao(): RemoteKeyDao
}