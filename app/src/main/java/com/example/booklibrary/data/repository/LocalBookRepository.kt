package com.example.booklibrary.data.repository

import com.example.booklibrary.R
import com.example.booklibrary.data.model.Book
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LocalBookRepository : BookRepository {
    private val _books = MutableStateFlow<List<Book>>(
        listOf(
            Book(
                id = "1",
                imageRes = R.drawable.primal_hunter_1,
                title = "The Primal Hunter",
                author = "Zogarth",
                genre = "Fantasy",
                rating = 4.8f,
                isSaved = true,
                currentChapter = 12,
                totalChapter = 200
            ),
            Book(
                id = "2",
                imageRes = R.drawable.atomic_habits,
                title = "Atomic Habits",
                author = "James Clear",
                genre = "Self Help",
                rating = 4.7f,
                isSaved = true,
                currentChapter = 35,
                totalChapter = 80
            ),
            Book(
                id = "3",
                imageRes = R.drawable.rich_dad,
                title = "Rich Dad Poor Dad",
                author = "Robert T. Kiyosaki",
                genre = "Finance",
                rating = 4.5f,
                isSaved = true,
                currentChapter = null,
                totalChapter = null
            ),
            Book(
                id = "4",
                imageRes = R.drawable.eternal_thief,
                title = "Eternal Thief",
                author = "Clayton Croft",
                genre = "Fantasy",
                rating = 4.6f,
                isSaved = false,
                currentChapter = null,
                totalChapter = null
            ),
            Book(
                id = "5",
                imageRes = R.drawable.deep_work,
                title = "Deep Work",
                author = "Cal Newport",
                genre = "Productivity",
                rating = 4.4f,
                isSaved = true,
                currentChapter = null,
                totalChapter = null
            ),
            Book(
                id = "6",
                imageRes = R.drawable.complete_martial,
                title = "Complete Martial Arts Attribute",
                author = "Blue Bubble",
                genre = "Sci-Fi",
                rating = 4.3f,
                isSaved = false,
                currentChapter = null,
                totalChapter = null
            )
        )
    )

    override fun getBooks(): Flow<List<Book>> = _books.asStateFlow()

    override suspend fun toggleSaved(bookId: String) {
        _books.update { currentList ->
            currentList.map { book ->
                if (book.id == bookId) {
                    book.copy(isSaved = !book.isSaved)
                } else {
                    book
                }
            }
        }
    }

    override suspend fun updateProgress(bookId: String, currentChapter: Int) {
        _books.update { currentList ->
            currentList.map { book ->
                if (book.id == bookId) {
                    val total = book.totalChapter ?: 100
                    val nextChapter = currentChapter.coerceAtMost(total)
                    book.copy(currentChapter = nextChapter)
                } else {
                    book
                }
            }
        }
    }
}
