package com.example.duocappmoviles003d.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Producto(

    val nombre: String,
    val precio: String,
    val imagenResId: Int = 0,

    val id: Int = 0,
    @SerialName("imagen_url")
    val imagenUrl: String = "",
    val categoria: String = "unisex"
)