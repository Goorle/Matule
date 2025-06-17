package com.example.matule.data

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.matule.domain.models.FavoritePublication
import com.example.matule.domain.models.FavoriteResponse
import com.example.matule.domain.models.NewspaperResponse
import com.example.matule.domain.models.Notification
import com.example.matule.domain.models.Publication
import com.example.matule.domain.models.User
import com.example.matule.domain.models.UserCollection
import com.example.matule.presentation.viewmodel.NotificationViewModel
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.auth.user.UserInfo
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.realtime.PostgresAction
import io.github.jan.supabase.realtime.channel
import io.github.jan.supabase.realtime.postgresChangeFlow
import io.github.jan.supabase.realtime.postgresListDataFlow
import io.github.jan.supabase.storage.storage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.serialization.json.Json

class Repositories() {
    var client: SupabaseClient = Database.supabase

    fun isUserLoggedIn(): Boolean {
        return client.auth.currentAccessTokenOrNull() != null
    }

    fun getUserId(): String {
        var userId = "dc016d2a-01dc-41f8-8412-9e500beec87b"
        val data = client.auth.currentUserOrNull()

        if (data != null) {
            userId = data.id
        }

        return userId
    }

    suspend fun signUpUser(email: String, password: String, name: String) {
        client.auth.signUpWith(Email) {
            this.email = email
            this.password = password
        }

        val userId = getUserId()

        client.from("User").insert(User(
            userId = userId,
            email = email,
            firstname = name
        ))
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

    suspend fun getFavoritePublication(): List<FavoriteResponse> {
        val userId = getUserId()

        return client.from("FavoritePublication").select(
            Columns.raw("favorite_id, Publication(id, title, publication_date, image, description)")
        ) {
            filter {
                FavoritePublication::userId eq userId
            }
        }.decodeList<FavoriteResponse>()
    }

    suspend fun   findFavorite(publicationId: String): FavoritePublication? {
        val userId = getUserId()

        return client.from("FavoritePublication").select{
            filter {
                FavoritePublication::publicationId eq publicationId
                FavoritePublication::userId eq userId
            }
        }.decodeSingleOrNull<FavoritePublication>()
    }

    suspend fun updateFavorite(publicationId: String) {
        val userId = getUserId()

        client.from("FavoritePublication").insert(
            FavoritePublication(
                userId = userId,
                publicationId = publicationId
            )
        )
    }

    suspend fun getUserData(): User? {
        val userId = getUserId()

        return client.from("User").select{
            filter {
                User::userId eq userId
            }
        }.decodeSingleOrNull()
    }

    suspend fun updateUserData(user: User) {
        val userId = getUserId()

        client.from("User").update({
            User::firstname setTo user.firstname
            User::lastname setTo user.lastname
            User::phone setTo user.phone
            User::userImage setTo user.userImage
            User::subscription setTo user.subscription
            User::email setTo user.email
            set("secondname", user.secondName)
        }

        ){
            filter {
                User::userId eq userId
            }
        }
    }

    suspend fun deleteFromFavorite(publicationId: String) {
        val userId = getUserId()

        client.from("FavoritePublication").delete{
            filter {
                FavoritePublication::userId eq userId
                FavoritePublication::publicationId eq publicationId
            }
        }
    }

    suspend fun uploadFile(bytes: ByteArray) {
        val userId = getUserId()
        val bucket = client.storage.from("user-image")
        bucket.upload(
            "${userId}_photo.jpg",
            bytes
        ) {
            upsert = true
        }
    }

    suspend fun getListNotification(): List<Notification> {
        val userId = getUserId()

        return client.from("Notification").select{
          filter {
              Notification::userId eq userId
          }
        }.decodeList<Notification>()
    }

    fun realtimeUpdateNotification() {
        val flow: Flow<List<Notification>>
    }
}