package com.example.matule.presentation.viewmodel

import android.graphics.Bitmap
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.matule.data.Repositories
import kotlinx.coroutines.launch

class CardViewModel: ViewModel() {
    private val repository = Repositories()
    var bitmap by mutableStateOf<Bitmap?>(null)

    fun getImage(path: String) {
        viewModelScope.launch {
            bitmap = repository.getImage(path)
        }
    }
}