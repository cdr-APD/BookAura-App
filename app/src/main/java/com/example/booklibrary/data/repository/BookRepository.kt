package com.example.booklibrary.data.repository

import com.example.booklibrary.data.model.Book
import kotlinx.coroutines.flow.Flow

interface BookRepository {
    fun getBooks(): Flow<List<Book>>
    suspend fun toggleSaved(bookId: String)
    suspend fun updateProgress(bookId: String, currentChapter: Int)
}
