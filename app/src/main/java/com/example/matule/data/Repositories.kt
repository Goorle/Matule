package com.example.matule.data



import android.util.Log
import com.example.matule.domain.models.Newspaper
import com.example.matule.domain.models.NewspaperResponse
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.auth.user.UserInfo
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.storage.storage
import io.github.jan.supabase.storage.upload
import kotlinx.serialization.json.Json

class Repositories {
    private val client = Database.supabase


    suspend fun signInUser(email: String, password: String) {
        client.auth.signInWith(Email) {
            this.email = email
            this.password = password
        }
    }

    suspend fun getFileFromStorage(bucket: String, file: String): ByteArray {
        return client.storage.from(bucket).downloadPublic(file)
    }


    fun getCurrentUser(): UserInfo? {
        return client.auth.currentUserOrNull()
    }

    suspend fun getNewsPapersWithDetails(): List<NewspaperResponse> {
        val data = client.from("Newspaper").select(
            Columns.raw("newspaper_id, Publication(id ,title, publication_date, image, description)")
        )
        return Json.decodeFromString(data.data)
    }

    suspend fun getPublicationById(publicationId: String): NewspaperResponse {
        val data = client.from("Newspaper").select(
            Columns.raw("newspaper_id, Publication(id ,title, publication_date, image, description)")
        ){
            filter {
                Newspaper::publicationId eq publicationId
            }
        }.decodeSingle<NewspaperResponse>()

        return data
    }
}