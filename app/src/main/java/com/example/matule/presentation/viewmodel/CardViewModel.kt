package com.example.matule.presentation.viewmodel

import android.graphics.Bitmap
import android.graphics.BitmapFactory
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

    suspend fun getImage(imageUrl: String?) {
        if (imageUrl.isNullOrEmpty()) {
        } else {
            val list = imageUrl.split("/")
            val bucket = list[0]
            val file = list[1]
            val byteArray = repository.getFileFromStorage(bucket, file)
            bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
        }
    }
}