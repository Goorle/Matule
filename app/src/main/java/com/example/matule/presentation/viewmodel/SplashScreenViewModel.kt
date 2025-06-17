package com.example.matule.presentation.viewmodel

import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import com.example.matule.data.Repositories
import io.github.jan.supabase.auth.auth

class SplashScreenViewModel: ViewModel() {
    private val repositories = Repositories()

    fun isUserLoggedIn(): Boolean {
        return repositories.isUserLoggedIn()
    }

}