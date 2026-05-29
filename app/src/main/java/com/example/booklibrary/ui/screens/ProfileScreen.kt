package com.example.booklibrary.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.booklibrary.ui.components.GenreChip
import com.example.booklibrary.ui.components.SectionTitle

@Composable
fun ProfileScreen() {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF0F111D),
                        Color(0xFF1A1B2F),
                        Color(0xFF121212)
                    )
                )
            )
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
        ) {

            Spacer(modifier = Modifier.height(60.dp))

            SectionTitle("Profile")

            Spacer(modifier = Modifier.height(28.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF7C4DFF)),
                    contentAlignment = Alignment.Center
                ) {

                    Text(
                        text = "A",
                        color = Color.White,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column {

                    Text(
                        text = "Apoorva Deep",
                        color = Color.White,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "BookAura Reader",
                        color = Color(0xFFB0B0B0)
                    )
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            Card(
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF1E2235)
                )
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),

                    horizontalArrangement =
                        Arrangement.SpaceBetween
                ) {

                    ProfileStat(
                        number = "24",
                        label = "Read"
                    )

                    ProfileStat(
                        number = "12",
                        label = "Saved"
                    )

                    ProfileStat(
                        number = "7",
                        label = "Streak"
                    )
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            SectionTitle("Favorite Genres")

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                horizontalArrangement =
                    Arrangement.spacedBy(12.dp)
            ) {

                GenreChip("Fantasy")
                GenreChip("Finance")
                GenreChip("Self Help")
            }

            Spacer(modifier = Modifier.height(30.dp))

            SectionTitle("Settings")

            Spacer(modifier = Modifier.height(16.dp))

            ProfileSettingItem(
                icon = Icons.Default.Person,
                title = "Account"
            )

            ProfileSettingItem(
                icon = Icons.Default.Notifications,
                title = "Notifications"
            )

            ProfileSettingItem(
                icon = Icons.Default.Info,
                title = "About"
            )
        }
    }
}

@Composable
fun ProfileStat(
    number: String,
    label: String
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = number,
            color = Color.White,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = label,
            color = Color(0xFFB0B0B0)
        )
    }
}

@Composable
fun ProfileSettingItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp),

        shape = RoundedCornerShape(20.dp),

        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1E2235)
        )
    ) {

        Row(
            modifier = Modifier.padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color.White
            )

            Spacer(modifier = Modifier.width(14.dp))

            Text(
                text = title,
                color = Color.White
            )
        }
    }
}