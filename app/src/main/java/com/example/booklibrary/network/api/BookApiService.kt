package com.example.booklibrary.network.api

import com.example.booklibrary.network.dto.BookItemDto
import com.example.booklibrary.network.dto.GoogleBooksResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BookApiService {
    @GET("volumes")
    suspend fun searchBooks(
        @Query("q") query: String,
        @Query("maxResults") maxResults: Int = 20
    ): GoogleBooksResponse

    @GET("volumes/{id}")
    suspend fun getBookById(
        @Path("id") id: String
    ): BookItemDto
}
