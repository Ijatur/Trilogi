package com.example.trilogi

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.trilogi.ui.theme.TrilogiTheme

class MainActivity : ComponentActivity() {

    // Tag konstan untuk mempermudah pencarian di Logcat Android Studio
    private val TAG = "LIFECYCLE_TAG"

    // State Compose untuk menampilkan status siklus hidup langsung di layar HP
    private var currentStatus by mutableStateOf("onCreate: Aplikasi Dimulai")
    private var resumeCount by mutableIntStateOf(0)

    // 1. Dipanggil saat Activity pertama kali dibuat di memori RAM
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        Log.d(TAG, "onCreate: Activity dibuat dan resource diinisialisasi")
        currentStatus = "Status: onCreateeeee (Inisialisasi Selesai)"

        setContent {
            TrilogiTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LifecycleScreen(
                        status = currentStatus,
                        counter = resumeCount,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
                UILayoutDemoScreen()
            }
        }
    }

    // 2. Dipanggil saat Activity mulai terlihat di layar ponsel (visible)
    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart: Layar mulai terlihat oleh pengguna")
        currentStatus = "Status: onStart (Aplikasi Terlihat)"
    }

    // 3. Dipanggil saat Activity berada di titik terdepan dan siap menerima interaksi/sentuhan
    override fun onResume() {
        super.onResume()
        resumeCount++
        Log.d(TAG, "onResume: Aplikasi aktif dan interaktif (Fase ke-$resumeCount)")
        currentStatus = "Status: onResume (Aplikasi Siap Digunakan)"
        Toast.makeText(this, "Aplikasi Aktif (onResume)", Toast.LENGTH_SHORT).show()
    }

    // 4. Dipanggil saat Activity kehilangan sebagian fokus (misal ada pop-up atau layar transisi)
    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause: Aplikasi kehilangan sebagian fokus")
        currentStatus = "Status: onPause (Kehilangan Fokus)"
    }

    // 5. Dipanggil saat Activity sudah tertutup penuh atau pengguna menekan tombol Home
    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop: Aplikasi masuk ke latar belakang (Background)")
        currentStatus = "Status: onStop (Berada di Background)"
    }

    // 6. Dipanggil saat pengguna membuka kembali aplikasi setelah sempat berada di onStop
    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "onRestart: Aplikasi dibuka kembali dari latar belakang")
    }

    // 7. Dipanggil sebelum Activity dihapus secara total dari memori RAM
    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: Activity dihancurkan dari memori")
    }
}

@Composable
fun LifecycleScreen(status: String, counter: Int, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Praktikum Pemrograman Mobile",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "Pertemuan 2: Activity Lifecycle",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider()
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = status,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Jumlah Masuk onResume: $counter kali",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Petunjuk Pengujian Lab:\n" +
                    "1. Cek pesan di jendela Logcat (filter: LIFECYCLE_TAG)\n" +
                    "2. Tekan tombol Home, lalu buka kembali dari Recent Apps\n" +
                    "3. Putar layar ponsel (Portrait <-> Landscape)\n" +
                    "4. Tekan tombol Back untuk menguji onDestroy",
            fontSize = 12.sp,
            textAlign = TextAlign.Start,
            color = MaterialTheme.colorScheme.outline,
            lineHeight = 18.sp
        )
    }
}

@Composable
fun UILayoutDemoScreen() {
    var nameInput by remember { mutableStateOf("") }
    var submittedName by remember { mutableStateOf("") }

    // State untuk scroll
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState), // Mencegah UI terpotong saat keyboard muncul
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // --- 1. Header Praktikum ---
        Text(
            text = "Pertemuan 3: Layout & Komponen UI",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        HorizontalDivider()

        // --- 2. Demonstrasi Box & Image  ---
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(MaterialTheme.colorScheme.primaryContainer, shape = RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Komponen Image di dalam Box
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = "Logo androit",
                    modifier = Modifier.size(60.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Komponen di dalam Box",
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }

        // --- 3. Demonstrasi Row ---
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BadgeCard(title = "Row 1", color = Color(0xFFE3F2FD))
            BadgeCard(title = "Row 2", color = Color(0xFFE8F5E9))
            BadgeCard(title = "Row 3", color = Color(0xFFFFF3E0))
        }

        // --- 4. Form Input & Button ---
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Latihan Input Data", fontWeight = FontWeight.Bold)

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = nameInput,
                    onValueChange = { nameInput = it },
                    label = { Text("Masukkan Nama Mahasiswa") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = { submittedName = nameInput },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Tampilkan")
                }

                if (submittedName.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Halo, $submittedName! Selamat Belajar Compose.",
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

@Composable
fun BadgeCard(title: String, color: Color) {
    Surface(
        color = color,
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.padding(4.dp)
    ) {
        Text(
            text = title,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LifecycleScreenPreview() {
    TrilogiTheme {
//        LifecycleScreen(
//            status = "Status: onResume (Aplikasi Siap)",
//            counter = 1
//        )
        UILayoutDemoScreen()
    }
}