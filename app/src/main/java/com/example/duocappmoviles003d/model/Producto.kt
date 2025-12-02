package com.example.duocappmoviles003d.model

import kotlinx.serialization.Serializable

@Serializable
data class Producto(
    val id: Int = 0,
    val nombre: String,
    val precio: String,
    val imagenResId: Int = 0,
    val category: String = "unisex" //filtrar si es hombre o mujer
)