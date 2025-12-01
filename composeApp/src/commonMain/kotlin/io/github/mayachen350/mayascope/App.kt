package io.github.mayachen350.mayascope

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import io.github.mayachen350.mayascope.ui.pages.HomePage
import io.github.mayachen350.mayascope.ui.theme.MayascopeTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MayascopeTheme {
        Surface {
            HomePage()
        }
    }
}