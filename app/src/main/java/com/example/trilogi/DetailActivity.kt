package com.example.trilogi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.trilogi.ui.theme.TrilogiTheme

class DetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Mengambil data berdasarkan Key yang dikirimkan[cite: 2]
        // Menggunakan Elvis Operator (?:) sebagai safety net jika data null[cite: 2]
        val passedName = intent.getStringExtra("EXTRA_NAME") ?: "Guest"
        val passedSemester = intent.getIntExtra("EXTRA_SEMESTER", 0)
        val isActive = intent.getBooleanExtra("EXTRA_IS_ACTIVE", false)

        setContent {
            TrilogiTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    DetailScreen(
                        name = passedName,
                        semester = passedSemester,
                        isActive = isActive,
                        onBackClicked = {
                            finish() // Menghapus activity saat ini secara programatik[cite: 2]
                        },
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun DetailScreen(
    name: String,
    semester: Int,
    isActive: Boolean,
    onBackClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Halaman Detail Mahasiswa",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Nama: $name", fontSize = 16.sp)
                Text("Semester: $semester", fontSize = 16.sp)
                Text("Status Aktif: ${if (isActive) "Ya" else "Tidak"}", fontSize = 16.sp)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onBackClicked,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
        ) {
            Text("Kembali & Hancurkan Activity (finish)")
        }
    }
}

@Preview(showBackground = true, name = "Detail Screen Preview")
@Composable
fun DetailScreenPreview() {
    TrilogiTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            DetailScreen(
                name = "Rahmat Ababil",    // Nilai dummy untuk preview
                semester = 6,              // Nilai dummy untuk preview
                isActive = true,           // Nilai dummy untuk preview
                onBackClicked = {
                    // Biarkan kosong untuk preview saja
                }
            )
        }
    }
}