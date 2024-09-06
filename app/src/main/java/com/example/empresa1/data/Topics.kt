package com.example.empresa1.data

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import com.example.empresa1.R

enum class Topics(@StringRes val label: Int, val color: Color) {
    Trabajo(R.string.work, Color.Red),
    Finanzas(R.string.banks, Color.Yellow),
    Divertido(R.string.`fun`, Color.Cyan),
    Personales(R.string.personal, Color.DarkGray),
    Compras(R.string.shopping, Color.Blue),
    Otros(R.string.others, Color.Green)
}