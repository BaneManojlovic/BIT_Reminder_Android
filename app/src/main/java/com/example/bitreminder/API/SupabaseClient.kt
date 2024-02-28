package com.example.bitreminder.API

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.GoTrue
import io.github.jan.supabase.postgrest.Postgrest

object SupabaseClient {

    val client = createSupabaseClient(
        supabaseUrl = "https://ojiqsgkmccrovkpsbfvs.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Im9qaXFzZ2ttY2Nyb3ZrcHNiZnZzIiwicm9sZSI6ImFub24iLCJpYXQiOjE2OTUxOTY0MDEsImV4cCI6MjAxMDc3MjQwMX0.zeszkitC7aoHtc12BdALDjzaWbyYXdd2UuLhNi33-Gs"
    ) {
        install(GoTrue)
        install(Postgrest)
    }
}