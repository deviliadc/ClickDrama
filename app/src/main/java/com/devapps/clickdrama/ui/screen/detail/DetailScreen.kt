package com.devapps.clickdrama.ui.screen.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.devapps.clickdrama.di.Injection
import com.devapps.clickdrama.ui.common.UiState
import com.devapps.clickdrama.ui.screen.ViewModelFactory
import com.devapps.clickdrama.ui.theme.ClickDramaTheme
import com.devapps.clickdrama.R
import com.devapps.clickdrama.model.response.DramaList
import com.devapps.clickdrama.ui.component.EmptyContent

@Composable
fun DetailScreen(
    id: Int,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    )
) {
    val uiState by viewModel.uiState.collectAsState()

    when (uiState) {
        is UiState.Loading -> {
            viewModel.getDramaById(id)
        }
        is UiState.Success -> {
            val data = (uiState as UiState.Success<DramaList>).data
            DetailContent(
                id = data.drama.id,
                image = data.drama.image,
                title = data.drama.title,
                year = data.drama.year,
                episode = data.drama.episode,
                rating = data.drama.rating,
                genre = data.drama.genre,
                description = data.drama.description,
                isFavorite = data.isFavorite,
                onFavoriteIconClicked = { id, newState ->
                    viewModel.updateFavorite(id, newState)
//                    isFavorite = newState
                },
                navigateBack = navigateBack,
                modifier = modifier
            )
        }
        is UiState.Error -> {
        }
    }
}

@Composable
fun DetailContent(
    id: Int,
    image: Int,
    title: String,
    year: String,
    episode: String,
    rating: String,
    genre: String,
    description: String,
    isFavorite: Boolean,
    onFavoriteIconClicked: (Int, Boolean) -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val favoriteIcon =
        if (isFavorite) Icons.Rounded.Favorite else Icons.Rounded.FavoriteBorder

    Column{
        Surface(
            modifier = Modifier
                .fillMaxWidth(),
            color = MaterialTheme.colorScheme.primary
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
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
                    text = stringResource(R.string.detail),
                    fontSize = 24.sp,
                    color = MaterialTheme.colorScheme.onPrimary,
                )
            }
        }

        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.headlineMedium,
            maxLines = 1,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
            overflow = TextOverflow.Ellipsis
        )

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ){
            Image(
                painter = painterResource(image),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(180.dp)
                    .size(120.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .align(Alignment.CenterVertically)
            )

            Spacer(
                modifier = modifier
                    .padding(6.dp)
            )

            Column {
                Text(
                    text = "Year: $year",
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onBackground,
                )

                Spacer(
                    modifier = modifier
                        .padding(2.dp)
                )

                Text(
                    text = "Episode: $episode",
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onBackground,
                )

                Spacer(
                    modifier = modifier
                        .padding(2.dp)
                )

                Text(
                    text = "Rating: $rating",
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onBackground,
                )

                Spacer(
                    modifier = modifier
                        .padding(2.dp)
                )

                Text(
                    text = "Genre: $genre",
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 3,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .height(70.dp)
                        .fillMaxWidth()
                )

                Spacer(
                    modifier = modifier
                        .padding(2.dp)
                )

                IconButton(
                    onClick = {
                        onFavoriteIconClicked(id, !isFavorite)
                    },
                    modifier = Modifier
                        .align(Alignment.End)
                ) {
                    Icon(
                        imageVector = favoriteIcon,
                        contentDescription = if (!isFavorite) stringResource(R.string.add_favorite)
                        else stringResource(R.string.remove_favorite),
                        tint = if (!isFavorite) Color.Gray else Color.Red,
                    )
                }
            }
        }

        Spacer(
            modifier = modifier
                .padding(4.dp)
        )

        Text(
            text = "Description",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .padding(16.dp)
        )

        Spacer(
            modifier = modifier
                .padding(2.dp)
        )

        Text(
            text = description,
            style = MaterialTheme.typography.bodyMedium,
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .padding(vertical = 6.dp, horizontal = 16.dp)
                .fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DetailScreenPreview() {
    ClickDramaTheme {
        DetailScreen(id = 0, navigateBack = {})
    }
}