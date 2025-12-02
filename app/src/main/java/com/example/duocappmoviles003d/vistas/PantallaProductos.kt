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
import com.example.duocappmoviles003d.R
import com.example.duocappmoviles003d.model.Producto
import com.example.duocappmoviles003d.vista.modelo.CartViewModel
import kotlinx.coroutines.launch

val productosHombre = listOf(
    Producto("Dior Sauvage", "$140.000", R.drawable.diorsauvage),
    Producto("Acqua di Gio", "$79.990", R.drawable.acquadigio),
    Producto("Versace Eros Flame", "$69.990", R.drawable.versaceeros),
    Producto("Jean Paul Gaultier", "$69.990", R.drawable.jeanpaul),
    Producto("212 NYC Men", "$55.000", R.drawable.nyc),
    Producto("Paco Rabanne Invictus", "$85.000", R.drawable.invictus),
)

val productosMujer = listOf(
    Producto("Good Girl", "$120.000", R.drawable.goodgirl),
    Producto("La Vie Est Belle", "$110.000", R.drawable.lavie),
    Producto("Black Opium", "$95.000", R.drawable.blacko),
    Producto("Perfume Cacharel", "$60.000", R.drawable.cacharel),
    Producto("Giorgio Armani My Way", "$90.000", R.drawable.myway)
)

val productosUnisex = listOf(
    Producto("Tom Ford Oud Wood", "$180.000", R.drawable.tomford),
    Producto("Jo Malone Lime Basil & Mandarin", "$130.000", R.drawable.jomalone),
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaProductos(
    username: String,
    onNavigate: (String) -> Unit,
    cartViewModel: CartViewModel
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
            ContenidoProductos(cartViewModel = cartViewModel)

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
fun ContenidoProductos(modifier: Modifier = Modifier, cartViewModel: CartViewModel) {
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
            Image(
                painter = painterResource(id = producto.imagenResId),
                contentDescription = producto.nombre,
                modifier = Modifier
                    .height(120.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
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
                // boton agregar producto
                onClick = { cartViewModel.addToCart(producto) },
                colors = ButtonDefaults.buttonColors(containerColor = AppPrimaryColor),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Agregar")
            }
        }
    }
}