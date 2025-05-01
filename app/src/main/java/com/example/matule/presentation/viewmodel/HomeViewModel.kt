package com.example.matule.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.matule.data.Repositories
import com.example.matule.domain.models.NewspaperResponse
import com.example.matule.domain.models.UserCollection
import kotlinx.coroutines.launch

class HomeViewModel: ViewModel() {
    private val repository = Repositories()
    private var newspaper by mutableStateOf<List<NewspaperResponse>>(listOf())
    private var userPublication by mutableStateOf<List<UserCollection>>(listOf())
    var isLoading by mutableStateOf(false)

    init {
        updateNewsPaper()
        updateUserPublication()
    }

    fun updateNewsPaper() {
        viewModelScope.launch {
            isLoading = true
            newspaper = repository.getNewsPapersWithDetails()
            setUserNewspaper()
            isLoading = false
        }
    }

    fun updateUserPublication() {
        viewModelScope.launch {
            val userId = repository.getUserId()
            if (userId.isNotEmpty()) {
                userPublication = repository.getPublicationUser(userId)
            }
        }
    }

    fun setUserNewspaper() {
        newspaper.forEach { newspaper ->
            userPublication.forEach { publication ->
                if (newspaper.publication != null)
                if (newspaper.publication.id == publication.publicationId) {
                    newspaper.userCollection = publication
                }
            }
        }
    }

    fun getNewsPaper(): List<NewspaperResponse> {
        return newspaper
    }


}