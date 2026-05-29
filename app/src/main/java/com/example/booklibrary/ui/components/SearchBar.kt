package com.example.booklibrary.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SearchBar(
    modifier: Modifier = Modifier
) {

    val searchText = remember {
        mutableStateOf("")
    }

    OutlinedTextField(
        value = searchText.value,
        onValueChange = {
            searchText.value = it
        },

        modifier = modifier.fillMaxWidth(),

        placeholder = {
            Text(
                text = "Search books...",
                color = Color.Gray
            )
        },

        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
                tint = Color.Gray
            )
        },

        shape = RoundedCornerShape(22.dp),

        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color(0xFF1E2235),
            unfocusedContainerColor = Color(0xFF1E2235),

            focusedBorderColor = Color(0xFF7C4DFF),
            unfocusedBorderColor = Color.Transparent,

            cursorColor = Color.White,
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White
        )
    )
}