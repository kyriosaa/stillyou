package com.example.stillyou.menu

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WelcomeMenu(navController: NavController, onRoleSelected: (UserRole) -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Welcome to Stillyou") },
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Select Your Role", fontSize = 24.sp, modifier = Modifier.padding(bottom = 24.dp))

            RoleSelectionButton("Doctor") {
                onRoleSelected(UserRole.Doctor)
                navController.navigate("main_menu/Doctor")
            }
            RoleSelectionButton("Patient") {
                onRoleSelected(UserRole.Patient)
                navController.navigate("main_menu/Patient")
            }
            RoleSelectionButton("Patient Guardian") {
                onRoleSelected(UserRole.Guardian)
                navController.navigate("main_menu/Guardian")
            }

            Spacer(modifier = Modifier.height(32.dp))

//            Row(horizontalArrangement = Arrangement.SpaceEvenly) {
//                TextButton(onClick = { navController.navigate("login") }) {
//                    Text("Login")
//                }
//                TextButton(onClick = { navController.navigate("register") }) {
//                    Text("Register")
//                }
//            }
        }
    }
}

@Composable
fun RoleSelectionButton(label: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(label)
    }
}

enum class UserRole {
    Doctor, Patient, Guardian
}

@Preview(showBackground = true)
@Composable
fun WelcomeMenuPreview() {
    val navController = rememberNavController()
    WelcomeMenu(navController = navController, onRoleSelected = {})
}