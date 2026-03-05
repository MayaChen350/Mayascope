package io.github.mayachen350.mayascope.ui.pages

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import io.github.mayachen350.mayascope.data.LINE_AND_POEM_NUMBER
import io.github.mayachen350.mayascope.data.LINE_OF_TODAY
import io.github.mayachen350.mayascope.data.RECORDED_DAY
import io.github.mayachen350.mayascope.data.dataStore
import io.github.mayachen350.mayascope.data.saveDailyData
import kotlinx.coroutines.flow.firstOrNull
import java.time.LocalDateTime

@Composable
actual fun HomePageHaver() {
    val context: Context = LocalContext.current

    val lastRecordDay = remember { mutableIntStateOf(-1) }
    val lastPoemLine = remember { mutableStateOf("") }
    val lastLinePoemNumber = remember { mutableStateOf("") }

    LaunchedEffect(true) {
        context.dataStore.data.firstOrNull()?.get(RECORDED_DAY)?.let {
            lastRecordDay.intValue = it
            context.dataStore.data.firstOrNull()?.get(LINE_OF_TODAY)
        }?.let {
            lastPoemLine.value = it
            context.dataStore.data.firstOrNull()?.get(LINE_AND_POEM_NUMBER)
        }?.also { lastLinePoemNumber.value = it }
    }

    val today by lazy { LocalDateTime.now().dayOfYear }

    HomePage(lastPoemLine, lastLinePoemNumber, buttonAppearCond = {
        lastPoemLine.value == "" || lastRecordDay.intValue != today
    }, saveDataFunc = {
        lastRecordDay.intValue = today // Necessary line so the line can appear
        saveDailyData(context, it)
    })
}