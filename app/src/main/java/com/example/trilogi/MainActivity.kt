package com.example.trilogi

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.trilogi.ui.theme.TrilogiTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TrilogiTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    IntentDemoScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun IntentDemoScreen(modifier: Modifier = Modifier) {
    // LocalContext diperlukan untuk menjalankan Intent di dalam Compose
    val context = LocalContext.current
    var nameInput by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Pertemuan 4: Intent & Navigasi",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        HorizontalDivider()

        OutlinedTextField(
            value = nameInput,
            onValueChange = { nameInput = it },
            label = { Text("Masukkan Nama Anda") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        // --- EXPLICIT INTENT ---
        Button(
            onClick = {
                // Mengirim data antar Activity menggunakan Key-Value Pair
                val intent = Intent(context, DetailActivity::class.java).apply {
                    putExtra("EXTRA_NAME", nameInput)     // Tipe Data String
                    putExtra("EXTRA_SEMESTER", 5)         // Tipe Data Int
                    putExtra("EXTRA_IS_ACTIVE", true)     // Tipe Data Boolean
                }
                context.startActivity(intent)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Buka DetailScreen (Explicit Intent)")
        }

        // --- IMPLICIT INTENT ---
        OutlinedButton(
            onClick = {
                // Menjalankan panggilan telepon dengan ACTION_DIAL dan parsing URI
                val intent = Intent(Intent.ACTION_DIAL).apply {
                    data = Uri.parse("tel:08123456789")
                }
                context.startActivity(intent)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Hubungi Dosen (Implicit Intent)")
        }
    }
}

@Preview(showBackground = true, name = "Main Screen Preview")
@Composable
fun IntentDemoScreenPreview() {
    TrilogiTheme {
        // Scaffold atau Surface bisa ditambahkan jika ingin background default yang rapi
        Surface(modifier = Modifier.fillMaxSize()) {
            IntentDemoScreen()
        }
    }
}