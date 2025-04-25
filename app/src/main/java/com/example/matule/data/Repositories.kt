package com.example.matule.data



import android.util.Log
import com.example.matule.domain.models.BookDetails
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.auth.user.UserInfo
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns

class Repositories {
    private val client = Database.supabase


    suspend fun signInUser(email: String, password: String) {
        client.auth.signInWith(Email) {
            this.email = email
            this.password = password
        }
    }

    fun getCurrentUser(): UserInfo? {
        return client.auth.currentUserOrNull()
    }

    suspend fun getBooksWithDetails() {
        val data =  client.from("Book").select(
            Columns.raw("book_id, author, Publication(title, image), Genre(name)")
        )

        val book = data.decodeAs<BookDetails>()
        Log.d("DATA: ", "$book")
    }
}