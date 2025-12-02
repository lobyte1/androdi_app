package com.example.duocappmoviles003d.vistas

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel // Importante para inyectar el ViewModel
import com.example.duocappmoviles003d.R
import com.example.duocappmoviles003d.NavigationRoutes
import com.example.duocappmoviles003d.model.Producto
import com.example.duocappmoviles003d.vista.modelo.CartViewModel
import com.example.duocappmoviles003d.vista.modelo.ProductsViewModel
import com.example.duocappmoviles003d.vistas.HomeTopAppBar // Asegúrate de que este import sea correcto según dónde tengas el TopBar
import com.example.duocappmoviles003d.vistas.AppPrimaryColor // Lo mismo para el color
import kotlinx.coroutines.launch

// Definimos el color aquí por si no se importa de otro lado
val AppPrimaryColor = Color(0xFFE0B0FF)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaProductos(
    username: String,
    onNavigate: (String) -> Unit,
    cartViewModel: CartViewModel,
    // Inyectamos el ProductsViewModel aquí.
    // Si no se pasa uno, se crea uno nuevo automáticamente.
    productsViewModel: ProductsViewModel = viewModel()
) {
    val estadoMenuHamburguesa = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = estadoMenuHamburguesa,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = Color.Black
            ) {
                Text(
                    text = "¡Hola, $username!",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                HorizontalDivider(color = Color.Gray)
                Spacer(modifier = Modifier.height(8.dp))
                NavigationDrawerItem(
                    label = { Text("Inicio") },
                    selected = false,
                    onClick = {
                        onNavigate(NavigationRoutes.createHomeRoute(username))
                        scope.launch { estadoMenuHamburguesa.close() }
                    },
                    colors = NavigationDrawerItemDefaults.colors(
                        selectedTextColor = Color.White,
                        unselectedTextColor = Color.White
                    )
                )

                NavigationDrawerItem(
                    label = { Text("Productos") },
                    selected = true,
                    onClick = {
                        onNavigate(NavigationRoutes.createProductsRoute(username))
                        scope.launch { estadoMenuHamburguesa.close() }
                    },
                    colors = NavigationDrawerItemDefaults.colors(
                        unselectedTextColor = Color.White
                    )
                )

                NavigationDrawerItem(
                    label = { Text("Carrito") },
                    selected = false,
                    onClick = {
                        onNavigate(NavigationRoutes.createCartRoute(username))
                        scope.launch { estadoMenuHamburguesa.close() }
                    },
                    colors = NavigationDrawerItemDefaults.colors(
                        unselectedTextColor = Color.White
                    )
                )

                NavigationDrawerItem(
                    label = { Text("Contacto") },
                    selected = false,
                    onClick = {
                        onNavigate(NavigationRoutes.createContactRoute(username))
                        scope.launch { estadoMenuHamburguesa.close() }
                    },
                    colors = NavigationDrawerItemDefaults.colors(
                        unselectedTextColor = Color.White
                    )
                )

                NavigationDrawerItem(
                    label = { Text("Cerrar Sesión") },
                    selected = false,
                    onClick = { onNavigate("login"); scope.launch { estadoMenuHamburguesa.close() } },
                    colors = NavigationDrawerItemDefaults.colors(
                        unselectedTextColor = Color.White
                    )
                )
            }
        }
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            // Pasamos ambos ViewModels al contenido
            ContenidoProductos(
                cartViewModel = cartViewModel,
                productsViewModel = productsViewModel
            )

            HomeTopAppBar(
                modifier = Modifier.align(Alignment.TopCenter),
                onMenuClick = {
                    scope.launch { estadoMenuHamburguesa.apply { if (isClosed) open() else close() } }
                },
                onCartClick = {
                    onNavigate(NavigationRoutes.createCartRoute(username))
                }
            )

            Image(
                painter = painterResource(id = R.drawable.roro_perfumes),
                contentDescription = "Logo",
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 40.dp)
                    .height(80.dp)
            )
        }
    }
}

@Composable
fun ContenidoProductos(
    modifier: Modifier = Modifier,
    cartViewModel: CartViewModel,
    productsViewModel: ProductsViewModel
) {
    // 1. Observamos la lista completa del ViewModel
    val todosLosProductos by productsViewModel.productos.collectAsState()

    // 2. Filtramos la lista en tiempo real para crear las secciones
    val productosHombre = todosLosProductos.filter { it.categoria == "hombre" }
    val productosMujer = todosLosProductos.filter { it.categoria == "mujer" }
    val productosUnisex = todosLosProductos.filter { it.categoria == "unisex" }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF0F0F0)),
        contentPadding = PaddingValues(bottom = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item(
            span = { GridItemSpan(2) }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.pf),
                    contentDescription = "Banner principal",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.8f)),
                                startY = 250f
                            )
                        )
                )
                Text(
                    text = "Productos",
                    color = AppPrimaryColor,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 16.dp)
                )
            }
        }

        // SECCIÓN HOMBRE
        if (productosHombre.isNotEmpty()) {
            item(span = { GridItemSpan(2) }) {
                Text(
                    text = "Perfumes de Hombre",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)
                )
            }
            items(productosHombre) { producto ->
                ProductoCard(
                    producto = producto,
                    modifier = Modifier.padding(horizontal = 16.dp),
                    cartViewModel = cartViewModel
                )
            }
        }

        // SECCIÓN MUJER
        if (productosMujer.isNotEmpty()) {
            item(span = { GridItemSpan(2) }) {
                Text(
                    text = "Perfumes de Mujer",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 24.dp)
                )
            }
            items(productosMujer) { producto ->
                ProductoCard(
                    producto = producto,
                    modifier = Modifier.padding(horizontal = 16.dp),
                    cartViewModel = cartViewModel
                )
            }
        }

        // SECCIÓN UNISEX
        if (productosUnisex.isNotEmpty()) {
            item(span = { GridItemSpan(2) }) {
                Text(
                    text = "Perfumes Unisex",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 24.dp)
                )
            }
            items(productosUnisex) { producto ->
                ProductoCard(
                    producto = producto,
                    modifier = Modifier.padding(horizontal = 16.dp),
                    cartViewModel = cartViewModel
                )
            }
        }
    }
}

@Composable
fun ProductoCard(
    producto: Producto,
    modifier: Modifier = Modifier,
    cartViewModel: CartViewModel
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // IMAGEN: Lógica mixta (Local o URL)
            if (producto.imagenResId != 0) {
                Image(
                    painter = painterResource(id = producto.imagenResId),
                    contentDescription = producto.nombre,
                    modifier = Modifier
                        .height(120.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
            } else {
                // Aquí pondrás AsyncImage(model = producto.imagenUrl) cuando uses Supabase
                Box(modifier = Modifier.height(120.dp).fillMaxWidth().background(Color.Gray))
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                producto.nombre,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                minLines = 2,
                maxLines = 2
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                producto.precio,
                color = Color.Red,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = { cartViewModel.addToCart(producto) },
                colors = ButtonDefaults.buttonColors(containerColor = AppPrimaryColor),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Agregar")
            }
        }
    }
}