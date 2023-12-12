package com.devapps.clickdrama.ui.screen.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devapps.clickdrama.model.response.DramaList
import com.devapps.clickdrama.repository.Repository
import com.devapps.clickdrama.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: Repository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<DramaList>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<DramaList>>>
        get() = _uiState

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query

    private val _searchResults = mutableStateOf<List<DramaList>>(emptyList())
    val searchResults: State<List<DramaList>> get() = _searchResults

    fun getAllDrama() {
        viewModelScope.launch {
            repository.getAllDrama()
                .catch { throwable ->
                    _uiState.value = UiState.Error(throwable.message.toString())
                }
                .collect { dramaList ->
                    _uiState.value = UiState.Success(dramaList)
                }
        }
    }

    fun searchDrama(query: String) {
        _query.value = query
        viewModelScope.launch {
            repository.searchDrama(_query.value)
                .catch { throwable ->
                    _uiState.value = UiState.Error(throwable.message.toString())
                }
                .collect { searchDrama ->
                    _uiState.value = UiState.Success(searchDrama)
                }
        }
    }

    fun updateDrama(id: Int, isFavorite: Boolean) {
        viewModelScope.launch {
            repository.updateDrama(id, isFavorite)
                .catch { throwable ->
                    _uiState.value = UiState.Error(throwable.message.toString())
                }
                .collect { isUpdated ->
                    if (isUpdated) getAllDrama()
                }
        }
    }
}
