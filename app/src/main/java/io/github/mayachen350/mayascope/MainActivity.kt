package io.github.mayachen350.mayascope

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import io.github.mayachen350.mayascope.ui.pages.HomePage
import io.github.mayachen350.mayascope.ui.theme.MayascopeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        var poems = ""
        resources.openRawResource(R.raw.poems).bufferedReader().run {
            poems = readText()
            close()
        }

        setContent {
            MayascopeTheme {
                Mayascope(poems)
            }
        }
    }
}

@Composable
fun Mayascope(poems: String) {
    Surface {
        HomePage(poems)
    }
}