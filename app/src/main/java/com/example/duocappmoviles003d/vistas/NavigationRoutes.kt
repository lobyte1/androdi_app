package com.example.duocappmoviles003d.vistas

object NavigationRoutes {
    const val ENTRY = "entry"
    const val LOGIN = "login"
    const val REGISTER = "register"
    const val HOME = "home/{username}"
    const val PRODUCTS = "productos/{username}"
    const val CART = "carrito/{username}"
    const val CONTACT = "contacto/{username}"

    fun createCartRoute(username: String) = "carrito/$username"
    fun createProductsRoute(username: String) = "productos/$username"
    fun createHomeRoute(username: String) = "home/$username"

    fun createContactRoute(username: String) = "contacto/$username"
}