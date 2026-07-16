package com.example.booklibrary.network.dto

import com.google.gson.annotations.SerializedName

data class GoogleBooksResponse(
    @SerializedName("items") val items: List<BookItemDto>?
)

data class BookItemDto(
    @SerializedName("id") val id: String,
    @SerializedName("volumeInfo") val volumeInfo: VolumeInfoDto?
)

data class VolumeInfoDto(
    @SerializedName("title") val title: String?,
    @SerializedName("authors") val authors: List<String>?,
    @SerializedName("categories") val categories: List<String>?,
    @SerializedName("averageRating") val averageRating: Float?,
    @SerializedName("imageLinks") val imageLinks: ImageLinksDto?,
    @SerializedName("description") val description: String?,
    @SerializedName("publisher") val publisher: String?,
    @SerializedName("publishedDate") val publishedDate: String?,
    @SerializedName("language") val language: String?,
    @SerializedName("pageCount") val pageCount: Int?,
    @SerializedName("previewLink") val previewLink: String?
)

data class ImageLinksDto(
    @SerializedName("smallThumbnail") val smallThumbnail: String?,
    @SerializedName("thumbnail") val thumbnail: String?
)
