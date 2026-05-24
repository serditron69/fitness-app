package com.example.fitlifeapp.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.fitlifeapp.viewmodel.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroScreen(
    vm: AuthViewModel,
    onRegistroExitoso: (Long) -> Unit,
    onIrALogin: () -> Unit
) {
    var nombre by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val loading by vm.loading.collectAsState()
    val error by vm.error.collectAsState()
    val loginExitoso by vm.loginExitoso.collectAsState()
    val usuario by vm.usuario.collectAsState()

    LaunchedEffect(loginExitoso) {
        if (loginExitoso) {
            usuario?.let { onRegistroExitoso(it.idUsuario) }
            vm.resetLoginExitoso()
        }
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Crear Cuenta") }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Registro",
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            error?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { vm.registrar(nombre, email, password) },
                enabled = !loading && nombre.isNotBlank() && email.isNotBlank() && password.isNotBlank(),
                modifier = Modifier.fillMaxWidth()
            ) {
                if (loading) CircularProgressIndicator(modifier = Modifier.size(20.dp))
                else Text("Registrarse")
            }

            Spacer(modifier = Modifier.height(12.dp))

            TextButton(onClick = onIrALogin) {
                Text("¿Ya tienes cuenta? Inicia Sesión")
            }
        }
    }
}