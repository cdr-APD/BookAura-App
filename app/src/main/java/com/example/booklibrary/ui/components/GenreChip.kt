package com.example.booklibrary.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun GenreChip(
    genre: String,
    isSelected: Boolean = false,
    onClick: () -> Unit = {}
){
    Text(
        text = genre,
        modifier = Modifier
            .background(
                color = if(isSelected)
                    Color(0xFF7C4DFF)
                else
                    Color(0xFF1E2235),
                shape = RoundedCornerShape(18.dp)
            )
            .border(
                width = 1.dp,
                color = if(isSelected)
                    Color(0xFF7C4DFF)
                else
                    Color(0xFF2E334A),

                shape = RoundedCornerShape(18.dp)
            )
            .clickable {
                onClick()
            }
            .padding(
                horizontal = 18.dp,
                vertical = 12.dp
            ),
        color = Color.White,
        fontWeight = FontWeight.Medium
    )
}