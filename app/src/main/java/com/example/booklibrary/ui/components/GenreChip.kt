package com.example.booklibrary.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.booklibrary.ui.theme.BorderDark
import com.example.booklibrary.ui.theme.BrandPurple
import com.example.booklibrary.ui.theme.CardBackground
import com.example.booklibrary.ui.theme.TextPrimary

@Composable
fun GenreChip(
    genre: String,
    isSelected: Boolean = false,
    onClick: () -> Unit = {}
){
    val shape = RoundedCornerShape(18.dp)
    Text(
        text = genre,
        modifier = Modifier
            .border(
                width = 1.dp,
                color = if(isSelected) BrandPurple else BorderDark,
                shape = shape
            )
            .clip(shape)
            .background(
                color = if(isSelected) BrandPurple else CardBackground
            )
            .clickable {
                onClick()
            }
            .padding(
                horizontal = 18.dp,
                vertical = 12.dp
            ),
        color = TextPrimary,
        fontWeight = FontWeight.Medium
    )
}