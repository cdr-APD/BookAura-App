package com.example.booklibrary.ui.components

import android.R.attr.label
import androidx.compose.runtime.Composable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

data class BottomNavItem(
    val label: String,
    val icon: ImageVector
)
@Composable
fun BottomNavBar(){
    val items = listOf(
        BottomNavItem("Home", Icons.Default.Home),
        BottomNavItem("Explore", Icons.Default.Explore),
        BottomNavItem("Saved", Icons.Default.Bookmark),
        BottomNavItem("Profile", Icons.Default.Person)
    )
    NavigationBar(
        containerColor = Color(0xFF1B1E2F),
        tonalElevation = 0.dp
    ) {
        items.forEachIndexed { index, item ->

            NavigationBarItem(
                selected = index == 0,
                onClick = { },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label
                    )
                },
                label = {
                    Text(text = item.label)
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xFF7C4DFF),
                    selectedTextColor = Color(0xFF7C4DFF),
                    unselectedIconColor = Color.Gray,
                    unselectedTextColor = Color.Gray,
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}