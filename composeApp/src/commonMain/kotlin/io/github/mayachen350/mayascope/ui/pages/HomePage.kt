package io.github.mayachen350.mayascope.ui.pages

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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.isSpecified
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import io.github.mayachen350.mayascope.ui.theme.Kodchasan
import kotlinx.coroutines.launch
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@Composable
expect fun HomePageHaver()

/** TODO: Secret access to the `poems.txt` when the total days of mayascope has reached **30**.*/
@OptIn(ExperimentalTime::class)
@Composable
fun HomePage(
    lastRecordDay: Int, lastPoemLine: String, lastLinePoemNumber: String,
    buttonAction: suspend () -> Unit
) {
    val scope = rememberCoroutineScope()

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Title()
        Spacer(Modifier.height(15.dp))
        if (lastPoemLine == "" && lastRecordDay != Clock.System.now()
                .toLocalDateTime(
                    TimeZone.currentSystemDefault()
                ).dayOfYear
        )
            MayascopeButton {
                scope.launch {
                    if (lastPoemLine == "")
                        buttonAction()
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
        fontSize = 45.sp,
        fontFamily = FontFamily.Kodchasan,
    )

@Composable
fun MayascopeButton(action: () -> Unit) =
    Button(
        modifier = Modifier
            .fillMaxWidth(0.65f),
//            .padding(horizontal = 70.dp)
        content = {
            Text(
                "Try your fate",
                modifier = Modifier.padding(vertical = 3.dp),
                fontSize = 25.sp,
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
            fontSize = 30.sp,
            fontStyle = FontStyle.Italic,
            lineHeight = 35.sp,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(25.dp))
        Text(
            linePoemNumber,
            fontSize = 25.sp
        )
    }

