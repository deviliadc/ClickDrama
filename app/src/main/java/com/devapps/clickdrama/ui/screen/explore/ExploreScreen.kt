package com.devapps.clickdrama.ui.screen.explore

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.devapps.clickdrama.R
import com.devapps.clickdrama.di.Injection
import com.devapps.clickdrama.model.response.DramaList
import com.devapps.clickdrama.ui.common.UiState
import com.devapps.clickdrama.ui.component.CardDrama
import com.devapps.clickdrama.ui.component.EmptyContent
import com.devapps.clickdrama.ui.component.Search
import com.devapps.clickdrama.ui.screen.ViewModelFactory
import com.devapps.clickdrama.ui.theme.ClickDramaTheme

@Composable
fun ExploreScreen(
    modifier: Modifier = Modifier,
    viewModel: ExploreViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (Int) -> Unit,
    navigateToAbout: () -> Unit,
) {
    var searchQuery by remember { mutableStateOf("") }

    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getAllDrama()
            }
            is UiState.Success -> {
                ExploreContent(
                    query = searchQuery,
                    onQueryChange = { newQuery ->
                        searchQuery = newQuery
                        if (newQuery.isNotEmpty()) {
                            viewModel.searchDrama(newQuery)
                        } else {
                            viewModel.getAllDrama()
                        }
                    },
                    listDrama = uiState.data,
                    onFavoriteIconClicked = { id, newState ->
                        viewModel.updateDrama(id, newState)
                    },
                    navigateToDetail = navigateToDetail,
                    navigateToAbout = navigateToAbout,
                )
            }
            is UiState.Error -> {
                EmptyContent(
                    contentText = stringResource(R.string.empty_content),
                )
            }
        }
    }
}

@Composable
fun ExploreContent(
    query: String,
    onQueryChange: (String) -> Unit,
    listDrama: List<DramaList>,
    onFavoriteIconClicked: (Int, Boolean) -> Unit,
    navigateToDetail: (Int) -> Unit,
    navigateToAbout: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Search(
            query = query,
            onQueryChange = onQueryChange,
            navigateToAbout = navigateToAbout
        )
        if (listDrama.isNotEmpty()) {
            DramaGrid(
                listDrama = listDrama,
                onFavoriteIconClicked = onFavoriteIconClicked,
                navigateToDetail = navigateToDetail,
            )
        } else {
            EmptyContent(
                contentText = stringResource(R.string.empty_content),
            )
        }
    }
}

@Composable
fun DramaGrid(
    listDrama: List<DramaList>,
    onFavoriteIconClicked: (Int, Boolean) -> Unit,
    navigateToDetail: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(100.dp),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        items(listDrama) { item ->
            CardDrama(
                id = item.drama.id,
                title = item.drama.title,
                image = item.drama.image,
                year = item.drama.year,
//                rating = item.drama.rating,
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
