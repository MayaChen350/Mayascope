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

        setContent {
            MayascopeTheme {
                Mayascope()
            }
        }
    }
}

@Composable
fun Mayascope() {
    Surface {
        HomePage()
    }
}