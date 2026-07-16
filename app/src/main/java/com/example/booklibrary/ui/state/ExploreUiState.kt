package com.example.booklibrary.ui.state

import com.example.booklibrary.data.model.Book

sealed class ExploreSearchState {
    object Empty : ExploreSearchState()
    object Loading : ExploreSearchState()
    data class Success(val books: List<Book>) : ExploreSearchState()
    data class Error(val message: String) : ExploreSearchState()
}

data class ExploreUiState(
    val searchQuery: String = "",
    val selectedGenre: String = "All",
    val sortOption: BookSortOption = BookSortOption.RECENTLY_ADDED,
    val searchState: ExploreSearchState = ExploreSearchState.Empty
)

sealed class ExploreBookDetailsState {
    object Loading : ExploreBookDetailsState()
    data class Success(val book: Book) : ExploreBookDetailsState()
    data class Error(val message: String) : ExploreBookDetailsState()
}
