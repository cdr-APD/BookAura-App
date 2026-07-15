package com.example.booklibrary.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.booklibrary.data.model.Book
import com.example.booklibrary.data.repository.BookRepository
import com.example.booklibrary.ui.state.BookSortOption
import com.example.booklibrary.ui.state.BookUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class BookViewModel(
    private val repository: BookRepository
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    private val _selectedGenre = MutableStateFlow("All")
    private val _sortOption = MutableStateFlow(BookSortOption.RECENTLY_ADDED)

    // Main UI State observing books from repository, filtered and sorted dynamically
    val uiState: StateFlow<BookUiState> = combine(
        repository.getBooks(),
        _searchQuery,
        _selectedGenre,
        _sortOption
    ) { rawBooks, query, genre, sort ->
        val filteredBooks = rawBooks.filter { book ->
            val matchesGenre = (genre == "All" || book.genre.equals(genre, ignoreCase = true))
            val matchesQuery = book.title.contains(query, ignoreCase = true) ||
                    book.author.contains(query, ignoreCase = true)
            matchesGenre && matchesQuery
        }

        val sortedBooks = when (sort) {
            BookSortOption.RECENTLY_ADDED -> {
                filteredBooks
            }
            BookSortOption.TITLE_AZ -> {
                filteredBooks.sortedBy { it.title.lowercase() }
            }
            BookSortOption.READING_PROGRESS -> {
                filteredBooks.sortedByDescending { book ->
                    if (book.currentChapter != null && book.totalChapter != null && book.totalChapter > 0) {
                        book.currentChapter.toFloat() / book.totalChapter.toFloat()
                    } else {
                        0f
                    }
                }
            }
        }

        BookUiState(
            books = sortedBooks,
            searchQuery = query,
            selectedGenre = genre,
            sortOption = sort
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = BookUiState()
    )

    // Saved Books State
    val savedBooks: StateFlow<List<Book>> = repository.getBooks().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    // Currently Reading Books (progress > 0% but less than 100%)
    val currentlyReading: StateFlow<List<Book>> = repository.getBooks().combine(uiState) { rawBooks, _ ->
        rawBooks.filter { book ->
            val progress = getBookProgress(book)
            progress > 0f && progress < 1f
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    // Completed Books (progress == 100%)
    val completedBooks: StateFlow<List<Book>> = repository.getBooks().combine(uiState) { rawBooks, _ ->
        rawBooks.filter { book ->
            val progress = getBookProgress(book)
            progress >= 1f
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    // Unread Collection (isSaved = true and progress == 0% or not started)
    val unreadCollection: StateFlow<List<Book>> = repository.getBooks().combine(uiState) { rawBooks, _ ->
        rawBooks.filter { book ->
            book.isSaved && (book.currentChapter == null || book.currentChapter == 0)
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    // Profile statistics: Books Saved, Currently Reading, Completed Books, Reading Progress
    val profileStats: StateFlow<ProfileStats> = repository.getBooks().combine(uiState) { rawBooks, _ ->
        val savedCount = rawBooks.count { it.isSaved }
        val readingCount = rawBooks.count { book ->
            val progress = getBookProgress(book)
            progress > 0f && progress < 1f
        }
        val completedCount = rawBooks.count { book ->
            val progress = getBookProgress(book)
            progress >= 1f
        }
        ProfileStats(
            booksSaved = savedCount.toString(),
            currentlyReading = readingCount.toString(),
            completed = completedCount.toString(),
            streak = "7"
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ProfileStats()
    )

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun setSelectedGenre(genre: String) {
        _selectedGenre.value = genre
    }

    fun setSortOption(option: BookSortOption) {
        _sortOption.value = option
    }

    fun toggleSaved(bookId: String) {
        viewModelScope.launch {
            repository.toggleSaved(bookId)
        }
    }

    fun incrementProgress(bookId: String) {
        viewModelScope.launch {
            val books = repository.getBooks().first()
            books.find { it.id == bookId }?.let { book ->
                val current = book.currentChapter ?: 0
                val total = book.totalChapter ?: 100
                if (current < total) {
                    val increment = if (total - current > 10) 10 else (total - current)
                    repository.updateProgress(bookId, current + increment)
                }
            }
        }
    }

    private fun getBookProgress(book: Book): Float {
        if (book.currentChapter == null || book.totalChapter == null || book.totalChapter == 0) return 0f
        return book.currentChapter.toFloat() / book.totalChapter.toFloat()
    }
}

class BookViewModelFactory(private val repository: BookRepository) : androidx.lifecycle.ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BookViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BookViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

data class ProfileStats(
    val booksSaved: String = "0",
    val currentlyReading: String = "0",
    val completed: String = "0",
    val streak: String = "7"
)
