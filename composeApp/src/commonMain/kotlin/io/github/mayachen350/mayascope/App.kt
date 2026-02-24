package io.github.mayachen350.mayascope

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import io.github.mayachen350.mayascope.ui.pages.HomePageHaver
import io.github.mayachen350.mayascope.ui.theme.MayascopeTheme

@Composable
fun App() {
    MayascopeTheme {
        Surface {
            HomePageHaver()
        }
    }
}