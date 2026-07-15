package com.example.booklibrary.ui.components
import androidx.compose.runtime.Composable
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
import androidx.navigation.NavHostController
import com.example.booklibrary.navigation.Screen
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.booklibrary.ui.theme.BottomNavBackground
import com.example.booklibrary.ui.theme.BrandPurple

data class BottomNavItem(
    val label: String,
    val icon: ImageVector,
    val route: String
)
@Composable
fun BottomNavBar(
    navController: NavHostController
){
    val items = listOf(
        BottomNavItem(
            "Home",
            Icons.Default.Home,
            Screen.Home.route
        ),

        BottomNavItem(
            "Explore",
            Icons.Default.Explore,
            Screen.Explore.route
        ),

        BottomNavItem(
            "Saved",
            Icons.Default.Bookmark,
            Screen.Saved.route
        ),

        BottomNavItem(
            "Profile",
            Icons.Default.Person,
            Screen.Profile.route
        )
    )

    val navBackStackEntry =
        navController.currentBackStackEntryAsState()

    val currentRoute =
        navBackStackEntry.value?.destination?.route

    NavigationBar(
        containerColor = BottomNavBackground,
        tonalElevation = 0.dp
    ) {
        items.forEachIndexed { index, item ->

            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                        restoreState = true
                    }
                },
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
                    selectedIconColor = BrandPurple,
                    selectedTextColor = BrandPurple,
                    unselectedIconColor = Color.Gray,
                    unselectedTextColor = Color.Gray,
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}