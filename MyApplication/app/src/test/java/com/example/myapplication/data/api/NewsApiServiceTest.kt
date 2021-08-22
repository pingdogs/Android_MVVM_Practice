package com.example.myapplication.data.api

import com.example.myapplication.newsapiclient.data.NewsAPIService
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.nio.charset.Charset

class NewsApiServiceTest {
    private lateinit var service:NewsAPIService
    private lateinit var server:MockWebServer

    @Before
    fun setUp() {
        server = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(server.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsAPIService::class.java)
    }
    private fun enqueueMockResponse(
        fileName:String
    ){
        val inputStream = javaClass.classLoader!!.getResourceAsStream((fileName))
        val source = inputStream.source().buffer()
        val mockResponse = MockResponse()
        mockResponse.setBody(source.readString(Charsets.UTF_8))
        server.enqueue(mockResponse)
    }
    @Test
    fun getTopHeadlines_sentRequest_receivedExpected() {
        runBlocking {
            enqueueMockResponse("newsresponse.json")
            val responseBody = service.getTopHeadlines("us", 1).body()
            val resquest = server.takeRequest()
            assertThat(responseBody).isNotNull()
            assertThat(resquest.path).isEqualTo("/v2/top-headlines?country=us&page=1&apiKey=6062ec57607041f88dc31830b6b96b62")
        }
    }
    @Test
    fun getTopHeadlines_receivedResponse_correctPageSize(){
        runBlocking{
            enqueueMockResponse("newsresponse.json")
            val responseBody = service.getTopHeadlines("us", 1).body()
            val articlesList = responseBody!!.articles
            val article = articlesList[0]
            assertThat(article.author).isEqualTo("https://www.facebook.com/bbcnews")
            assertThat(article.url).isEqualTo("https://www.bbc.com/news/world-europe-58043912")
            assertThat(article.publishedAt).isEqualTo("2021-08-01T04:37:20Z")
        }
    }


    @After
    fun tearDown() {
        server.shutdown()
    }
}