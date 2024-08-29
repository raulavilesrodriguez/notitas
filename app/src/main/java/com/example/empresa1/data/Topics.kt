package com.example.empresa1.data

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import com.example.empresa1.R

enum class Topics(@StringRes val label: Int, val color: Color) {
    Work(R.string.work, Color.Red),
    Banks(R.string.banks, Color.Yellow),
    Fun(R.string.`fun`, Color.Cyan),
    Personal(R.string.personal, Color.DarkGray),
    Shopping(R.string.shopping, Color.Blue),
    Others(R.string.others, Color.Green)
}