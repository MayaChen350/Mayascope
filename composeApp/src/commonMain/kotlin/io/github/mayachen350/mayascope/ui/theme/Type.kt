package io.github.mayachen350.mayascope.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import mayascope.composeapp.generated.resources.Kodchasan_Bold
import mayascope.composeapp.generated.resources.Kodchasan_SemiBold
import mayascope.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.FontResource


val FontFamily.Companion.Kodchasan: FontFamily
    @Composable
    get() = FontFamily(
        Font(
            Res.font.Kodchasan_SemiBold
        )
    )


// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Serif,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
)