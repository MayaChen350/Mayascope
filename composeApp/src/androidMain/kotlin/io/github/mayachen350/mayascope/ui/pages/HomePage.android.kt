package io.github.mayachen350.mayascope.ui.pages

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import io.github.mayachen350.mayascope.data.LINE_AND_POEM_NUMBER
import io.github.mayachen350.mayascope.data.LINE_OF_TODAY
import io.github.mayachen350.mayascope.data.MayascopeBackend
import io.github.mayachen350.mayascope.data.RECORDED_DAY
import io.github.mayachen350.mayascope.data.dataStore
import io.github.mayachen350.mayascope.data.saveDailyData
import kotlinx.coroutines.flow.firstOrNull

@Composable
actual fun HomePageHaver() {
    val context: Context = LocalContext.current

    var lastRecordDay by remember { mutableIntStateOf(-1) }
    var lastPoemLine by remember { mutableStateOf("") }
    var lastLinePoemNumber by remember { mutableStateOf("") }

    LaunchedEffect(true) {
        lastRecordDay = context.dataStore.data.firstOrNull()?.get(RECORDED_DAY) ?: -1
        lastPoemLine = context.dataStore.data.firstOrNull()?.get(LINE_OF_TODAY) ?: ""
        lastLinePoemNumber = context.dataStore.data.firstOrNull()?.get(LINE_AND_POEM_NUMBER) ?: ""
    }

    HomePage(lastRecordDay, lastPoemLine, lastLinePoemNumber, buttonAction = {
        with(MayascopeBackend().getNewMayascope()) {
            lastPoemLine = "\"$line\""
            lastLinePoemNumber = formatPoemLineNumber()
            saveDailyData(context, this)
        }
    })
}