package com.example.booklibrary.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.booklibrary.data.model.Book
import com.example.booklibrary.data.repository.GoogleBooksRepository
import com.example.booklibrary.ui.state.BookSortOption
import com.example.booklibrary.ui.state.ExploreSearchState
import com.example.booklibrary.ui.state.ExploreUiState
import com.example.booklibrary.ui.state.ExploreBookDetailsState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class ExploreViewModel(
    private val googleBooksRepository: GoogleBooksRepository = GoogleBooksRepository()
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _selectedGenre = MutableStateFlow("All")
    private val _sortOption = MutableStateFlow(BookSortOption.RECENTLY_ADDED)
    private val _savedExploreBookIds = MutableStateFlow<Set<String>>(emptySet())

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val exploreSearchState: StateFlow<ExploreSearchState> = _searchQuery
        .debounce(400)
        .distinctUntilChanged()
        .flatMapLatest { query ->
            flow {
                val trimmed = query.trim()
                if (trimmed.length < 2) {
                    emit(ExploreSearchState.Empty)
                    return@flow
                }
                emit(ExploreSearchState.Loading)
                try {
                    val results = googleBooksRepository.searchBooks(trimmed)
                    emit(ExploreSearchState.Success(results))
                } catch (t: Throwable) {
                    emit(ExploreSearchState.Error(getErrorMessage(t)))
                }
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ExploreSearchState.Empty
        )

    val exploreUiState: StateFlow<ExploreUiState> = combine(
        _searchQuery,
        _selectedGenre,
        _sortOption,
        exploreSearchState,
        _savedExploreBookIds
    ) { query, genre, sort, searchState, savedExploreIds ->
        val mappedSearchState = when (searchState) {
            is ExploreSearchState.Success -> {
                val booksWithSavedState = searchState.books.map { book ->
                    val isSaved = savedExploreIds.contains(book.id)
                    book.copy(isSaved = isSaved)
                }

                val filtered = booksWithSavedState.filter { book ->
                    genre == "All" || book.genre.equals(genre, ignoreCase = true)
                }

                val sorted = when (sort) {
                    BookSortOption.RECENTLY_ADDED -> filtered
                    BookSortOption.TITLE_AZ -> filtered.sortedBy { it.title.lowercase() }
                    BookSortOption.READING_PROGRESS -> filtered
                }

                ExploreSearchState.Success(sorted)
            }
            else -> searchState
        }

        ExploreUiState(
            searchQuery = query,
            selectedGenre = genre,
            sortOption = sort,
            searchState = mappedSearchState
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ExploreUiState()
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

    fun toggleExploreSaved(bookId: String) {
        _savedExploreBookIds.update { current ->
            if (current.contains(bookId)) {
                current - bookId
            } else {
                current + bookId
            }
        }
    }

    fun getBookDetailsFlow(bookId: String): Flow<ExploreBookDetailsState> = flow {
        emit(ExploreBookDetailsState.Loading)
        val cachedBook = googleBooksRepository.getBookFromCache(bookId)
        if (cachedBook != null) {
            val isSaved = _savedExploreBookIds.value.contains(cachedBook.id)
            emit(ExploreBookDetailsState.Success(cachedBook.copy(isSaved = isSaved)))
        } else {
            try {
                val remoteBook = googleBooksRepository.fetchBookById(bookId)
                val isSaved = _savedExploreBookIds.value.contains(remoteBook.id)
                emit(ExploreBookDetailsState.Success(remoteBook.copy(isSaved = isSaved)))
            } catch (t: Throwable) {
                emit(ExploreBookDetailsState.Error(getErrorMessage(t)))
            }
        }
    }

    private fun getErrorMessage(throwable: Throwable): String {
        return when (throwable) {
            is UnknownHostException -> "No Internet connection. Please check your connection and try again."
            is SocketTimeoutException -> "Request timeout. The server took too long to respond. Please try again."
            is IOException -> "No Internet connection. Please check your connection and try again."
            is HttpException -> {
                when (throwable.code()) {
                    429 -> "Too Many Requests. You have exceeded the API rate limit. Please try again later."
                    in 500..599 -> "Server error. Google Books servers are currently down. Please try again later."
                    else -> "HTTP error ${throwable.code()}. Please try again."
                }
            }
            else -> "An unexpected error occurred. Please try again."
        }
    }
}
