package com.example.duocappmoviles003d.data

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest

object SupabaseClient {
    val client = createSupabaseClient(
        supabaseUrl = "https://fgdtvulunipydhwpnegv.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImZnZHR2dWx1bmlweWRod3BuZWd2Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NjQ2OTUyNDAsImV4cCI6MjA4MDI3MTI0MH0.7NincoaUsWfHsaOo3Ad1A_5dTsN85J-CnpLO5EP2_VE"
    ) {
        install(Postgrest)
    }
}