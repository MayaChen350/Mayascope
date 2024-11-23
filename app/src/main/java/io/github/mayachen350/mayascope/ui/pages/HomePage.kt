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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.mayachen350.mayascope.ui.theme.Kodchasan

@Composable
fun HomePage() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Title(modifier = Modifier.padding(bottom = 0.dp))
        Spacer(Modifier.height(15.dp))
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 70.dp),
            content = {
                Text(
                    "Try",
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
                println("hello")
            },
        )
    }
}

@Composable
fun Title(modifier: Modifier = Modifier) {
    Text(
        "MAYASCOPE",
        modifier = modifier,
        textAlign = TextAlign.Center,
        fontSize = 40.sp,
        fontFamily = FontFamily.Kodchasan,
        fontWeight = FontWeight.ExtraBold
    )
}