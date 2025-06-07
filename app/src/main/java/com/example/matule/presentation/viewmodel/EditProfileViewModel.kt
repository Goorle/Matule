package com.example.matule.presentation.viewmodel

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.matule.data.Repositories
import com.example.matule.domain.models.User
import kotlinx.coroutines.launch

class EditProfileViewModel: ViewModel() {
    private val repositories = Repositories()
    private var userId: String = ""

    var firstname by mutableStateOf("")
    var lastname by mutableStateOf("")
    var secondname by mutableStateOf("")
    var phone by mutableStateOf("")

    var user: User? = null

    var isLoading by mutableStateOf(false)
    var isEditSuccess by mutableStateOf(false)

    init {
        userId = repositories.getUserId()
        updateFields()
    }

    private fun updateFields() {
        viewModelScope.launch {
            try {
                user = repositories.getUserData()
                val currentUser = user
                if (currentUser != null) {
                    firstname = currentUser.firstname
                    lastname = currentUser.lastname ?: ""
                    secondname = currentUser.secondName
                    phone = currentUser.phone
                }
            } catch (e: Exception) {

            }
        }
    }

    fun updateUser() {
        viewModelScope.launch {
            try {
                isLoading = true
                val currentUser = user

                if (currentUser != null) {
                    currentUser.firstname = firstname
                    currentUser.lastname = lastname
                    currentUser.secondName = secondname
                    currentUser.phone = phone

                    repositories.updateUserData(currentUser)

                    isEditSuccess = true
                }
            } catch (e: Exception) {

            } finally {
                isLoading = false
            }
        }
    }
}