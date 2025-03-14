package com.example.matule.data

import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.storage.Storage

object Database {
    val supabase = createSupabaseClient(
        supabaseUrl = "https://rnbkvbycjxwgbhjgixkh.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InJuYmt2Ynljanh3Z2JoamdpeGtoIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDExNTExMzksImV4cCI6MjA1NjcyNzEzOX0.s0aQFm7_AtMdRlxOiamv-jA1aUrP1tuEzKDRBOBZyLM"
    ) {
        install(Auth)
        install(Postgrest)
        install(Storage)
    }
}
