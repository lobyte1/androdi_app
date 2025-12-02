package com.example.duocappmoviles003d.vista.modelo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.duocappmoviles003d.data.SupabaseClient
import com.example.duocappmoviles003d.model.Producto
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductsViewModel : ViewModel() {

    private val _productos = MutableStateFlow<List<Producto>>(emptyList())
    val productos: StateFlow<List<Producto>> = _productos.asStateFlow()

    init {
        fetchProductos()
    }

    fun fetchProductos() {
        viewModelScope.launch {
            try {
                val listaReales = SupabaseClient.client
                    .from("productos")
                    .select()
                    .decodeList<Producto>()

                _productos.value = listaReales

            } catch (e: Exception) {
                println("Error al traer productos: ${e.message}")

            }
        }
    }
}