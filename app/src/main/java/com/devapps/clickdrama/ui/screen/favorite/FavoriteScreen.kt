package com.devapps.clickdrama.ui.screen.favorite

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.devapps.clickdrama.R
import com.devapps.clickdrama.di.Injection
import com.devapps.clickdrama.model.response.DramaList
import com.devapps.clickdrama.ui.component.CardFavDrama
import com.devapps.clickdrama.ui.component.EmptyContent
import com.devapps.clickdrama.ui.screen.ViewModelFactory
import com.devapps.clickdrama.ui.screen.detail.DetailViewModel
import com.devapps.clickdrama.ui.theme.ClickDramaTheme

@Composable
fun FavoriteScreen(
    viewModel: DetailViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    ),
    navigateToDetail: (Int) -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val favoriteDrama by viewModel.getFavoriteDrama().collectAsState(emptyList())

    if (favoriteDrama.isEmpty()) {
        EmptyContent(
            contentText = stringResource(R.string.empty_content),
        )
    }
    else {
        FavoriteContent(
            listDrama = favoriteDrama,
            onFavoriteIconClicked = { id, newState ->
                viewModel.updateFavorite(id, newState)
            },
            navigateToDetail = navigateToDetail,
            navigateBack = navigateBack,
            modifier = modifier,
        )
    }
}

@Composable
fun FavoriteContent(
    listDrama: List<DramaList>,
    onFavoriteIconClicked: (Int, Boolean) -> Unit,
    navigateToDetail: (Int) -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(),
            color = MaterialTheme.colorScheme.primary
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = navigateBack) {
                    Icon(
                        imageVector = Icons.Rounded.ArrowBack,
                        contentDescription = stringResource(R.string.back),
                        tint = MaterialTheme.colorScheme.onPrimary)
                }

                Spacer(modifier = Modifier.width(16.dp))

                Text(
                    text = stringResource(R.string.favorite),
                    fontSize = 24.sp,
                    color = MaterialTheme.colorScheme.onPrimary,
                )
            }
        }

        listDrama.forEach { item ->
            CardFavDrama(
                id = item.drama.id,
                image = item.drama.image,
                title = item.drama.title,
                year = item.drama.year,
                genre = item.drama.genre,
                isFavorite = item.isFavorite,
                onFavoriteIconClicked = { id, newState ->
                    onFavoriteIconClicked(id, newState)
                },
                modifier = Modifier
                    .clickable { navigateToDetail(item.drama.id) }

            )
        }
    }
}

@Preview
@Composable
fun FavoriteScreenPreview() {
    ClickDramaTheme {
        FavoriteScreen(
            navigateToDetail = {},
            navigateBack = {},
        )
    }
}