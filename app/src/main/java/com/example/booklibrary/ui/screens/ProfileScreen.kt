package com.example.booklibrary.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.booklibrary.ui.components.GenreChip
import com.example.booklibrary.ui.components.SectionTitle
import com.example.booklibrary.ui.theme.BackgroundDarkCenter
import com.example.booklibrary.ui.theme.BackgroundDarkEnd
import com.example.booklibrary.ui.theme.BackgroundDarkStart
import com.example.booklibrary.ui.theme.BrandPurple
import com.example.booklibrary.ui.theme.CardBackground
import com.example.booklibrary.ui.theme.TextPrimary
import com.example.booklibrary.ui.theme.TextSecondary
import com.example.booklibrary.viewmodel.BookViewModel

@Composable
fun ProfileScreen(
    viewModel: BookViewModel,
    bottomPadding: Dp = 0.dp
) {
    val stats by viewModel.profileStats.collectAsStateWithLifecycle()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        BackgroundDarkStart,
                        BackgroundDarkCenter,
                        BackgroundDarkEnd
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(start = 20.dp, end = 20.dp, bottom = bottomPadding + 20.dp)
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
                        .background(BrandPurple),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "A",
                        color = TextPrimary,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text(
                        text = "Apoorva Deep",
                        color = TextPrimary,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "BookAura Reader",
                        color = TextSecondary
                    )
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            Card(
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(
                    containerColor = CardBackground
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    ProfileStat(
                        number = stats.completed,
                        label = "Read"
                    )

                    ProfileStat(
                        number = stats.booksSaved,
                        label = "Saved"
                    )

                    ProfileStat(
                        number = stats.streak,
                        label = "Streak"
                    )
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            SectionTitle("Favorite Genres")

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
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
            color = TextPrimary,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = label,
            color = TextSecondary
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
            containerColor = CardBackground
        )
    ) {
        Row(
            modifier = Modifier.padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = TextPrimary
            )

            Spacer(modifier = Modifier.width(14.dp))

            Text(
                text = title,
                color = TextPrimary
            )
        }
    }
}