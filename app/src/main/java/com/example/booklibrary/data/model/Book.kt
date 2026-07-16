package com.example.booklibrary.data.model

data class Book(
    val id: String,
    val imageRes: Int,
    val title: String,
    val author: String,
    val genre: String,
    val rating: Float,
    val isSaved: Boolean = false,
    val currentChapter: Int? = null,
    val totalChapter: Int? = null,
    val imageUrl: String? = null,
    val description: String? = null,
    val publisher: String? = null,
    val publishedDate: String? = null,
    val language: String? = null,
    val pageCount: Int? = null,
    val previewLink: String? = null
)