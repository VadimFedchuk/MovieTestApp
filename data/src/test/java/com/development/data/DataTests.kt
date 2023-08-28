package com.development.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.development.data.database.MoviesDatabase
import com.development.data.database.MoviesDatabaseDao
import com.development.data.entities.MovieRemote
import com.development.data.entities.MoviesDbModel
import com.development.data.entities.MoviesResponse
import com.development.data.mappers.ResponseDataMapper
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import kotlin.random.Random

@RunWith(RobolectricTestRunner::class)
class DataTests {
    private lateinit var moviesDatabaseDao: MoviesDatabaseDao
    private lateinit var database: MoviesDatabase
    private val responseDataMapper: ResponseDataMapper = ResponseDataMapper()

    @Before
    fun createDatabase() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(
            context, MoviesDatabase::class.java).build()
        moviesDatabaseDao = database.moviesDatabaseDao()
    }

    @After
    fun closeDatabase() {
        database.close()
    }

    @Test
    fun `save movie and after that check if it was saved`() = runTest {
        val moviesDbModel = createTestMovie()

        moviesDatabaseDao.saveMovies(listOf(moviesDbModel))
        val savedData = moviesDatabaseDao.getMoviesFromDatabase()
        assertEquals(savedData.find { it.id == moviesDbModel.id }, moviesDbModel)
    }

    @Test
    fun `save movie than change isFavorite = true, than check if it is true`() = runTest {
        val moviesDbModel = createTestMovie()

        moviesDatabaseDao.saveMovies(listOf(moviesDbModel))
        moviesDatabaseDao.updateBookmarkById(moviesDbModel.id, true)
        val savedData = moviesDatabaseDao.getMoviesFromDatabase()
        assertTrue(savedData.firstOrNull {
            it.id == moviesDbModel.id &&
                    it.isFavorite == !moviesDbModel.isFavorite
        } != null)
    }

    @Test
    fun `save movie than deleteAll and check if database is Empty`() = runTest {
        val moviesDbModel = createTestMovie()

        moviesDatabaseDao.saveMovies(listOf(moviesDbModel))
        moviesDatabaseDao.deleteAll()
        val savedData = moviesDatabaseDao.getMoviesFromDatabase()
        assertTrue(savedData.isEmpty())
    }

    @Test
    fun `check that mapper return correct model`() = runTest {
        val moviesRemoteModel = createRemoteResponse()
        assertTrue(responseDataMapper.toMoviesDbModel(moviesRemoteModel).first() is MoviesDbModel)
    }
}

private fun createTestMovie() = MoviesDbModel(
    id = Random.nextInt(1000),
    title = "title",
    description = "description",
    rate = 5.0,
    publishedAt = "unknown",
    urlToImage = "unknown",
    isFavorite = false
)

private fun createRemoteResponse() = MoviesResponse(
    movieRemotes = listOf(
        MovieRemote(
            id = Random.nextInt(1000),
            title = "title",
            description = "description",
            rate = 5.0,
            releaseDate = "unknown",
            posterPath = "unknown",
        )
    )
)