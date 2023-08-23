package com.development.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.development.data.entities.MoviesDbModel

@Dao
interface MoviesDatabaseDao {

    @Query("SELECT * FROM moviesdbmodel")
    fun getMoviesFromDatabase(): List<MoviesDbModel>

    @Query("SELECT * FROM moviesdbmodel WHERE is_favorite=1")
    fun getFavoriteMoviesFromDatabase(): List<MoviesDbModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveMovies(news: List<MoviesDbModel>)

    @Query("UPDATE moviesdbmodel SET is_favorite=:bookmark WHERE id=:id")
    fun updateBookmarkById(id: Int, bookmark: Boolean)

    @Query("DELETE FROM moviesdbmodel")
    fun deleteAll()
}