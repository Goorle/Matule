package com.example.matule.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.matule.data.Repositories
import com.example.matule.domain.models.NewspaperResponse
import kotlinx.coroutines.launch

class HomeViewModel: ViewModel() {
    private val repository = Repositories()
    private var newspaper by mutableStateOf<List<NewspaperResponse>>(listOf())
    var isLoading by mutableStateOf(false)

    init {
        updateNewsPaper()
    }

    fun updateNewsPaper() {
        viewModelScope.launch {
            isLoading = true
            newspaper = repository.getNewsPapersWithDetails()
            isLoading = false
        }
    }

    fun getNewsPaper(): List<NewspaperResponse> {
        return newspaper
    }
}