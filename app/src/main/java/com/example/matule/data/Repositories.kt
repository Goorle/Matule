package com.example.matule.data


import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email

class Repositories {
    private val client = Database.supabase

    suspend fun getUser(login: String, password: String) {
        client.auth.signInWith(Email) {
            email = login
            this.password = password
        }
    }

    suspend fun regUser(login: String, password: String) {
        client.auth.signUpWith(Email) {
            email = login
            this.password = password
        }
    }
}