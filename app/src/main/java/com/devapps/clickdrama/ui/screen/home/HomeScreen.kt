package com.devapps.clickdrama.ui.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import com.devapps.clickdrama.R
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.devapps.clickdrama.di.Injection
import com.devapps.clickdrama.model.response.DramaList
import com.devapps.clickdrama.ui.common.UiState
import com.devapps.clickdrama.ui.component.CardDrama
import com.devapps.clickdrama.ui.component.EmptyContent
import com.devapps.clickdrama.ui.component.Search
import com.devapps.clickdrama.ui.screen.ViewModelFactory
import com.devapps.clickdrama.ui.screen.explore.DramaGrid
import com.devapps.clickdrama.ui.theme.ClickDramaTheme
import java.util.Random

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
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
                ClickDramaTheme {
                    HomeContent(
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
            }
            is UiState.Error -> {
            }
        }
    }
}

@Composable
fun HomeContent(
    query: String,
    onQueryChange: (String) -> Unit,
    listDrama: List<DramaList>,
    onFavoriteIconClicked: (Int, Boolean) -> Unit,
    navigateToDetail: (Int) -> Unit,
    navigateToAbout: () -> Unit,
    modifier: Modifier = Modifier
) {
    val trendingDrama by remember {
        mutableStateOf(listDrama.shuffled(Random(System.currentTimeMillis())).take(7))
    }

    val latestDrama by remember {
        mutableStateOf(listDrama.shuffled(Random(System.currentTimeMillis() + 1)).take(7))
    }

    val recommendationDrama by remember {
        mutableStateOf(listDrama.shuffled(Random(System.currentTimeMillis() + 2)).take(7))
    }

    Column {
        Search(
            query = query,
            onQueryChange = onQueryChange,
            navigateToAbout = navigateToAbout
        )

        if (query.isNotEmpty()) {
            DramaGrid(
                listDrama = listDrama,
                onFavoriteIconClicked = onFavoriteIconClicked,
                navigateToDetail = navigateToDetail,
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(6.dp)
            ) {
                item {
                    SectionText(stringResource(R.string.section_trending))
                    DramaRow(
                        listDrama = trendingDrama,
                        onFavoriteIconClicked = onFavoriteIconClicked,
                        navigateToDetail = navigateToDetail
                    )
                }

                item {
                    SectionText(stringResource(R.string.section_latest))
                    DramaRow(
                        listDrama = latestDrama,
                        onFavoriteIconClicked = onFavoriteIconClicked,
                        navigateToDetail = navigateToDetail
                    )
                }

                item {
                    SectionText(stringResource(R.string.section_recommendation))
                    DramaRow(
                        listDrama = recommendationDrama,
                        onFavoriteIconClicked = onFavoriteIconClicked,
                        navigateToDetail = navigateToDetail
                    )
                }

                if (listDrama.isEmpty()) {
                    item {
                        EmptyContent(
                            contentText = stringResource(R.string.empty_content),
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun DramaRow(
    listDrama: List<DramaList>,
    onFavoriteIconClicked: (Int, Boolean) -> Unit,
    navigateToDetail: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
        modifier = modifier
    ) {
        items(listDrama) { item ->
            CardDrama(
                id = item.drama.id,
                title = item.drama.title,
                image = item.drama.image,
                year = item.drama.year,
                isFavorite = item.isFavorite,
                onFavoriteIconClicked = onFavoriteIconClicked,
                modifier = Modifier
                    .clickable { navigateToDetail(item.drama.id) }
            )
        }
    }
}

@Composable
fun SectionText(
    title: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = title,
        style = MaterialTheme.typography.headlineMedium.copy(
            fontWeight = FontWeight.ExtraBold
        ),
        color = MaterialTheme.colorScheme.onBackground,
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
    )
}