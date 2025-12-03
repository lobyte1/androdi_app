package com.example.duocappmoviles003d.vista.modelo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.duocappmoviles003d.data.repository.ProductsRepository
import com.example.duocappmoviles003d.model.Producto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductsViewModel : ViewModel() {

    private val repository = ProductsRepository()

    private val _productos = MutableStateFlow<List<Producto>>(emptyList())
    val productos: StateFlow<List<Producto>> = _productos.asStateFlow()

    init {
        fetchProductos()
    }

    fun fetchProductos() {
        viewModelScope.launch {
            try {
                println("Iniciando descarga desde Repository...")

                val listaReales = repository.getProductos()

                println("Productos descargados: ${listaReales.size}")
                _productos.value = listaReales

            } catch (e: Exception) {
                println("Error en ViewModel: ${e.message}")
                e.printStackTrace()
            }
        }
    }
}