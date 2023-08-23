package com.development.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.development.data.entities.MoviesDbModel

@Database(entities = [MoviesDbModel::class], version = 1)
abstract class MoviesDatabase : RoomDatabase() {
    abstract fun moviesDatabaseDao(): MoviesDatabaseDao
}