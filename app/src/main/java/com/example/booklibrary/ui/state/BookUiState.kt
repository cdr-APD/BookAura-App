package com.example.booklibrary.ui.state

import com.example.booklibrary.data.model.Book

data class BookUiState(
    val books: List<Book> = emptyList(),
    val searchQuery: String = "",
    val selectedGenre: String = "All",
    val sortOption: BookSortOption = BookSortOption.RECENTLY_ADDED
)
