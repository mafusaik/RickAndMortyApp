package com.mafusaik.characters.presentation.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import com.mafusaik.characters.presentation.theme.*

@Composable
fun TextDescription(text: String){
    Text(
        text = text,
        style = MaterialTheme.typography.bodyMedium,
        fontWeight = FontWeight.Medium,
        color = Gray,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}