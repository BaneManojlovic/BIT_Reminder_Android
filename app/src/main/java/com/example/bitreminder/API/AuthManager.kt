package com.example.bitreminder.API

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest


class AuthManager {


    private val projectUrl = "https://ojiqsgkmccrovkpsbfvs.supabase.co"

    private val apiKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Im9qaXFzZ2ttY2Nyb3ZrcHNiZnZzIiwicm9sZSI6ImFub24iLCJpYXQiOjE2OTUxOTY0MDEsImV4cCI6MjAxMDc3MjQwMX0.zeszkitC7aoHtc12BdALDjzaWbyYXdd2UuLhNi33-Gs"

   public  fun getClient() : SupabaseClient {
        return createSupabaseClient(
            supabaseUrl = projectUrl,
            supabaseKey = apiKey
        ) {
            install(Postgrest)
        }
    }
}