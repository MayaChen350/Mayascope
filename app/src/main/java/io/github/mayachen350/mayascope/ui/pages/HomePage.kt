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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import java.time.LocalDateTime

/** TODO: Secret access to the `poems.txt` when the total days of mayascope has reached **30**.*/
@Composable
fun HomePage() {
    val scope = rememberCoroutineScope()
    val context: Context = LocalContext.current

    var lastRecordDay by remember { mutableIntStateOf(-1) }
    var lastPoemLine by remember { mutableStateOf("") }
    var lastLinePoemNumber by remember { mutableStateOf("") }

    LaunchedEffect(true) {
        lastRecordDay = context.dataStore.data.firstOrNull()?.get(RECORDED_DAY) ?: -1
        lastPoemLine = context.dataStore.data.firstOrNull()?.get(LINE_OF_TODAY) ?: ""
        lastLinePoemNumber = context.dataStore.data.firstOrNull()?.get(LINE_AND_POEM_NUMBER) ?: ""
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Title()
        Spacer(Modifier.height(15.dp))
        if (lastPoemLine == "" && lastRecordDay != LocalDateTime.now().dayOfYear)
            MayascopeButton {
                scope.launch {
                    if (lastPoemLine == "")
                        with(MayascopeBackend(context).getMayascope()) {
                            lastPoemLine = "\"$line\""
                            lastLinePoemNumber = formatPoemLineNumber()
                        }
                }
            }
        else {
            MayascopeLine("\"$lastPoemLine\"", lastLinePoemNumber)
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
                "Try your fate",
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

