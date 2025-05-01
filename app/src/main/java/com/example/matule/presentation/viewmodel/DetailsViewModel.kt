package com.example.matule.presentation.viewmodel

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.matule.data.Repositories
import com.example.matule.domain.models.NewspaperResponse
import com.example.matule.domain.models.Publication
import com.example.matule.domain.models.UserCollection
import kotlinx.coroutines.launch

class DetailsViewModel: ViewModel() {
    val repository = Repositories()
    var item by mutableStateOf<Publication?>(null)
    var bitmap by mutableStateOf<Bitmap?>(null)
    var linesDescription by mutableIntStateOf(3)
    var visiblePDF by mutableStateOf(false)
    var urlPDF by mutableStateOf("")
    var buttonEnable by mutableStateOf(false)
    var userCollection by mutableStateOf<UserCollection?>(null)

    fun getUserCollection(publicationId: String) {
        var userId = repository.getUserId()
        if (userId.isNotEmpty()) {
            viewModelScope.launch {
                userCollection = repository.getUserPublication(publicationId, userId)
            }
        }
    }

    fun getEmptyCollection(itemId: String): UserCollection {
        var userId = repository.getUserId()
        if (userId.isNotEmpty()) {
            return UserCollection(
                publicationId = itemId,
                userId = userId,
                countReading = 1
            )
        }

        return UserCollection()
    }

    fun getItem(publicationId: String) {
        viewModelScope.launch {
            item = repository.getPublicationById(publicationId)
            if (item != null) {
                buttonEnable = true
            }
        }
    }

    fun getImage() {
        viewModelScope.launch {
            val itemBuff = item
            if (itemBuff != null) {
                val imageUrl = itemBuff.image
                val list = imageUrl.split("/")
                val bucket = list[0]
                val file = list[1]
                val byteArray = repository.getFileFromStorage(bucket, file)
                bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
            }
        }
    }

    fun getUrlPDF() {
        val itemBuff = item
        if (itemBuff != null && itemBuff.pointFile.isNotEmpty()) {
            val filePath = itemBuff.pointFile
            val list = filePath.split("/")
            val bucket = list[0]
            val file = list[1]

            urlPDF = repository.getUrlFile(bucket, file)
        }
    }

    fun changeLines() {
        if (linesDescription == 3) {
            linesDescription = Int.MAX_VALUE
        } else {
            linesDescription = 3
        }
    }

    fun addPublicationToCollection(publicationId: String) {
        viewModelScope.launch {

            val userId = repository.getUserId()

            if (userId != "" && userCollection == null) {
                repository.addPublicationCollection(publicationId, userId)
            }
        }
    }



}