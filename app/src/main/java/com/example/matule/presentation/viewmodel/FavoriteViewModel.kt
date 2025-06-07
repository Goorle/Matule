package com.example.matule.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.matule.data.Repositories
import com.example.matule.domain.models.FavoritePublication
import com.example.matule.domain.models.FavoriteResponse
import com.example.matule.domain.models.UserCollection
import kotlinx.coroutines.launch

class FavoriteViewModel: ViewModel() {
    private val repositories = Repositories()
    var favoriteResponse by mutableStateOf<List<FavoriteResponse>>(listOf())
    private var userPublication by mutableStateOf<List<UserCollection>>(listOf())
    var isLoading by mutableStateOf(false)

    init {
        updateFavorite()
    }

    private fun updateFavorite() {
        try {
            viewModelScope.launch {
                isLoading = true

                val userId = repositories.getUserId()
                if (userId.isNotEmpty()) {
                    userPublication = repositories.getPublicationUser(userId)
                }

                favoriteResponse = repositories.getFavoritePublication()

                setUserNewspaper()
            }
        } catch (e: Exception) {

        } finally {
            isLoading = false
        }
    }

    fun setUserNewspaper() {
        favoriteResponse.forEach { favorite ->
            userPublication.forEach { publication ->
                if (favorite.publicationId.id == publication.publicationId) {
                    favorite.userCollection = publication
                }
            }
        }
    }

    private fun insertProduct() {

    }
}