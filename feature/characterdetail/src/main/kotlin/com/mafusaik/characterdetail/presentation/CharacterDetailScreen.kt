package com.mafusaik.characterdetail.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.IconButton
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowWidthSizeClass
import com.mafusaik.characterdetail.presentation.ui.ImageContent
import com.mafusaik.characterdetail.presentation.ui.TextContent
import com.mafusaik.characters.R
import com.mafusaik.characters.domain.model.Character

@Composable
fun CharacterDetailScreen(
    character: Character,
    onBackClick: () -> Unit
) {
    val topPadding = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val isHorizontal = windowSizeClass.windowWidthSizeClass != WindowWidthSizeClass.COMPACT

    val modifier = Modifier
        .fillMaxSize()
        .padding(top = topPadding)
        .padding(horizontal = 16.dp)

    Box {
        IconButton(
            modifier = Modifier.padding(top = topPadding),
            onClick = onBackClick
        ) {
            Image(
                painter = painterResource(id = R.drawable.icon_back),
                contentDescription = "Назад"
            )
        }

        if (isHorizontal) {
            HorizontalLayout(modifier, character)
        } else {
            VerticalLayout(modifier, character)
        }
    }

}

@Composable
private fun VerticalLayout(modifier: Modifier, character: Character) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ImageContent(character)
        Spacer(modifier = Modifier.height(16.dp))
        TextContent(character)
    }
}

@Composable
private fun HorizontalLayout(modifier: Modifier, character: Character) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        ImageContent(character)
        TextContent(character)
    }
}


