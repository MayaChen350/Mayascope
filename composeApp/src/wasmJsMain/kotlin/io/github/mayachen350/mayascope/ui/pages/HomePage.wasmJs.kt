package io.github.mayachen350.mayascope.ui.pages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import io.github.mayachen350.mayascope.data.MayascopeBackend
import io.github.mayachen350.mayascope.data.saveDailyData
import io.github.mayachen350.mayascope.ui.pages.HomePage
import kotlinx.browser.document
import kotlinx.coroutines.flow.firstOrNull

@Composable
actual fun HomePageHaver() {
    var lastRecordDay by remember { mutableIntStateOf(-1) }
    var lastPoemLine by remember { mutableStateOf("") }
    var lastLinePoemNumber by remember { mutableStateOf("") }

    LaunchedEffect(true) {
        val cookie = document.cookie.split(";").getOrNull(0)?.split(",")
        lastRecordDay = cookie?.getOrNull(0)?.toIntOrNull() ?: 0
        lastPoemLine = cookie?.getOrNull(1) ?: ""
        lastLinePoemNumber = cookie?.getOrNull(2) ?: ""
    }

    HomePage(lastRecordDay, lastPoemLine, lastLinePoemNumber, buttonAction = {
        with(MayascopeBackend().getNewMayascope()) {
            lastPoemLine = "\"$line\""
            lastLinePoemNumber = formatPoemLineNumber()
            saveDailyData(this)
        }
    })
}