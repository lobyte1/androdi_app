package com.example.duocappmoviles003d.vistas

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.duocappmoviles003d.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaContacto(username: String, onNavigate: (String) -> Unit) {
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
                    selected = true,
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
        Box(modifier = Modifier.fillMaxSize()) {
            ContenidoContacto()

            // Barra superior específica para esta pantalla
            ContactoTopAppBar(
                modifier = Modifier.align(Alignment.TopCenter),
                onMenuClick = {
                    scope.launch { estadoMenuHamburguesa.apply { if (isClosed) open() else close() } }
                },
                onInicioClick = { onNavigate(NavigationRoutes.createHomeRoute(username)) }
            )

            // Logo flotante
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
fun ContactoTopAppBar(
    modifier: Modifier = Modifier,
    onMenuClick: () -> Unit,
    onInicioClick: () -> Unit
) {
    TopAppBar(
        modifier = modifier,
        title = { /* Vacío */ },
        navigationIcon = {
            Surface(shape = CircleShape, color = Color.Black.copy(alpha = 0.5f), modifier = Modifier.padding(start = 8.dp)) {
                IconButton(onClick = onMenuClick) {
                    Icon(Icons.Default.Menu, contentDescription = "Menú")
                }
            }
        },
        actions = {
            // Botón "Inicio" en lugar del carrito
            Button(
                onClick = onInicioClick,
                colors = ButtonDefaults.buttonColors(containerColor = AppPrimaryColor),
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Text("Inicio")
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Black.copy(alpha = 0.4f),
            navigationIconContentColor = Color.White
        )
    )
}


@Composable
fun ContenidoContacto(modifier: Modifier = Modifier) {
    var email by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF0F0F0)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            // Banner superior
            Image(
                painter = painterResource(id = R.drawable.pf),
                contentDescription = "Banner principal",
                modifier = Modifier.fillMaxWidth().height(250.dp),
                contentScale = ContentScale.Crop
            )
        }

        item { Spacer(modifier = Modifier.height(16.dp)) }

        // Tarjeta del formulario
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = MaterialTheme.shapes.large,
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "Contáctanos",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(24.dp))

                    // Campo de E-mail
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("E-mail") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    // Campo de Mensaje
                    OutlinedTextField(
                        value = message,
                        onValueChange = { message = it },
                        label = { Text("Mensaje") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp),
                        minLines = 5
                    )
                    Spacer(modifier = Modifier.height(24.dp))

                    // Botón de Enviar
                    Button(
                        onClick = { /* Lógica para enviar mensaje */ },
                        modifier = Modifier.fillMaxWidth().height(50.dp),
                        shape = MaterialTheme.shapes.medium,
                        colors = ButtonDefaults.buttonColors(containerColor = AppPrimaryColor)
                    ) {
                        Text("Enviar", fontSize = 18.sp)
                    }
                }
            }
        }
        item { Spacer(modifier = Modifier.height(16.dp)) }
    }
}