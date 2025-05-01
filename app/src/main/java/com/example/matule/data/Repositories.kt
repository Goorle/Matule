package com.example.matule.data



import android.util.Log
import com.example.matule.domain.models.Newspaper
import com.example.matule.domain.models.NewspaperResponse
import com.example.matule.domain.models.Publication
import com.example.matule.domain.models.UserCollection
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

    fun getUserId(): String {
        var userId = "dc016d2a-01dc-41f8-8412-9e500beec87b"
        val data = client.auth.currentUserOrNull()

        if (data != null) {
            userId = data.id
        }

        return userId
    }

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

    suspend fun getPublicationUser(userId: String): List<UserCollection> {
            return client.from("UserCollection").select{
                filter {
                    UserCollection::userId eq userId
                }
            }.decodeList<UserCollection>()
    }

    suspend fun getNewsPapersWithDetails(): List<NewspaperResponse> {
        val data = client.from("Newspaper").select(
            Columns.raw("newspaper_id, Publication(id, title, publication_date, image, description)")
        )
        return Json.decodeFromString(data.data)
    }

    fun getUrlFile(bucket: String, file: String): String{
        return client.storage.from(bucket).publicUrl(file)
    }

    suspend fun getPublicationById(publicationId: String): Publication {
        val data = client.from("Publication").select(
            Columns.raw("id ,title, publication_date, image, description, point_file")
        ){
            filter {
                Publication::id eq publicationId
            }
        }.decodeSingle<Publication>()

        return data
    }

    suspend fun addPublicationCollection(publicationId: String, userId: String) {
        client.from("UserCollection").insert(UserCollection(
            publicationId = publicationId,
            userId = userId,
            countReading = 1
        ))
    }

    suspend fun updateCollection(userCollection: UserCollection) {
        client.from("UserCollection").update(userCollection){
            filter {
                UserCollection::publicationId eq userCollection.publicationId
                UserCollection::userId eq userCollection.userId
            }
        }
    }

    suspend fun getUserPublication(publicationId: String, userId: String): UserCollection? {
        return client.from("UserCollection").select{
            filter {
                UserCollection::userId eq userId
                UserCollection::publicationId eq publicationId
            }
        }.decodeSingleOrNull<UserCollection>()

    }
}