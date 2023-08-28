package com.development.data

import com.development.data.apiService.RemoteApiService
import com.development.data.datasources.LocalDataSource
import com.development.data.datasources.RemoteDataSource
import com.development.data.entities.MoviesResponse
import com.development.data.mappers.DatabaseDataMapper
import com.development.data.mappers.ResponseDataMapper
import com.development.data.repositories.MoviesRepositoryImpl
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(RobolectricTestRunner::class)
class RetrofitTest {

    private val server = MockWebServer()
    private lateinit var repository: MoviesRepositoryImpl
    private lateinit var remoteDataSource: RemoteDataSource
    private val localDataSource = mockk<LocalDataSource>()
    private val responseDataMapper = mockk<ResponseDataMapper>()
    private val databaseDataMapper = mockk<DatabaseDataMapper>()
    @Before
    fun init() {
        server.start(8000)
        val BASE_URL = server.url("/").toString()
        val okHttpClient = OkHttpClient
            .Builder()
            .build()
        val service = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .client(okHttpClient)
            .build().create(RemoteApiService::class.java)
        remoteDataSource = RemoteDataSource(service)
        repository = MoviesRepositoryImpl(remoteDataSource, localDataSource, responseDataMapper, databaseDataMapper)
    }

    @After
    fun tearDown() {
        server.shutdown()
    }

    @Test
    fun testApiSuccess() = runTest {
        val mockedResponse = MockResponseFileReader("responses/success.json").content
        server.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(mockedResponse)
        )
        val response = remoteDataSource.getMoviesFromServer(1)
        val json = GsonBuilder().setLenient().create().toJson(response)
        val resultResponse = Gson().fromJson(json, MoviesResponse::class.java)
        val expectedResponse = Gson().fromJson(mockedResponse, MoviesResponse::class.java)
        Assert.assertNotNull(response)
        Assert.assertTrue(resultResponse.movieRemotes?.size == expectedResponse.movieRemotes?.size)
    }
}