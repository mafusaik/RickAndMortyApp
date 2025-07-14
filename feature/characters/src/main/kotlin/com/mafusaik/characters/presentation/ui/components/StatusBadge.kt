package com.mafusaik.characters.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.mafusaik.characters.R
import com.mafusaik.characters.presentation.theme.*

@Composable
fun StatusBadge(modifier: Modifier, scale: Float = 1f, status: String) {
    val (color, textRes) = when (status.lowercase()) {
        "alive" -> Green to R.string.status_alive
        "dead" -> Red to R.string.status_dead
        else -> White to R.string.status_unknown
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(Black.copy(alpha = 0.4f))
            .padding(horizontal = 12.dp * scale, vertical = 6.dp)
    ) {
        Box(
            modifier = Modifier
                .size(8.dp * scale)
                .clip(CircleShape)
                .background(color)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = stringResource(id = textRes),
            color = color,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontSize = MaterialTheme.typography.bodyMedium.fontSize * scale
            )
        )
    }
}