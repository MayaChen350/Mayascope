package io.github.mayachen350.mayascope.ui.pages

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import io.github.mayachen350.mayascope.ui.theme.Kodchasan

@Composable
fun Title() {
    Text(
        "MAYASCOPE",
        textAlign = TextAlign.Center,
        fontSize = 40.sp,
        fontFamily = FontFamily.Kodchasan,
        fontWeight = FontWeight.ExtraBold
    )
}