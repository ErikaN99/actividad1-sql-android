package com.example.introsqlapp
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.introsqlapp.ui.theme.IntroSQLAppTheme
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TipScreen(this)

        }
    }

}
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    IntroSQLAppTheme {
        Greeting("Android")
    }
}

@Composable
fun TipScreen(context: Context) {
    val db = remember { TipDatabaseHelper(context) }
    var tipInput by remember { mutableStateOf("") }
    var tips by remember { mutableStateOf(db.readAllTips()) }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        TextField(
            value = tipInput,
            onValueChange = { tipInput = it },
            label = { Text("Ingresa la propina") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = {
            val tip = tipInput.toDoubleOrNull()
            if (tip != null) {
                db.insertTip(tip)
                tips = db.readAllTips()
                tipInput = ""
            }
        }, modifier = Modifier.fillMaxWidth()) {
            Text("Guardar")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Historial de propinas:")
        tips.forEach {
            Text("Propina: $it")
        }
    }
}
