package com.devapps.clickdrama.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devapps.clickdrama.model.response.DramaList
import com.devapps.clickdrama.repository.Repository
import com.devapps.clickdrama.ui.common.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class DetailViewModel(
    private val repository: Repository
): ViewModel() {

    private val _uiState: MutableStateFlow<UiState<DramaList>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<DramaList>>
        get() = _uiState

    fun updateFavorite(id: Int, isFavorite: Boolean) = viewModelScope.launch {
        repository.updateDrama(id, isFavorite)
            .collect { isUpdated ->
                if (isUpdated) {
                    getDramaById(id)
                }
            }
    }

    fun getDramaById(id: Int) = viewModelScope.launch {
        repository.getDramaById(id)
            .catch { exception ->
                _uiState.value = UiState.Error(exception.message.toString())
            }
            .collect { drama ->
                _uiState.value = UiState.Success(drama)
            }
    }

    fun getFavoriteDrama(): Flow<List<DramaList>> {
        return repository.getFavoriteDrama()
    }
}