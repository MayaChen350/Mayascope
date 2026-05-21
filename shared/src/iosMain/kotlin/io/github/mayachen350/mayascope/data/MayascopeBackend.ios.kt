package io.github.mayachen350.mayascope.data

import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.Storage
import androidx.datastore.core.okio.OkioStorage
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.PreferencesSerializer
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import okio.FileSystem
import okio.Path.Companion.toPath
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask
import kotlin.time.Clock

val RECORDED_DAY = intPreferencesKey("recorded_day")
val LINE_OF_TODAY = stringPreferencesKey("line_of_today")
val LINE_AND_POEM_NUMBER = stringPreferencesKey("line_and_poem_number")

val TOTAL_DAYS_USING_THE_MAYASCOPE = intPreferencesKey("total_days_mayascope")

suspend inline fun saveDailyData(mayascope: TodayMayascope) {
    dataStore.edit {
        it[RECORDED_DAY] = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).dayOfYear
        it[LINE_OF_TODAY] = mayascope.line
        it[LINE_AND_POEM_NUMBER] = mayascope.formatPoemLineNumber()
        it[TOTAL_DAYS_USING_THE_MAYASCOPE] = it[TOTAL_DAYS_USING_THE_MAYASCOPE]?.plus(1) ?: 1
    }
}

/**
 *   Gets the singleton DataStore instance, creating it if necessary.
 */
fun createDataStore(storage: Storage<Preferences>): DataStore<Preferences> =
    DataStoreFactory.create(storage = storage)

@OptIn(ExperimentalForeignApi::class)
fun createDataStore(): DataStore<Preferences> = createDataStore(
    storage = OkioStorage(
        fileSystem = FileSystem.SYSTEM,
        serializer = PreferencesSerializer,
        producePath = {
            val documentDirectory: NSURL? = NSFileManager.defaultManager.URLForDirectory(
                directory = NSDocumentDirectory,
                inDomain = NSUserDomainMask,
                appropriateForURL = null,
                create = false,
                error = null,
            )
            (requireNotNull(documentDirectory).path + "/$dataStoreFileName").toPath()
        }
    )
)

val dataStore = createDataStore()

internal const val dataStoreFileName = "dice.preferences_pb"