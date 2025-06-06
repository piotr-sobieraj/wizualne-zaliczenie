package com.example.zaliczenie

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.zaliczenie.ui.theme.ZaliczenieTheme
import androidx.compose.runtime.setValue
import androidx.compose.material3.TextField
import androidx.compose.material3.Button
import androidx.compose.ui.platform.LocalContext
import android.widget.Toast


class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      ZaliczenieTheme {
        MainScreen()
      }
    }
  }
}

@Composable
fun MainScreen() {
  var text by remember { mutableStateOf("") }
  val context = LocalContext.current

  Column(
    modifier = Modifier
      .padding(16.dp)
      .fillMaxWidth(),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    // Górny rząd: pole tekstowe + guzik obok
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      TextField(
        value = text,
        onValueChange = { text = it },
        modifier = Modifier.weight(1f),
        label = { Text("Say something") }
      )

      Button(onClick = {
        val intent = Intent(context, SecActivity::class.java)
        intent.putExtra("TEXT_KEY", text) // ← dodajemy tekst do intencji
        context.startActivity(intent)
      }) {
        Text("Send")
      }
    } // ← tu zamykamy Row!

    // Dolny guzik: na całą szerokość
    Button(
      onClick = {
        val inflater = android.view.LayoutInflater.from(context)
        val layout = inflater.inflate(R.layout.toast_layout, null)

        val textView = layout.findViewById<android.widget.TextView>(R.id.toast_text)
        textView.text = "🗣️ You said: $text"

        val toast = Toast(context)
        toast.duration = Toast.LENGTH_SHORT
        toast.view = layout
        toast.show()
      },
      modifier = Modifier.fillMaxWidth()
    ) {
      Text("Speak your mind")
    }
  }
}

