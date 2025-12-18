package io.github.mayachen350.mayascope.ui.pages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import io.github.mayachen350.mayascope.data.cs
import io.github.mayachen350.mayascope.data.getCookie
import kotlinx.browser.document

@Composable
actual fun HomePageHaver() {
    val lastPoemLine = remember { mutableStateOf("") }
    val lastLinePoemNumber = remember { mutableStateOf("") }

    LaunchedEffect(true) {
        document.cookie.run {
            if (this.isNotEmpty()) {
                val bakedMayascope = this.substringAfter("=").split(cs)

                bakedMayascope.getOrNull(0)?.let {
                    lastPoemLine.value = it
                    bakedMayascope.getOrNull(1)
                }?.also { lastLinePoemNumber.value = it }
            }
        }
    }

    HomePage(
        lastPoemLine,
        lastLinePoemNumber,
        buttonAppearCond = { lastPoemLine.value == "" },
        saveDataFunc = { document.cookie = it.getCookie() }
    )
}