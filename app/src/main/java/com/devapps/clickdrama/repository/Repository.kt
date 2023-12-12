package com.devapps.clickdrama.repository

import com.devapps.clickdrama.model.local.DramaDataSource
import com.devapps.clickdrama.model.response.DramaList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class Repository {

    private val drama = mutableListOf<DramaList>()

    init {
        if (drama.isEmpty()) {
            DramaDataSource.dramaList.forEach {
                drama.add(DramaList(it, false))
            }
        }
    }

    fun getAllDrama(): Flow<List<DramaList>> {
        return flow {
            emit(drama)
        }
    }

    fun getDramaById(dramaId: Int): Flow<DramaList> {
        return flowOf(drama.first {
            it.drama.id == dramaId
        })
    }

    fun getFavoriteDrama(): Flow<List<DramaList>> {
        return getAllDrama().map { drama ->
            drama.filter {
                it.isFavorite
            }
        }
    }

    fun searchDrama(query: String): Flow<List<DramaList>> {
        return getAllDrama().map { drama ->
            drama.filter {
                it.drama.title.contains(query, ignoreCase = true)
            }
        }
    }

    fun updateDrama(id: Int, isFavorite: Boolean): Flow<Boolean> {
        val index = drama.indexOfFirst { it.drama.id == id }
        val result = if (index >= 0) {
            drama[index] = drama[index].copy(isFavorite = isFavorite)
            true
        } else {
            false
        }
        return flowOf(result)
    }

    companion object {
        @Volatile
        private var instance: Repository? = null

        fun getInstance(): Repository =
            instance ?: synchronized(this) {
                Repository().apply {
                    instance = this
                }
            }
    }
}