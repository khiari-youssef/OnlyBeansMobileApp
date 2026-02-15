package com.youapps.designsystem

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.youapps.onlybeans.designsystem.R

data object OBFontFamilies {
    val BrandFontRegular = FontFamily(Font(R.font.brand_font))
    val MainRegularFontFamily = FontFamily(Font(R.font.roboto_regular))
    val MainMediumFontFamily = FontFamily(Font(R.font.roboto_medium))
    val MainBoldFontFamily = FontFamily(Font(R.font.roboto_bold))
    val MainItalicFontFamily = FontFamily(Font(R.font.roboto_italic))
}