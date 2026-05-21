package io.github.mayachen350.mayascope.ui.pages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import io.github.mayachen350.mayascope.data.LINE_AND_POEM_NUMBER
import io.github.mayachen350.mayascope.data.LINE_OF_TODAY
import io.github.mayachen350.mayascope.data.RECORDED_DAY
import io.github.mayachen350.mayascope.data.dataStore
import io.github.mayachen350.mayascope.data.saveDailyData
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock

@Composable
actual fun HomePageHaver() {

    val lastRecordDay = remember { mutableIntStateOf(-1) }
    val lastPoemLine = remember { mutableStateOf("") }
    val lastLinePoemNumber = remember { mutableStateOf("") }

    LaunchedEffect(true) {
        dataStore.data.firstOrNull()?.get(RECORDED_DAY)?.let {
            lastRecordDay.intValue = it
            dataStore.data.firstOrNull()?.get(LINE_OF_TODAY)
        }?.let {
            lastPoemLine.value = it
            dataStore.data.firstOrNull()?.get(LINE_AND_POEM_NUMBER)
        }?.also { lastLinePoemNumber.value = it }
    }

    val today by lazy { Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).dayOfYear }

    HomePage(lastPoemLine, lastLinePoemNumber, buttonAppearCond = {
        lastPoemLine.value == "" || lastRecordDay.intValue != today
    }, saveDataFunc = {
        lastRecordDay.intValue = today // Necessary line so the line can appear
        saveDailyData(it)
    })
}