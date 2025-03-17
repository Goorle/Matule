package com.example.matule.data


import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.example.matule.domain.models.Category
import com.example.matule.domain.models.Products
import com.example.matule.domain.models.Promotions
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.storage.storage

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

    suspend fun getALlCategories(): List<Category> {
        return client.from("category").select().decodeList<Category>()
    }

    suspend fun getAllProducts(): List<Products> {
        return client.from("products").select().decodeList<Products>()
    }

    suspend fun getPromotions(): List<Promotions> {
        return client.from("promotions").select().decodeList<Promotions>()
    }

    suspend fun getImage(path: String): Bitmap? {
        val byte  = client.storage.from("products/items_image").downloadPublic(path)
        return BitmapFactory.decodeByteArray(byte, 0, byte.size)
    }

    suspend fun getImagePromotions(path: String): Bitmap? {
        val byte  = client.storage.from("products/discounts_image").downloadPublic(path)
        return BitmapFactory.decodeByteArray(byte, 0, byte.size)
    }
}