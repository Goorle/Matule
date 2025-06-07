package com.example.matule.data

import com.example.matule.domain.models.FavoritePublication
import com.example.matule.domain.models.FavoriteResponse
import com.example.matule.domain.models.NewspaperResponse
import com.example.matule.domain.models.Publication
import com.example.matule.domain.models.User
import com.example.matule.domain.models.UserCollection
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.auth.user.UserInfo
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.storage.storage
import kotlinx.serialization.json.Json

class Repositories {
    private val client = Database.supabase

    fun getUserId(): String {
        var userId = "cbb588f6-4c90-4b65-8370-f205e0da5b36"
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

    suspend fun findFavorite(publicationId: String): FavoritePublication? {
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

        client.from("User").update(user){
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
}