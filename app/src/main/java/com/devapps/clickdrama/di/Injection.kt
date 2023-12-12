package com.devapps.clickdrama.di

import com.devapps.clickdrama.repository.Repository

object Injection {
    fun provideRepository(): Repository {
        return Repository.getInstance()
    }
}