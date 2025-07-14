package com.mafusaik.characterdetail.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import coil.compose.rememberAsyncImagePainter
import com.mafusaik.characters.domain.model.Character
import com.mafusaik.characters.presentation.ui.components.GenderBadge
import com.mafusaik.characters.presentation.ui.components.StatusBadge


@Composable
fun ImageContent(character: Character) {
    BoxWithConstraints(
        modifier = Modifier
            .wrapContentSize()
            .clip(RoundedCornerShape(24.dp))
    ) {
        val smallestSide = min(maxWidth, maxHeight) * 0.8f

        Image(
            painter = rememberAsyncImagePainter(character.image),
            contentDescription = character.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(smallestSide)
                .aspectRatio(1f)
                .border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(24.dp))
        )

        StatusBadge(
            Modifier.align(Alignment.TopEnd),
            scale = 1.5f,
            status = character.status
        )
        GenderBadge(
            Modifier.align(Alignment.BottomEnd),
            scale = 1.5f,
            gender = character.gender
        )
    }
}