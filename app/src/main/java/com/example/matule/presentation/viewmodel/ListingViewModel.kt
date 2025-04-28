package com.example.matule.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.matule.data.Repositories



class ListingViewModel: ViewModel() {
    private val repository = Repositories()
    var currentCategory by mutableStateOf("Все")

}