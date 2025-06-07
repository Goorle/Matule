package com.example.matule.presentation.viewmodel

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.matule.data.Repositories
import com.example.matule.domain.models.User
import kotlinx.coroutines.launch

class UserViewModel: ViewModel() {
    private val repository = Repositories()
    var userData by mutableStateOf<User?>(null)

    var isLoading by mutableStateOf(false)

    var bitmap by mutableStateOf<Bitmap?>(null)

    var firstname by mutableStateOf("")
    var lastname by mutableStateOf("")
    var secondname by mutableStateOf("")
    var email by mutableStateOf("")
    var phone by mutableStateOf("")
    var subscription by mutableStateOf(false)


    init {
        updateUser()
    }

    private fun updateUser() {
        viewModelScope.launch {
            try {
                isLoading = true
                userData = repository.getUserData()
                updateEdits()
            } catch (e: Exception) {
                Log.d("ERROR", "$e")
            } finally {
                isLoading = false
            }
        }
    }

    private fun updateEdits() {
        val currentData = userData
        if (currentData != null) {
            firstname = currentData.firstname
            lastname = currentData.lastname ?: ""
            secondname = currentData.secondName
            email = currentData.email
            phone = currentData.phone
            subscription = currentData.subscription
        }

    }

    suspend fun getImage(imageUrl: String?) {
        if (!imageUrl.isNullOrEmpty()) {
            val list = imageUrl.split("/")
            val bucket = list[0]
            val file = list[1]
            val byteArray = repository.getFileFromStorage(bucket, file)
            bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)

        }
    }

}