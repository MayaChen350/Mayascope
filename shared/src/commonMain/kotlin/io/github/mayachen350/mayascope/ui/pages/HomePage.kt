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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.mayachen350.mayascope.data.MayascopeBackend
import io.github.mayachen350.mayascope.data.TodayMayascope
import io.github.mayachen350.mayascope.ui.theme.Kodchasan
import kotlinx.coroutines.launch

@Composable
expect fun HomePageHaver()

/** TODO: Secret access to the `poems.txt` when the total days of mayascope has reached **30**.*/
@Composable
fun HomePage(
    lastPoemLine: MutableState<String>,
    lastLinePoemNumber: MutableState<String>,
    buttonAppearCond: () -> Boolean,
    saveDataFunc: suspend (TodayMayascope) -> Unit
) {
    val scope = rememberCoroutineScope()

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            "MAYASCOPE",
            modifier = Modifier.padding(bottom = 0.dp),
            textAlign = TextAlign.Center,
            fontSize = 45.sp,
            fontFamily = FontFamily.Kodchasan,
        )

        Spacer(Modifier.height(15.dp))

        if (buttonAppearCond())
            MayascopeButton("Try your fate") {
                scope.launch {
                    with(MayascopeBackend.getNewMayascope()) {
                        // Setting those set the MayascopeLine content
                        // Both those values are needed since they are in different Text components
                        lastPoemLine.value = this.line
                        lastLinePoemNumber.value = formatPoemLineNumber()
                        saveDataFunc(this)
                    }
                }
            }
        else
            MayascopeLine("\"${lastPoemLine.value}\"", lastLinePoemNumber.value)
    }
}

@Composable
fun MayascopeButton(textContent: String, action: () -> Unit) = Button(
    modifier = Modifier.fillMaxWidth(0.65f),
    content = {
        Text(
            textContent,
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
fun MayascopeLine(line: String, linePoemNumber: String) = Column(
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
