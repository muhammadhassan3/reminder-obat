package com.muhammhassan.reminderobat.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.muhammhassan.reminderobat.R

val plusJakarta = FontFamily(
    Font(R.font.plus_jakarta, FontWeight.Normal)
)

val Typography = Typography(
    h1= TextStyle(
        fontFamily = plusJakarta,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold
    ),
    body1 = TextStyle(
        fontFamily = plusJakarta,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    )
)