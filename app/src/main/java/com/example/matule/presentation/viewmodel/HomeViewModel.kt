package com.example.matule.presentation.viewmodel

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.matule.data.Repositories
import com.example.matule.domain.models.Category
import com.example.matule.domain.models.Products
import io.github.jan.supabase.exceptions.HttpRequestException
import io.github.jan.supabase.postgrest.exception.PostgrestRestException
import io.ktor.client.plugins.HttpRequestTimeoutException
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
            try {
                products =  repository.getAllProducts()
            } catch (e: HttpRequestTimeoutException) {
              // TODO: Сделать вывод для ошибки  (время ожидания истекло)
            } catch (e: HttpRequestException) {
                // TODO: Сделать вывод для ошибки (проблемы с сетью)
            } catch (e: PostgrestRestException) {
                // TODO: Сделать вывод для ошибки (проблемы с сетью)
                when (e.statusCode) {

                }
            }
        }
    }

    private fun getCategories() {
        viewModelScope.launch {
            try {
                val allCategories = repository.getALlCategories()
                categories = listOf(Category(name = "Все")) + allCategories
            } catch (e: HttpRequestTimeoutException) {
                // TODO: Сделать вывод для ошибки  (время ожидания истекло)
            } catch (e: HttpRequestException) {
                // TODO: Сделать вывод для ошибки (проблемы с сетью)
            } catch (e: PostgrestRestException) {
                // TODO: Сделать вывод для ошибки (проблемы с сетью)
                when (e.statusCode) {

                }
            }
        }
    }
}