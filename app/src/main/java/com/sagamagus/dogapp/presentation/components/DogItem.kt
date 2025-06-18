package com.sagamagus.dogapp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.sagamagus.dogapp.domain.model.DogModel


@Composable
fun DogItem(dog: DogModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = com.sagamagus.dogapp.ui.theme.Card // o cualquier color que desees
        ),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,

        ) {
            Image(
                modifier = Modifier
                    .width(160.dp)
                    .height(240.dp)
                    .clip(RoundedCornerShape(16.dp)),
                painter = rememberAsyncImagePainter(dog.image),
                contentDescription = dog.dogName,
                contentScale = ContentScale.FillBounds
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(end = 8.dp)
            ) {
                Text(text = dog.dogName, style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = dog.description, style = MaterialTheme.typography.bodySmall, maxLines = 3)
                Spacer(modifier = Modifier.height(15.dp))
                Text(text = "Almost ${dog.age} years", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}