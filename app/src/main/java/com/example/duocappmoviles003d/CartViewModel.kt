package com.example.duocappmoviles003d

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.SharingStarted
import androidx.lifecycle.viewModelScope
import com.example.duocappmoviles003d.model.CartItem
import com.example.duocappmoviles003d.model.Producto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// El ViewModel que gestionará el estado del carrito.
class CartViewModel : ViewModel() {

    // Flujo de datos privado y mutable que contiene la lista de items del carrito.
    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    // Exposición pública e inmutable del flujo de datos para que la UI lo observe.
    val cartItems: StateFlow<List<CartItem>> = _cartItems.asStateFlow()

    // Flujo de datos que calcula el total del carrito cada vez que la lista de items cambia.
    val total: StateFlow<Int> = cartItems.map { items ->
        items.sumOf { it.producto.precio.parsePrice() * it.cantidad }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    fun addToCart(producto: Producto) {
        viewModelScope.launch {
            _cartItems.update { currentItems ->
                val existingItem = currentItems.find { it.producto.nombre == producto.nombre }
                if (existingItem != null) {
                    // Si el producto ya existe, incrementa su cantidad
                    currentItems.map {
                        if (it.producto.nombre == producto.nombre) {
                            it.copy(cantidad = it.cantidad + 1)
                        } else {
                            it
                        }
                    }
                } else {
                    // Si es un producto nuevo, lo añade a la lista
                    currentItems + CartItem(producto, 1)
                }
            }
        }
    }

    fun removeFromCart(cartItem: CartItem) {
        viewModelScope.launch {
            _cartItems.update { currentItems ->
                currentItems.filterNot { it.producto.nombre == cartItem.producto.nombre }
            }
        }
    }

    fun updateQuantity(cartItem: CartItem, newQuantity: Int) {
        if (newQuantity <= 0) {
            removeFromCart(cartItem)
            return
        }
        viewModelScope.launch {
            _cartItems.update { currentItems ->
                currentItems.map {
                    if (it.producto.nombre == cartItem.producto.nombre) {
                        it.copy(cantidad = newQuantity)
                    } else {
                        it
                    }
                }
            }
        }
    }
}

// Función de extensión para convertir el precio "$140.000" a un número entero 140000
private fun String.parsePrice(): Int {
    return this.replace("$", "").replace(".", "").trim().toIntOrNull() ?: 0
}