package com.example.matule.presentation.viewmodel

import android.graphics.Bitmap
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.matule.data.Repositories
import kotlinx.coroutines.launch

class CardViewModel: ViewModel() {
    private val repository = Repositories()
    var isFavorite by mutableStateOf(false)
    var bitmap by mutableStateOf<Bitmap?>(null)

    fun getImage() {

    }
}