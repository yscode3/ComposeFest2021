package ys.compose.basicsyscode2

import android.media.audiofx.Equalizer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ys.compose.basicsyscode2.ui.theme.BasicsYSCode2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BasicsYSCode2Theme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    DrawingBoxList()
                }
            }
        }
    }
}

@Composable
fun DrawingBoxList(numbers: List<String> = List(20) { "$it" }) {
    // in Layout
    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ) {
        items(items = numbers) { item ->
            DrawColumn(item)
        }
    }
}

@Composable
fun DrawColumn(item: String) {
    Surface(
        color = MaterialTheme.colors.primary,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            for (rowCount in 0 until 2) {
                DrawRow(item)
            }
        }
    }
}

@Composable
fun DrawRow(item: String) {
    Column {
        Text(text = "Hello, ")
        Text(text = item)
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BasicsYSCode2Theme {
        DrawingBoxList()
    }
}