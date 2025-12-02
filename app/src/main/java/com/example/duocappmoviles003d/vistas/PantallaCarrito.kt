package com.example.duocappmoviles003d.vistas

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Remove
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.duocappmoviles003d.R
// IMPORTANTE: Importamos tus modelos desde la carpeta 'model'
import com.example.duocappmoviles003d.model.CartItem
import com.example.duocappmoviles003d.vista.modelo.CartViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaCarrito(
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
                    selected = true,
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
            ContenidoCarrito(
                onNavigateHome = {
                    onNavigate(NavigationRoutes.createHomeRoute(username))
                },
                cartViewModel = cartViewModel
            )

            HomeTopAppBar(
                modifier = Modifier.align(Alignment.TopCenter),
                onMenuClick = {
                    scope.launch { estadoMenuHamburguesa.apply { if (isClosed) open() else close() } }
                },
                onCartClick = {}
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
fun ContenidoCarrito(
    modifier: Modifier = Modifier,
    onNavigateHome: () -> Unit,
    cartViewModel: CartViewModel
) {
    val cartItems by cartViewModel.cartItems.collectAsState()
    val total by cartViewModel.total.collectAsState()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF0F0F0)),
        horizontalAlignment = Alignment.CenterHorizontally
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
                    text = "Carrito",
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
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    if (cartItems.isEmpty()) {
                        Text(
                            "Tu carrito está vacío",
                            modifier = Modifier.align(Alignment.CenterHorizontally).padding(vertical = 32.dp),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    } else {
                        cartItems.forEach { cartItem ->
                            CartItemCard(
                                cartItem = cartItem,
                                cartViewModel = cartViewModel
                            )
                            if (cartItems.last() != cartItem) {
                                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Total:", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                        Text(
                            "$${"%,d".format(total)}",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { /* Lógica para pagar */ },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = AppPrimaryColor)
                    ) {
                        Text("Pagar", fontSize = 18.sp)
                    }
                }
            }
        }
        item { Spacer(modifier = Modifier.height(16.dp)) }
        item {
            OutlinedButton(
                onClick = onNavigateHome,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = AppPrimaryColor),
                border = ButtonDefaults.outlinedButtonBorder.copy(brush = Brush.horizontalGradient(listOf(
                    AppPrimaryColor,
                    AppPrimaryColor
                )))
            ) {
                Text("Seguir comprando", fontSize = 16.sp)
            }
        }
        item { Spacer(modifier = Modifier.height(16.dp)) }
    }
}

@Composable
fun CartItemCard(cartItem: CartItem, cartViewModel: CartViewModel) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = cartItem.producto.imagenResId),
            contentDescription = cartItem.producto.nombre,
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(12.dp))
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(cartItem.producto.nombre, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            QuantitySelector(
                // La cantidad se lee directamente del item y los cambios se envían al ViewModel.
                quantity = cartItem.cantidad,
                onQuantityChange = { newQuantity ->
                    cartViewModel.updateQuantity(cartItem, newQuantity)
                }
            )
        }
        // El botón de eliminar llama a la función correspondiente en el ViewModel.
        IconButton(onClick = { cartViewModel.removeFromCart(cartItem) }) {
            Icon(Icons.Default.Delete, contentDescription = "Eliminar producto", tint = Color.Gray)
        }
    }
}

@Composable
fun QuantitySelector(
    quantity: Int,
    onQuantityChange: (Int) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        IconButton(
            onClick = { onQuantityChange(quantity - 1) },
            modifier = Modifier.size(32.dp).border(1.dp, Color.LightGray, CircleShape)
        ) {
            Icon(Icons.Default.Remove, contentDescription = "Restar uno")
        }
        Text(
            text = quantity.toString(),
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        IconButton(
            onClick = { onQuantityChange(quantity + 1) },
            modifier = Modifier.size(32.dp).background(AppPrimaryColor, CircleShape)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Sumar uno", tint = Color.White)
        }
    }
}