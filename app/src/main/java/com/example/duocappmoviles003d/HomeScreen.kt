package com.example.duocappmoviles003d

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ShoppingCart
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
import com.example.duocappmoviles003d.model.Producto
import kotlinx.coroutines.launch

val AppPrimaryColor = Color(0xFFE0B0FF)

val ofertaPrincipal = Producto("Perfume Cacharel", "$60.000", R.drawable.cacharel)
val productosDestacados = listOf(
    Producto("Dior Sauvage", "$140.000", R.drawable.diorsauvage),
    Producto("Acqua Di Gio", "$79.990", R.drawable.acquadigio),
    Producto("Paco Rabanne Invictus", "$85.000", R.drawable.invictus)
)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(username: String, onNavigate: (String) -> Unit) {
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
                    selected = false,
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
            ContenidoPrincipal()

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopAppBar(
    modifier: Modifier = Modifier,
    onMenuClick: () -> Unit,
    onCartClick: () -> Unit
) {
    TopAppBar(
        modifier = modifier,
        title = { /* El título está vacío, el logo se maneja por fuera */ },
        navigationIcon = {
            Surface(
                shape = CircleShape,
                color = Color.Black.copy(alpha = 0.5f),
                modifier = Modifier.padding(start = 8.dp)
            ) {
                IconButton(onClick = onMenuClick) {
                    Icon(Icons.Default.Menu, contentDescription = "Menú")
                }
            }
        },
        actions = {
            Surface(
                shape = CircleShape,
                color = Color.Black.copy(alpha = 0.5f),
                modifier = Modifier.padding(end = 8.dp)
            ) {
                IconButton(onClick = onCartClick) {
                    Icon(Icons.Default.ShoppingCart, contentDescription = "Carrito")
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Black.copy(alpha = 0.4f),
            navigationIconContentColor = Color.White,
            actionIconContentColor = Color.White
        )
    )
}

@Composable
fun ContenidoPrincipal(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF0F0F0))
    ) {
        item {
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
                    text = "Inicio",
                    color = AppPrimaryColor,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 16.dp)
                )
            }
        }

        item { Spacer(modifier = Modifier.height(16.dp)) }
        item { SeccionOfertas() }
        item { Spacer(modifier = Modifier.height(24.dp)) }
        item { SeccionDestacados() }
        item { Spacer(modifier = Modifier.height(24.dp)) }
        item { SeccionProximamente() }
        item { Spacer(modifier = Modifier.height(16.dp)) }
    }
}

@Composable
fun SeccionOfertas() {
    Column {
        Text(
            text = "Ofertas del Mes",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(id = ofertaPrincipal.imagenResId),
                    contentDescription = ofertaPrincipal.nombre,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(ofertaPrincipal.nombre, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text(ofertaPrincipal.precio, color = Color.Red, fontSize = 16.sp)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun SeccionDestacados() {
    Column {
        Text(
            text = "Destacados",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(productosDestacados) { producto ->
                Card(
                    modifier = Modifier.width(150.dp),
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
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
                            minLines = 2
                        )
                        Text(producto.precio, color = Color.Red)
                    }
                }
            }
        }
    }
}

@Composable
fun SeccionProximamente() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "¡Próximamente!",
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text("Nuevos productos llegarán pronto. ¡Mantente atento!")
    }
}