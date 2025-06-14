package io.github.mayachen350.mayascope.ui.pages

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.mayachen350.mayascope.data.LINE_AND_POEM_NUMBER
import io.github.mayachen350.mayascope.data.LINE_OF_TODAY
import io.github.mayachen350.mayascope.data.MayascopeBackend
import io.github.mayachen350.mayascope.data.RECORDED_DAY
import io.github.mayachen350.mayascope.data.dataStore
import io.github.mayachen350.mayascope.ui.theme.Kodchasan
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.LocalDateTime

/** TODO: Secret access to the `poems.txt` when the total days of mayascope has reached **30**.*/
@Composable
fun HomePage() {
    val todayMayascopeResult = remember { mutableStateOf("") }
    val todayMayascopeResultNumbers = remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()

    val context: Context = LocalContext.current
    var lastRecordDay = remember { mutableIntStateOf(-1) }
    var lastPoemLine = remember { mutableStateOf("") }
    var lastLinePoemNumber = remember { mutableStateOf("") }

    LaunchedEffect(todayMayascopeResult.value) {
        lastRecordDay.intValue = context.dataStore.data.first()[RECORDED_DAY] ?: -1
        lastPoemLine.value = context.dataStore.data.first()[LINE_OF_TODAY] ?: ""
        lastLinePoemNumber.value = context.dataStore.data.first()[LINE_AND_POEM_NUMBER] ?: ""
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Title()
        Spacer(Modifier.height(15.dp))
        if (todayMayascopeResult.value == "" && lastRecordDay.intValue != LocalDateTime.now().dayOfYear)
            MayascopeButton {
                scope.launch {
                    if (todayMayascopeResult.value == "")
                        MayascopeBackend(context).run {
                            getMayascope().also {
                                todayMayascopeResult.value = """"${it.line}""""
                                todayMayascopeResultNumbers.value = it.formatPoemLineNumber()
                            }
                        }
                }
            }
         else {
            MayascopeLine(""""${lastPoemLine.value}"""", lastLinePoemNumber.value)
        }
    }
}

@Composable
fun Title() =
    Text(
        "MAYASCOPE",
        modifier = Modifier.padding(bottom = 0.dp),
        textAlign = TextAlign.Center,
        fontSize = 40.sp,
        fontFamily = FontFamily.Kodchasan,
        fontWeight = FontWeight.ExtraBold
    )

@Composable
fun MayascopeButton(action: () -> Unit) =
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 70.dp),
        content = {
            Text(
                "Choose your fate",
                modifier = Modifier.padding(vertical = 3.dp),
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            )
        },
        colors = ButtonDefaults.buttonColors().copy(
            containerColor = Color(0xFFCE5A5A),
            contentColor = Color.White
        ),

        onClick = {
            action()
        }
    )

@Composable
fun MayascopeLine(line: String, linePoemNumber: String) =
    Column(
        modifier = Modifier.padding(horizontal = 5.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            line,
            fontSize = 25.sp,
            fontStyle = FontStyle.Italic,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(20.dp))
        Text(
            linePoemNumber
        )
    }

