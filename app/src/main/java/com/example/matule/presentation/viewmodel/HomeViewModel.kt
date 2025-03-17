package com.example.matule.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.matule.data.Repositories
import com.example.matule.domain.models.Category
import com.example.matule.domain.models.Products
import kotlinx.coroutines.launch

class HomeViewModel: ViewModel() {
    private val repository = Repositories()
    var categories by mutableStateOf<List<Category>?>(null)
    var products by mutableStateOf<List<Products>?>(null)

    init {
        getCategories()
        getProducts()
    }

    private fun getProducts() {
        viewModelScope.launch {
            products =  repository.getAllProducts()
        }
    }


    private fun getCategories() {
        viewModelScope.launch {
            val allCategories = repository.getALlCategories()
            categories = listOf(Category(name = "Все")) + allCategories
        }
    }
}