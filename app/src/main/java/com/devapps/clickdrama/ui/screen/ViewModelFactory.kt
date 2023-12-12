package com.devapps.clickdrama.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.devapps.clickdrama.repository.Repository
import com.devapps.clickdrama.ui.screen.detail.DetailViewModel
import com.devapps.clickdrama.ui.screen.explore.ExploreViewModel
import com.devapps.clickdrama.ui.screen.home.HomeViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory (
    private val repository: Repository)
    : ViewModelProvider.NewInstanceFactory(){

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(ExploreViewModel::class.java)) {
            return ExploreViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}