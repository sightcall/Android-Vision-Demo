package com.sightcall.visiondemo

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sightcall.core.experiences.SightCall
import com.sightcall.visiondemo.ui.theme.VisionDemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VisionDemoTheme {
                MainActivityContent()
            }
        }

    }
}

@Composable
fun MainActivityContent() {
    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current
    var textState by remember { mutableStateOf(TextFieldValue()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = textState,
            onValueChange = { textState = it },
            label = { Text("Enter URL") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            clipboardManager.getText()?.let { clipboardText ->
                textState = TextFieldValue(clipboardText.text)
            } ?: run {
                Toast.makeText(context, "Clipboard is empty", Toast.LENGTH_SHORT).show()
            }
        }) {
            Text("Paste from Clipboard")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                val enteredUrl = textState.text
                val uri = Uri.parse(enteredUrl)
                try {
                    SightCall.start(uri)
                } catch (e: Exception) {
                    Toast.makeText(context, "Invalid URL", Toast.LENGTH_SHORT).show()
                }
            },
            colors = ButtonDefaults.buttonColors(Color.Magenta)
        ) {
            Text("Launch Flow", color = Color.White)
        }
    }

}

@Preview(showBackground = true)
@Composable
fun MainActivityPreview() {
    VisionDemoTheme {
        MainActivityContent()
    }
}

