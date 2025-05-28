package com.example.zaliczenie

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.zaliczenie.ui.theme.ZaliczenieTheme
import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class SecActivity : ComponentActivity() {
  private val channelId = "text_channel"

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    // 🔐 Prośba o pozwolenie (Android 13+)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
      if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
        != PackageManager.PERMISSION_GRANTED
      ) {
        ActivityCompat.requestPermissions(
          this,
          arrayOf(Manifest.permission.POST_NOTIFICATIONS),
          1001
        )
      }
    }

    val receivedText = intent.getStringExtra("TEXT_KEY") ?: "(brak danych)"
    createNotificationChannel()

    setContent {
      ZaliczenieTheme {
        SecScreen(text = receivedText, channelId = channelId)
      }
    }
  }

  private fun createNotificationChannel() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      val name = "Text Notifications"
      val descriptionText = "Kanał do przekazywania tekstu z MainActivity"
      val importance = NotificationManager.IMPORTANCE_DEFAULT
      val channel = NotificationChannel(channelId, name, importance).apply {
        description = descriptionText
      }
      val notificationManager: NotificationManager =
        getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
      notificationManager.createNotificationChannel(channel)
    }
  }
}

@SuppressLint("MissingPermission")
@Composable
fun SecScreen(text: String, channelId: String) {
  val context = LocalContext.current

  Button(onClick = {
    val notification = NotificationCompat.Builder(context, channelId)
      .setSmallIcon(android.R.drawable.ic_dialog_info) // możesz dodać własną ikonę
      .setContentTitle("The mind says: ")
      .setContentText(text)
      .setPriority(NotificationCompat.PRIORITY_DEFAULT)
      .build()

    with(NotificationManagerCompat.from(context)) {
      notify(1, notification)
    }
  }) {
    Text("Notify")
  }
}