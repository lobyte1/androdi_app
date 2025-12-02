package com.example.duocappmoviles003d.data

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest

object SupabaseClient {
    val client = createSupabaseClient(
        supabaseUrl = "TU_URL_DE_SUPABASE_AQUI",
        supabaseKey = "TU_API_KEY_DE_SUPABASE_AQUI"
    ) {
        install(Postgrest)
    }
}