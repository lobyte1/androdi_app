package com.example.duocappmoviles003d.data.repository

import com.example.duocappmoviles003d.data.SupabaseClient
import com.example.duocappmoviles003d.model.Producto
import io.github.jan.supabase.postgrest.from

class ProductsRepository {


    suspend fun getProductos(): List<Producto> {
        return SupabaseClient.client
            .from("productos")
            .select()
            .decodeList<Producto>()
    }
}