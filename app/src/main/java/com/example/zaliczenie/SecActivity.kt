package com.example.zaliczenie

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.zaliczenie.ui.theme.ZaliczenieTheme

class SecActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      ZaliczenieTheme {
        SecScreen()
      }
    }
  }
}

@Composable
fun SecScreen() {
  Text("To jest druga aktywność!")
}