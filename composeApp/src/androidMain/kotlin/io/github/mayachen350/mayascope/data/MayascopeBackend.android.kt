package io.github.mayachen350.mayascope.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import java.time.LocalDateTime

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

val RECORDED_DAY = intPreferencesKey("recorded_day")
val LINE_OF_TODAY = stringPreferencesKey("line_of_today")
val LINE_AND_POEM_NUMBER = stringPreferencesKey("line_and_poem_number")

val TOTAL_DAYS_USING_THE_MAYASCOPE = intPreferencesKey("total_days_mayascope")

suspend fun saveDailyData(context: Context, mayascope: TodayMayascope) {
    context.dataStore.edit {
        it[RECORDED_DAY] = LocalDateTime.now().dayOfYear
        it[LINE_OF_TODAY] = mayascope.line
        it[LINE_AND_POEM_NUMBER] = mayascope.formatPoemLineNumber()
        it[TOTAL_DAYS_USING_THE_MAYASCOPE] = it[TOTAL_DAYS_USING_THE_MAYASCOPE]?.plus(1) ?: 1
    }
}