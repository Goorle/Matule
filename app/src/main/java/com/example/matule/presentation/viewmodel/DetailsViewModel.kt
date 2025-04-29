package com.example.matule.presentation.viewmodel

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.matule.data.Repositories
import com.example.matule.domain.models.NewspaperResponse
import kotlinx.coroutines.launch

class DetailsViewModel: ViewModel() {
    val repository = Repositories()

    var item by mutableStateOf<NewspaperResponse?>(null)

    var bitmap by mutableStateOf<Bitmap?>(null)

    var linesDescription by mutableStateOf(3)


    fun getItem(publicationId: String) {
        viewModelScope.launch {
            item = repository.getPublicationById(publicationId)
        }
    }

    fun getImage() {
        viewModelScope.launch {
            val itemBuff = item
            if (itemBuff != null && itemBuff.publication.image != null) {
                val imageUrl = itemBuff.publication.image
                val list = imageUrl.split("/")
                val bucket = list[0]
                val file = list[1]
                val byteArray = repository.getFileFromStorage(bucket, file)
                bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
            }
        }
    }

    fun changeLines() {
        if (linesDescription == 3) {
            linesDescription = Int.MAX_VALUE
        } else {
            linesDescription = 3
        }
    }

}