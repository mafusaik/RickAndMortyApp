package com.mafusaik.characters.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.mafusaik.characters.R
import com.mafusaik.characters.domain.model.Character
import com.mafusaik.characters.presentation.ui.components.GenderBadge
import com.mafusaik.characters.presentation.ui.components.StatusBadge
import com.mafusaik.characters.presentation.ui.components.TextDescription

@Composable
fun CharacterItem(
    modifier: Modifier,
    character: Character,
    onClick: (Character) -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(6.dp)
            .wrapContentHeight()
            .clickable { onClick(character) },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Box(modifier = Modifier.clip(RoundedCornerShape(16.dp))) {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(16.dp)),
                painter = rememberAsyncImagePainter(character.image),
                contentDescription = character.name,
                contentScale = ContentScale.Crop
            )
            StatusBadge(
                Modifier.align(Alignment.TopEnd),
                status = character.status
            )

            GenderBadge(
                modifier = Modifier.align(Alignment.BottomEnd),
                gender = character.gender
            )
        }

        Spacer(modifier = Modifier.height(4.dp))
        Column(
            modifier = Modifier.padding(horizontal = 8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = character.name,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            TextDescription(stringResource(R.string.species_label, character.species))
            TextDescription(stringResource(R.string.origin_label, character.origin))
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}