package com.example.booklibrary.data.repository

import com.example.booklibrary.data.model.Book
import com.example.booklibrary.network.mapper.BookMapper
import com.example.booklibrary.network.retrofit.RetrofitClient

class GoogleBooksRepository {
    private val apiService = RetrofitClient.apiService
    
    // In-memory cache for the most recent query results
    @Volatile
    private var lastQuery: String? = null
    
    @Volatile
    private var cachedBooks: List<Book> = emptyList()

    suspend fun searchBooks(query: String): List<Book> {
        val trimmedQuery = query.trim()
        if (trimmedQuery.isEmpty()) {
            return emptyList()
        }
        
        // Cache hit (case-insensitive check to avoid duplicate calls for the same term with different casing)
        synchronized(this) {
            if (trimmedQuery.equals(lastQuery, ignoreCase = true)) {
                return cachedBooks
            }
        }
        
        // Perform network request
        val response = apiService.searchBooks(trimmedQuery)
        val domainBooks = BookMapper.mapToDomain(response)
        
        // Update cache
        synchronized(this) {
            lastQuery = trimmedQuery
            cachedBooks = domainBooks
        }
        
        return domainBooks
    }

    fun getBookFromCache(bookId: String): Book? {
        return synchronized(this) {
            cachedBooks.find { it.id == bookId }
        }
    }

    suspend fun fetchBookById(bookId: String): Book {
        val response = apiService.getBookById(bookId)
        return BookMapper.mapItemToDomain(response)
    }
}
