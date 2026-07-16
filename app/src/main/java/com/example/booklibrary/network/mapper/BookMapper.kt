package com.example.booklibrary.network.mapper

import com.example.booklibrary.data.model.Book
import com.example.booklibrary.network.dto.GoogleBooksResponse

import com.example.booklibrary.network.dto.BookItemDto

object BookMapper {
    fun mapToDomain(dto: GoogleBooksResponse): List<Book> {
        val items = dto.items ?: return emptyList()
        return items.mapNotNull { item ->
            try {
                mapItemToDomain(item)
            } catch (e: Exception) {
                null
            }
        }.distinctBy { it.id }
    }

    fun mapItemToDomain(item: BookItemDto): Book {
        val info = item.volumeInfo ?: throw IllegalArgumentException("VolumeInfo is missing")
        val id = item.id
        val title = info.title ?: "Unknown Title"
        val author = info.authors?.joinToString(", ") ?: "Unknown Author"
        val genre = info.categories?.firstOrNull() ?: "General"
        val rating = info.averageRating ?: 0f
        
        // Map remote cover image links and convert HTTP to HTTPS
        val rawUrl = info.imageLinks?.thumbnail ?: info.imageLinks?.smallThumbnail
        val imageUrl = rawUrl?.replace("http://", "https://")

        return Book(
            id = id,
            imageRes = 0,
            title = title,
            author = author,
            genre = genre,
            rating = rating,
            isSaved = false,
            currentChapter = null,
            totalChapter = null,
            imageUrl = imageUrl,
            description = info.description,
            publisher = info.publisher,
            publishedDate = info.publishedDate,
            language = info.language,
            pageCount = info.pageCount,
            previewLink = info.previewLink
        )
    }
}
