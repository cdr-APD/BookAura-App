package com.example.booklibrary.ui.screens

import android.R.attr.rating
import android.R.attr.text
import com.example.booklibrary.data.model.Book
import com.example.booklibrary.ui.components.BookCard
import com.example.booklibrary.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
@Composable
fun HomeScreen(modifier: Modifier = Modifier){
    val books = listOf(
        Book(R.drawable.primal_hunter_1, "Primal Hunter", "Fantasy", 4.8f),
        Book(R.drawable.atomic_habits, "Atomic Habits", "Self Help", 4.7f),
        Book(R.drawable.rich_dad, "Rich Dad Poor Dad", "Finance", 4.5f),
        Book(R.drawable.eternal_thief, "Eternal Thief", "Fantasy", 4.6f),
        Book(R.drawable.deep_work, "Deep Work", "Productivity", 4.4f),
        Book(R.drawable.complete_martial, "Complete Martial Arts Attribute", "Fiction", 4.3f)
    )
    Box(modifier = Modifier
        .fillMaxSize()
        .background(
            brush = Brush.verticalGradient(
                colors = listOf(
                    Color(0xFF0F111D),
                    Color(0xFF1A1B2F),
                    Color(0xFF121212)
                )
            )
        )
    ){
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ){
            item(
                span = { GridItemSpan(2) }
            ){
                Spacer(modifier = Modifier.height(20.dp))
                GreetingSection()
            }

            item(
                span = { GridItemSpan(2) }
            ){
                Spacer(modifier = Modifier.height(20.dp))
                CurrentlyReadingCard()
            }
            item(
                span = { GridItemSpan(2) }
            ) {

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "For You ",
                    color = Color.White,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
            }
            items(books) { book ->

                BookCard(
                    modifier = Modifier.fillMaxWidth(),
                    imageRes = book.imageRes,
                    title = book.title,
                    genre = book.genre,
                    rating = book.rating
                )
            }

        }
    }
}

@Composable
fun GreetingSection(
    name: String = "Apoorva"
){
    Column {
        Text(
            text = "Good Evening, $name",
            color = Color.White,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Pick up where you left off...",
            color = Color(0xFFB0B0B0),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
fun CurrentlyReadingCard(){
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1E2235)
        )
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ){
            Image(
                painter = painterResource(R.drawable.primal_hunter_1),
                contentDescription = "Book Cover",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(120.dp)
                    .aspectRatio(0.65f)
                    .clip(RoundedCornerShape(16.dp))
            )
            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.SpaceBetween
            ){
                Text(
                    text = "The Primal Hunter",
                    color = Color.White,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Zogarth",
                    color = Color(0xFFB0B0B0),
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "90% completed",
                    color = Color(0xFFB0B0B0),
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(15.dp))

                LinearProgressIndicator(
                    progress = { 0.90f },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp),
                    color = Color(0xFF7C4DFF),
                    trackColor = Color(0xFF3A3F5A)
                )

                Spacer(modifier = Modifier.height(15.dp))

                Button(
                    onClick = { },
                    modifier = Modifier.fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF7C4DFF)
                    ),
                    shape = RoundedCornerShape(15.dp)
                ){
                    Text(
                        text = "Continue Reading",
                        color = Color.White,
                        fontSize = 18.sp
                    )
                }
            }
        }
    }
}

//@Composable
//fun RecommendationSection(){
//    val books = listOf(
//        Book(R.drawable.primal_hunter_1, "Primal Hunter", "Fantasy", 4.8f),
//        Book(R.drawable.atomic_habits, "Atomic Habits", "Self Help", 4.7f),
//        Book(R.drawable.rich_dad, "Rich Dad Poor Dad", "Finance", 4.5f),
//        Book(R.drawable.eternal_thief, "Eternal Thief", "Fantasy", 4.6f),
//        Book(R.drawable.deep_work, "Deep Work", "Productivity", 4.4f),
//        Book(R.drawable.complete_martial, "Complete Martial Arts Attribute", "Fiction", 4.3f)
//    )
//    Column(
//
//    ) {
//        Text(
//            text = "For You",
//            color = Color.White,
//            style = MaterialTheme.typography.headlineMedium,
//            fontWeight = FontWeight.Bold
//        )
//
//        Spacer(modifier = Modifier.height(20.dp))
//
//        LazyVerticalGrid(
//            columns = GridCells.Fixed(2),
//            verticalArrangement = Arrangement.spacedBy(16.dp),
//            horizontalArrangement = Arrangement.spacedBy(16.dp),
//            modifier = Modifier.height(800.dp)
//        ) {
//            items(books) { book ->
//
//                BookCard(
//                    imageRes = book.imageRes,
//                    title = book.title,
//                    genre = book.genre,
//                    rating = book.rating
//                )
//            }
//        }
//    }
//
//
//
//}
