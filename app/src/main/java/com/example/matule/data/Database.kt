package com.example.matule.data

import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.realtime.Realtime
import io.github.jan.supabase.storage.Storage
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import kotlin.time.Duration.Companion.seconds

object Database {
    val okHttpClient = OkHttpClient.Builder()
        .pingInterval(20, TimeUnit.SECONDS) // Для поддержания соединения
        .build()

    val supabase = createSupabaseClient(
        supabaseUrl = "https://orduqvkxlhvxfpgvbdal.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Im9yZHVxdmt4bGh2eGZwZ3ZiZGFsIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDU0NzUyODUsImV4cCI6MjA2MTA1MTI4NX0.as-pnNpZBaGw5l00ksimareuc5gPSMH7tqrx-v4Jeow",

    ) {
        install(Auth)
        install(Postgrest)
        install(Storage)
        install(Realtime) {
            reconnectDelay = 5.seconds
        }
    }
}
